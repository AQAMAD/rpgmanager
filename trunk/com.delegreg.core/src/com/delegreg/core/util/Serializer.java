package com.delegreg.core.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Serializer {

	static Serializer instance;
	
	final private XStream streamer=new XStream(new DomDriver()); 
	//final private XStream copystreamer=new XStream(new DomDriver()); 
	
	public Serializer() {
		super();
		initStreamer(streamer);
//		initStreamer(copystreamer);
//		copystreamer.omitField(ContaineableNameable.class, "container");
	}

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
	
	public static Serializer getInstance(){
		if (instance==null){
			instance=new Serializer();
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
	
//	final public String CopyToXML(Object obj){
//		try {
//			return copystreamer.toXML(obj);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
}
