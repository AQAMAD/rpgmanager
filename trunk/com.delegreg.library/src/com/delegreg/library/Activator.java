package com.delegreg.library;

import org.osgi.framework.BundleContext;

import com.delegreg.core.AbstractActivator;
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
import com.delegreg.library.util.LibrarySerializer;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = AbstractActivator.VENDOR_ID + ".library";

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		String xml=readStringFrom("data/system.library");
		Libraries sys=(Libraries) LibrarySerializer.getInstance().fromXML(xml);
		Libraries.registerLibraries(sys);
		}    
}
