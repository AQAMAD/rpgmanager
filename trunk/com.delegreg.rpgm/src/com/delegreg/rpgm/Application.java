package com.delegreg.rpgm;

import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.IContentListener;
import com.delegreg.rpgm.model.Campaigns;
import com.delegreg.rpgm.statblocks.DefaultStatBlock;
import com.delegreg.rpgm.statblocks.ui.DefaultStatBlockDetailsPage;
import com.delegreg.rpgm.ui.IImageKeys;
import com.delegreg.rpgm.ui.dialogs.PickWorkspaceDialog;
import com.delegreg.rpgm.ui.views.CampaignsView;

// TODO: Auto-generated Javadoc
/**
 * This class controls all aspects of the application's execution.
 */
public class Application implements IApplication {


	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = "com.delegreg.rpgm";	//$NON-NLS-1$
	
	/** The current campaigns. */
	private static Campaigns currentCampaigns;
	
	/** The stat blocks. */
	private static HashMap<Class,Class> statBlocks=new HashMap<Class,Class>();
	
	/** The dirty. */
	private static boolean dirty=false;
	
	/** The current file name. */
	private static String currentFileName=null; 
	
	/** The cl. */
	private static IContentListener cl=new IContentListener() {
		@Override
		public void contentChanged() {
			setDirty(true);
		}
	};;;
	

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			currentCampaigns=new Campaigns();
			currentCampaigns.addContentListener(cl);
			
			statBlocks.put(DefaultStatBlock.class, DefaultStatBlockDetailsPage.class);

		    // fetch the Location that we will be modifying 
		    Location instanceLoc = Platform.getInstanceLocation(); 
			
			// get what the user last said about remembering the workspace location 
			        boolean remember = PickWorkspaceDialog.isRememberWorkspace(); 
			        // get the last used workspace location 
			        String lastUsedWs = PickWorkspaceDialog.getLastSetWorkspaceDirectory(); 
			        // if we have a "remember" but no last used workspace, it's not much to remember 
			        if (remember && (lastUsedWs == null || lastUsedWs.length() == 0)) { 
			            remember = false; 
			        } 
			        // check to ensure the workspace location is still OK 
			        if (remember) { 
			            // if there's any problem whatsoever with the workspace, force a dialog which in its turn will tell them what's bad 
			            String ret = PickWorkspaceDialog.checkWorkspaceDirectory(Display.getDefault().getActiveShell(), lastUsedWs, false, false); 
			            if (ret != null) { 
			            remember = false; 
			            } 
			        } 
			        // if we don't remember the workspace, show the dialog 
			        if (!remember) { 
			            PickWorkspaceDialog pwd = new PickWorkspaceDialog(false,AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.BIGWORKSPACE).createImage()); 
			            int pick = pwd.open(); 
			            // if the user cancelled, we can't do anything as we need a workspace, so in this case, we tell them and exit 
			            if (pick == Window.CANCEL) { 
			            if (pwd.getSelectedWorkspaceLocation()  == null) { 
			                MessageDialog.openError(display.getActiveShell(), "Error", 
			                    "The application can not start without a workspace root and will now exit."); 
			                try { 
			                PlatformUI.getWorkbench().close(); 
			                } catch (Exception err) { 
			                } 
			                System.exit(0); 
			                return IApplication.EXIT_OK; 
			            } 
			            } 
			            else { 
			            // tell Eclipse what the selected location was and continue 
			            instanceLoc.set(new URL("file", null, pwd.getSelectedWorkspaceLocation()), false); 
			            } 
			        } 
			        else { 
			            // set the last used location and continue 
			            instanceLoc.set(new URL("file", null, lastUsedWs), false); 
			        }     			
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());

			if (returnCode == PlatformUI.RETURN_RESTART)
				{return IApplication.EXIT_RESTART;}
			else
				{return IApplication.EXIT_OK;}
		} finally {
			display.dispose();
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}

	/**
	 * Sets the current campaigns.
	 *
	 * @param theCampaigns the new current campaigns
	 */
	public static void setCurrentCampaigns(Campaigns theCampaigns) {
		if (currentCampaigns!=null){
			currentCampaigns.removeContentListener(cl);
		}
		currentCampaigns = theCampaigns;
		IWorkbenchWindow wbw=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (wbw!=null){
		IViewReference[] vr=wbw.getActivePage().getViewReferences();
		for (int i = 0; i < vr.length; i++) {
			IViewPart part=vr[i].getView(false);
			if (part instanceof CampaignsView){
				((CampaignsView)part).setCampaigns(currentCampaigns);
			}
		}
		setDirty(false);
		}
		currentCampaigns.addContentListener(cl);
	}

	/**
	 * Gets the current campaigns.
	 *
	 * @return the current campaigns
	 */
	public static Campaigns getCurrentCampaigns() {
		return currentCampaigns;
	}

	/**
	 * Sets the dirty.
	 *
	 * @param dirty the new dirty
	 */
	public static void setDirty(boolean dirty) {
		Application.dirty = dirty;
		Shell theshell=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		theshell.setText(getTitle());		
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public static String getTitle(){
		String title = Platform.getProduct().getName();
		if (currentFileName!=null){
			title = title + " - " + currentFileName;
		}else{
			title = title + " - " + "New Campaigns File.rpgm";
		}
		if (dirty){
			title=title + "*";
		}
		return title;
	}

	/**
	 * Checks if is dirty.
	 *
	 * @return true, if is dirty
	 */
	public static boolean isDirty() {
		return dirty;
	}

	/**
	 * Sets the current file name.
	 *
	 * @param currentFileName the new current file name
	 */
	public static void setCurrentFileName(String currentFileName) {
		Application.currentFileName = currentFileName;
	}

	/**
	 * Gets the current file name.
	 *
	 * @return the current file name
	 */
	public static String getCurrentFileName() {
		return currentFileName;
	}

	/**
	 * Sets the stat blocks.
	 *
	 * @param statBlocks the stat blocks
	 */
	public static void setStatBlocks(HashMap<Class, Class> statBlocks) {
		Application.statBlocks = statBlocks;
	}

	/**
	 * Gets the stat blocks.
	 *
	 * @return the stat blocks
	 */
	public static HashMap<Class, Class> getStatBlocks() {
		return statBlocks;
	}
}
