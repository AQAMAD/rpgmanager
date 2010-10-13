package com.delegreg.rpgm.ui.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.IFile;
import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.AudioRessource;
import com.delegreg.library.model.IDocument;
import com.delegreg.library.model.Library;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Campaigns;
import com.delegreg.rpgm.ui.IImageKeys;

public class SolidifyAction extends Action implements IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.solidify"; //$NON-NLS-1$

	public SolidifyAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText(Messages.SolidifyAction_Name);
		setToolTipText(Messages.SolidifyAction_Tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.SOLIDIFY));
	}


	public static String getID() {
		return ID;
	}


	public void run() {
		try {
			DirectoryDialog dialog=new DirectoryDialog(window.getShell(),SWT.OPEN);
			String fileName=dialog.open();
			final Campaigns root=Application.getCurrentCampaigns();
			//enumerate number of items to solidify
			//we must count documents in each library
			int totaldocs=0;
			Library templib;
			for (Iterator iterator = root.getReferences().iterator(); iterator.hasNext();) {
				templib = (Library) iterator.next();
				totaldocs=+countDocuments(templib);
			}
			final int doccount=totaldocs;
			if (fileName!=null) {
				//action validated
				final File dir=new File(fileName);
				ProgressMonitorDialog pd = new ProgressMonitorDialog(window.getShell());
				try {
					pd.run(false /*fork*/, true /*cancelable*/, new IRunnableWithProgress() {
					    public void run(IProgressMonitor monitor) {
					    	monitor.beginTask("Solidifying ressources", doccount );
							for (int i = 0; i < root.getReferences().size(); i++) {
								solidifyLib(root.getReferences().get(i),dir,monitor);
							}
					    	monitor.done();
							MessageDialog.openInformation(window.getShell(), "Complete", "Solidification Complete");
					    }
					  });
					root.getReferences().setSolidFolderPath(fileName);
				} catch (Exception e) {
					MessageDialog.openError(window.getShell(), Messages.SolidifyAction_ErrorTitle, Messages.SolidifyAction_ErrorMessage);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	private int countDocuments(Library lib) {
		int count=0;
		for (Iterator iterator = lib.getSubLibraries().iterator(); iterator.hasNext();) {
			Library templib = (Library) iterator.next();
			count=+countDocuments(templib);
		}
		count=+lib.size();
		return count;
	}

	private void solidifyLib(final Library lib, File dir, IProgressMonitor monitor) {
			//here we create the relevant directories
			File newDir=new File(dir.getPath() + File.separator + lib.getName());
			if (!newDir.exists()){
				if (!newDir.mkdir()){
					//cas litigieux, à voir
					System.out.println("Can't create : " + newDir.getPath());
				}
			} else {
				if (!newDir.isDirectory()){
					//on efface et on crée
					if (!newDir.delete()){
						//cas litigieux, à voir
						System.out.println("Can't replace : " + newDir.getPath());
					} else {
						newDir.mkdir();
					}
				} 
			}
			// Convert the given monitor into a progress instance
			for (int i = 0; i < lib.getSubLibraries().size(); i++) {
				solidifyLib(lib.getSubLibraries().get(i),newDir,monitor);
			}
			SubMonitor progress = SubMonitor.convert(monitor, lib.size());
			// Advance the monitor by another 30%
			progress.beginTask("Solidifying library : " + lib.getName(), lib.size());
			for (int i = 0; i < lib.size(); i++) {
		        if (progress.isCanceled()) {return;};
		        IDocument doc=lib.get(i);
		        progress.subTask("Solidifying document : " + lib.get(i).getName());
		        if (doc instanceof IFile){
		        	//copy the file here, case for pdf and images
					File olddoc=new File(((IFile) doc).getFileName());
					File newdoc=new File(newDir.getPath() + File.separator + olddoc.getName());
					//file does not exists, we copy it
					try {
						copyFile(olddoc,newdoc);
						((IFile) doc).setFileName(newdoc.getPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("I/O problem");
						e.printStackTrace();
					} 
		        } else if (doc instanceof AudioDocument){
		        	//we need to treat this as a library, namely, create a folder and iterate over it
		        	AudioDocument audiodoc=(AudioDocument)doc;
		        	File mp3Folder=new File(audiodoc.getFolder());
					File newFolder=new File(newDir.getPath() + File.separator + mp3Folder.getName());
		        	newFolder.mkdir();
		        	for (Iterator iterator = audiodoc.iterator(); iterator
							.hasNext();) {
						AudioRessource docRessource = (AudioRessource) iterator
								.next();
						File olddoc=new File(docRessource.getFileName());
						File newdoc=new File(newFolder.getPath() + File.separator + olddoc.getName());
						//file does not exists, we copy it
						try {
							copyFile(olddoc,newdoc);
							docRessource.setFileName(newdoc.getPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("I/O problem");
							e.printStackTrace();
						} 
					}
		        	audiodoc.setFolder(newFolder.getPath());
		        }else {
		        	//TODO future implementations
					System.out.println("unhandled solidification type");
		        }
				progress.worked(1);
		        window.getShell().getDisplay().readAndDispatch();
			}
			progress.done();
	}

	public static void copyFile(File in, File out) 
	throws IOException 
	{
		FileChannel inChannel = new
		FileInputStream(in).getChannel();
		FileChannel outChannel = new
		FileOutputStream(out).getChannel();
		try {
			// magic number for Windows, 64Mb - 32Kb)
			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			long size = inChannel.size();
			long position = 0;
			while (position < size) {
				position += 
					inChannel.transferTo(position, maxCount, outChannel);
			} }

		catch (IOException e) {
			throw e;
		}
		finally {
			if (inChannel != null) inChannel.close();
			if (outChannel != null) outChannel.close();
		}
}
	
	
	@Override
	public void dispose() {
		
	}	

}
