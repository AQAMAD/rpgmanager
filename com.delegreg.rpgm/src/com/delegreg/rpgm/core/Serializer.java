package com.delegreg.rpgm.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.ui.PlatformUI;

import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.AudioRessource;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.ImageDocument;
import com.delegreg.library.model.Libraries;
import com.delegreg.library.model.Library;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;
import com.delegreg.rpgm.model.Actor;
import com.delegreg.rpgm.model.Campaign;
import com.delegreg.rpgm.model.Campaigns;
import com.delegreg.rpgm.model.Group;
import com.delegreg.rpgm.model.Groups;
import com.delegreg.rpgm.model.Location;
import com.delegreg.rpgm.model.Locations;
import com.delegreg.rpgm.model.Scenario;
import com.delegreg.rpgm.model.Scenarios;
import com.delegreg.rpgm.model.Sequence;
import com.delegreg.rpgm.model.Sequences;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.io.xml.DomDriver;

public final class Serializer {

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
		ClassLoader cl = Campaigns.class.getClassLoader(); 
		((CompositeClassLoader) streamer.getClassLoader()).add( cl );
		//if (cl != null) streamer.setClassLoader(cl);
		streamer.alias("campaigns", Campaigns.class); //$NON-NLS-1$
		streamer.alias("campaign", Campaign.class); //$NON-NLS-1$
		streamer.alias("scenario", Scenario.class); //$NON-NLS-1$
		streamer.alias("location", Location.class); //$NON-NLS-1$
		streamer.alias("group", Group.class); //$NON-NLS-1$
		streamer.alias("groups", Groups.class); //$NON-NLS-1$
		streamer.alias("locations", Locations.class); //$NON-NLS-1$
		streamer.alias("scenarios", Scenarios.class); //$NON-NLS-1$
		streamer.alias("sequence", Sequence.class); //$NON-NLS-1$
		streamer.alias("sequences", Sequences.class); //$NON-NLS-1$
		streamer.alias("actor", Actor.class); //$NON-NLS-1$
		streamer.alias("pdfdocument",PDFDocument.class); //$NON-NLS-1$
		streamer.alias("pdfdocressource",PDFDocRessource.class); //$NON-NLS-1$
		streamer.alias("httpdocressource",HttpDocRessource.class); //$NON-NLS-1$
		streamer.alias("httpdocument",HttpDocument.class); //$NON-NLS-1$
		streamer.alias("library",Library.class); //$NON-NLS-1$
		streamer.alias("libraries",Libraries.class); //$NON-NLS-1$
		streamer.alias("audioressource",AudioRessource.class); //$NON-NLS-1$
		streamer.alias("audiodocument",AudioDocument.class); //$NON-NLS-1$
		streamer.alias("imagedocument",ImageDocument.class); //$NON-NLS-1$
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
