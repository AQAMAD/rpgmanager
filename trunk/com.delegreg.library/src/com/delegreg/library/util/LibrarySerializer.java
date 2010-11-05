package com.delegreg.library.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.delegreg.core.util.Serializer;
import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.AudioRessource;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.ImageDocument;
import com.delegreg.library.model.Libraries;
import com.delegreg.library.model.Library;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class LibrarySerializer {

	public LibrarySerializer() {
		super();
		initStreamer(streamer);
		addLoader(getClass().getClassLoader());
		alias("pdfdocument",PDFDocument.class); //$NON-NLS-1$
		alias("pdfdocressource",PDFDocRessource.class); //$NON-NLS-1$
		alias("httpdocressource",HttpDocRessource.class); //$NON-NLS-1$
		alias("httpdocument",HttpDocument.class); //$NON-NLS-1$
		alias("library",Library.class); //$NON-NLS-1$
		alias("libraries",Libraries.class); //$NON-NLS-1$
		alias("audioressource",AudioRessource.class); //$NON-NLS-1$
		alias("audiodocument",AudioDocument.class); //$NON-NLS-1$
		alias("imagedocument",ImageDocument.class); //$NON-NLS-1$
	}
	
	static LibrarySerializer instance;
	
	final private XStream streamer=new XStream(new DomDriver()); 
	//final private XStream copystreamer=new XStream(new DomDriver()); 
	

	private void initStreamer(XStream streamer) {
		ClassLoader cl = Serializer.class.getClassLoader(); 
		addLoader(cl);
	}

	public void alias(String alias,Class theClass){
		streamer.alias(alias,theClass);
	}
	
	public void addLoader(ClassLoader cl){
		((CompositeClassLoader) streamer.getClassLoader()).add( cl );
	}
	
	public static LibrarySerializer getInstance(){
		if (instance==null){
			instance=new LibrarySerializer();
		}
		return instance;
	}
	
	final public Object fromXML(String xml){
		//System.out.println(xml);
		//deserialize
		try {
			return streamer.fromXML(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	final public Object fromFileName(String  fileName) throws IOException{
		StringBuffer str=new StringBuffer();//=item.getXML();
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String line=""; //$NON-NLS-1$
		while (line!=null) {
			line=in.readLine();	
			if (line!=null) {
				str.append(line + "\n"); //$NON-NLS-1$
			}
		}
		in.close();

		String xml=str.toString();
		
		return streamer.fromXML(xml);
	}
	
	final public String toXML(Object obj){
		try {
			return streamer.toXML(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
