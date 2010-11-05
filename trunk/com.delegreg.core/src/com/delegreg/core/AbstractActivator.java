package com.delegreg.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public abstract class AbstractActivator extends Plugin {

	// The plug-in ID
	public static final String VENDOR_ID = "com.delegreg";

	public static String PLUGIN_ID;
	
	// The shared instance
	private static AbstractActivator plugin;
	
	/**
	 * The constructor
	 */
	public AbstractActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static AbstractActivator getDefault() {
		return plugin;
	}

	static public String readStringFrom(String filename) {
        try {
        	InputStream stream=openStream(filename);
        	BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            StringBuilder sb=new StringBuilder();
            for (String s = in.readLine();s != null;s=in.readLine()) {
            	sb.append(s);
            }
            return sb.toString();
        } catch (IOException e) {
            log(makeStatus(IStatus.WARNING,"Problem reading strings from file",e)); //$NON-NLS-1$
        }
        return new String("");
    }

	static public InputStream openStream(String filename) {
        try {
            IPath path = new Path(filename);
            Bundle bundle = getDefault().getBundle();
            InputStream stream = FileLocator.openStream(bundle,path,false);
            return stream;
        } catch (IOException e) {
            log(makeStatus(IStatus.WARNING,"Problem opening from file",e)); //$NON-NLS-1$
        }
        return null;
    }


	public static IStatus makeStatus(int severity, String msg, Throwable exc) {
        if (exc != null) exc.printStackTrace();
        return new Status(severity,PLUGIN_ID,IStatus.OK,msg,exc);
    }
    public static IStatus makeStatus(int severity, String msg) {
        return makeStatus(severity,msg,null);
    }    

    static public void log(IStatus status) {
        getDefault().getLog().log(status);
    }		
}
