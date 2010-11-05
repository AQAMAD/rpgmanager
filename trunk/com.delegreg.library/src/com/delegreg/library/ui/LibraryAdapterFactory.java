/*
 * 
 */
package com.delegreg.library.ui;

import java.util.ArrayList;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.IContainer;
import com.delegreg.library.Activator;
import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.IDocRessource;
import com.delegreg.library.model.IDocument;
import com.delegreg.library.model.Libraries;
import com.delegreg.library.model.Library;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;

/**
 * A factory for creating RPGMAdapter objects.
 */
public class LibraryAdapterFactory implements IAdapterFactory {

	/**
	 * Instantiates a new rPGM adapter factory.
	 */
	public LibraryAdapterFactory() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	@Override
	public Object getAdapter(final Object adaptableObject, final Class adapterType) {
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Library)
			return libraryAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Libraries)
			return librariesAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof Libraries[])
			return librariesArrayAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof IDocument)
			return documentAdapter;
		if(adapterType == IWorkbenchAdapter.class && adaptableObject instanceof IDocRessource)
			return documentRessourceAdapter;
		return null;
	}

	public String computeSizeLabel(IContainer<?> array){
		if (array.size()==0) {
			return "";//$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$
		} else {
			return " (" + array.size() + ")" ;  //$NON-NLS-1$//$NON-NLS-2$
		}
	};
	
	public String computeSizeLabel(ArrayList array){
		if (array.size()==0) {
			return "";//$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$
		} else {
			return " (" + array.size() + ")" ;  //$NON-NLS-1$//$NON-NLS-2$
		}
	};

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Class[] getAdapterList() {
		return new Class[] {IWorkbenchAdapter.class};
	}



	/** The libraries adapter. */
	private IWorkbenchAdapter librariesAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Libraries)o).getContainer();
		}
		public String getLabel(Object o) {
			Libraries entry = ((Libraries)o);
			return entry.getName() + computeSizeLabel(entry); //$NON-NLS-2$
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Activator.PLUGIN_ID, IImageKeys.LIBRARIES);
		}

		public Object[] getChildren(Object o) {
			Libraries entry = ((Libraries)o);
			return entry.toArray();
		}
	};

	/** The libraries Array adapter. */
	private IWorkbenchAdapter librariesArrayAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return null;
		}
		public String getLabel(Object o) {
			return ""; //$NON-NLS-2$
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}

		public Object[] getChildren(Object o) {
			Libraries[] entry = ((Libraries[])o);
			return entry;
		}
	};

	/** The library adapter. */
	private IWorkbenchAdapter libraryAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((Library)o).getContainer();
		}
		public String getLabel(Object o) {
			Library entry = ((Library)o);
			return entry.getName() + computeSizeLabel(entry); //$NON-NLS-1$ //$NON-NLS-2$
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Activator.PLUGIN_ID, IImageKeys.LIBRARY);
		}

		public Object[] getChildren(Object o) {
			Library entry = ((Library)o);
			Object tmp[] = new Object[entry.size()+entry.getSubLibraries().size()];
			System.arraycopy(entry.getSubLibraries().toArray(), 0, tmp, 0, entry.getSubLibraries().size());
			System.arraycopy(entry.toArray(), 0, tmp, entry.getSubLibraries().size(), entry.size());			
			return tmp;
		}
	};


	/** The document adapter. */
	private IWorkbenchAdapter documentAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((IDocument)o).getContainer();
		}
		public String getLabel(Object o) {
			IDocument entry = ((IDocument)o);
			return entry.getName();
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			if (object instanceof PDFDocument) {
				return AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, IImageKeys.PDF);
			} else if (object instanceof HttpDocument) {
				return AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, IImageKeys.WEBSITE);
			} else if (object instanceof AudioDocument) {
				return AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, IImageKeys.PLAYLIST);
			} else {
				return AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, IImageKeys.IMAGE);
			}
		}

		public Object[] getChildren(Object o) {
			//to be filled soon too
			//add the docreferences objects
			//return new Object[0];
			IDocument entry = ((IDocument)o);
			return entry.toArray();					    
		}
	};


	/** The document ressource adapter. */
	private IWorkbenchAdapter documentRessourceAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return ((IDocRessource)o).getContainer();
		}
		public String getLabel(Object o) {
			IDocRessource entry = ((IDocRessource)o);
			return entry.getName();
		}
		public ImageDescriptor getImageDescriptor(Object object) {
			if (object instanceof PDFDocRessource) {
				return AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, IImageKeys.PDF);
			} else if (object instanceof HttpDocRessource) {
				return AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, IImageKeys.WEBPAGE);
			} else {
				return AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, IImageKeys.AUDIO);
			}
		}

		public Object[] getChildren(Object o) {
			IDocRessource entry = ((IDocRessource)o);
			return entry.toArray();
		}
	};



}
