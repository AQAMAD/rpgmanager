package com.delegreg.library.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.ParserException;
import org.jpedal.PdfDecoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.AudioRessource;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.IDocRessource;
import com.delegreg.library.model.IDocument;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;

public class DocumentIndexer {

	private PdfDecoder pdfDecoder;
	
	public boolean index(IDocument doc){
		if (doc instanceof PDFDocument){
			indexPDF((PDFDocument) doc);
			return true;
		}
		if (doc instanceof HttpDocument){
			indexHttp((HttpDocument) doc);
			return true;
		}
		if (doc instanceof AudioDocument){
			indexAudio((AudioDocument) doc);
			return true;
		}
		return false;
	}
	
	private void indexAudio(AudioDocument doc) {
		File docFolder=new File(doc.getFolder());
		File[] list=docFolder.listFiles();
		if (list!=null){
			for (int i = 0; i < list.length; i++) {
				File docFile=(File)list[i];
				if (docFile.getName().endsWith(".mp3")){ //$NON-NLS-1$
					//let's add it to the Audio Doc
					AudioRessource ar=new AudioRessource(docFile.getName());
					ar.setFileName(docFile.getPath());
					doc.add(ar);
				}
			}
		}
	}	
	
	private void indexPDF(PDFDocument pdf){
		pdfDecoder=new PdfDecoder(true);
		try {
			pdfDecoder.openPdfFile(pdf.getFileName());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Document XMLOutline = null;
		try {
			XMLOutline = pdfDecoder.getOutlineAsXML();
			if (XMLOutline!=null) {
				try {
					Element root=XMLOutline.getDocumentElement();
					if (root!=null){
						Node title=root.getFirstChild();
						processPDFTitle(title, pdf, pdf.getFileName());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//System.out.println(XMLOutline.getDocumentElement().toString());
					e.printStackTrace();
				}
			}
			else {
				//System.out.println("null outline"); //$NON-NLS-1$
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		
	}
	
	private void processPDFTitle(Node title,List<IDocRessource> list,String filename){
		Node item=title;
		while (item!=null) {
			try {
				NamedNodeMap attributes=item.getAttributes();
				String name=attributes.getNamedItem("title").getTextContent(); //$NON-NLS-1$
				String page=attributes.getNamedItem("page").getTextContent(); //$NON-NLS-1$
				//System.out.println(name + " " + page);
				PDFDocRessource docres=new PDFDocRessource(name);
				docres.setPageNumber(Integer.parseInt(page));
				list.add(docres);
				processPDFTitle(item.getFirstChild(), docres, filename);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			item=item.getNextSibling();
		}
	}		

	private Lexer lexer;

	private void indexHttp(HttpDocument http){
		index(http,http.getUrl());
		//second pass indexing
		for (Iterator iterator = http.iterator(); iterator.hasNext();) {
			IDocRessource docRessource = (IDocRessource) iterator.next();
			HttpDocRessource newindex=(HttpDocRessource)docRessource;
			index(newindex,newindex.getUrl());
		}
	}



	private void index(BaseContaineableContainer<IDocRessource> doc,String url) {
		String html=""; //$NON-NLS-1$
		try {
			html = getPage(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println(url);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(url);
			e.printStackTrace();
		}
		indexHTML(doc, url, html);
	}



	public void indexHTML(BaseContaineableContainer<IDocRessource> doc, String url, String html) {
		URL parsedurl;
		URL newparsedurl;
		try {
			parsedurl = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println(url);
			e.printStackTrace();
			return;
		}		
		if (html==null){return;}
		lexer=new Lexer(html);
		org.htmlparser.Node node;
		Tag tag;
		String name;
		boolean ina;
		Tag startatag = null;
		String href;
		String acontent = null;
		try {
			while (null != (node = lexer.nextNode()))
			{
			    if (node instanceof Tag)
			    {
			        tag = (Tag)node;
			        name = tag.getTagName ();
			        if ("A".equals (name)) //$NON-NLS-1$
			        {
			            if (tag.isEndTag ())
			            {
			                ina = false;
			                href = startatag.getAttribute ("HREF"); //$NON-NLS-1$
			                if (null != href)
			                {
			                	//we solved a new link, we'll add it
			                	//TODO : lots, rework strings and urls
			                	//test outside links, titleless links and other things
			            		try {
			            			if (!(href.contains("javascript:"))){ //$NON-NLS-1$
				            			newparsedurl = new URL(parsedurl,href);
					            		if (newparsedurl.getProtocol().equals("http") || newparsedurl.getProtocol().equals("https")){ //$NON-NLS-1$ //$NON-NLS-2$
					            			//http link (avoids mailto)
						            		if (newparsedurl.getHost().equalsIgnoreCase(parsedurl.getHost())){
						            			//same host, can add
						            			//must cast/translate acontent
						            			acontent=acontent.replace("&amp;", "&"); //$NON-NLS-1$ //$NON-NLS-2$
							                	HttpDocRessource dr=new HttpDocRessource(acontent);
							                	dr.setUrl(newparsedurl.toExternalForm());
							                	if (doc instanceof HttpDocument){
							                		((HttpDocument)doc).add(dr);
							                	}else if (doc instanceof HttpDocRessource){
							                		((HttpDocRessource)doc).add(dr);
							                	}
							                	
						            		}
					            		}
			            			}
			            		} catch (MalformedURLException e) {
			            			// TODO Auto-generated catch block
			            			System.out.println(href);
			            			e.printStackTrace();
			            		}
			                }
			            }
			            else
			            {
			                startatag = tag;
			                ina = true;
			            }
			        }
			    } else if (node instanceof Text){
			    	acontent=node.toPlainTextString();
			    }
			    
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			System.out.println(html);
			e.printStackTrace();
		}
	}


	
	public static String getPage(URL url) throws IOException 
	  {
	    try 
	    {
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8")); //$NON-NLS-1$
	        String str;
	        StringBuffer html=new StringBuffer();

	        while ((str = in.readLine()) != null) 
	        {
	          html.append(str);
	        }

	        in.close();
	        return html.toString();
	    } 
	    catch (MalformedURLException e) {
	    	e.printStackTrace();
	    	return null;
	    } 
	    catch (IOException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	  }
	
	
	
}
