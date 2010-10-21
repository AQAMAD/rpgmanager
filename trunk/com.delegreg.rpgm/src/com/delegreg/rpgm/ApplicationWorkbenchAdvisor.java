package com.delegreg.rpgm;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.rpgm.core.RpgmPreferenceStore;
import com.delegreg.rpgm.core.Serializer;
import com.delegreg.rpgm.model.Campaigns;
import com.delegreg.rpgm.ui.actions.SaveAction;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "com.delegreg.rpgm.perspective"; //$NON-NLS-1$

    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}
	
	@Override
	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
		configurer.setSaveAndRestore(true);
	}

	@Override
	public IStatus restoreState(IMemento memento) {
		// TODO code window restore
		
		//System.out.println("restore");
		PreferenceStore ps=RpgmPreferenceStore.getInstance();
		try {
			ps.load();
		} catch (Exception e1) {
			//try to save
			try {
				ps.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			boolean bLoad=ps.getBoolean(RpgmPreferenceStore.LOAD_LAST_SAVED_CAMPAIGNS);
			if (bLoad){
				String filename=ps.getString(RpgmPreferenceStore.LAST_SAVED_CAMPAIGNS);
				if (!filename.equals("")){ //$NON-NLS-1$
					File f=new File(filename);
					if (f.exists()){
						Campaigns campaigns=(Campaigns)Serializer.getInstance().fromFileName(filename);
						Application.setCurrentCampaigns(campaigns);						
						Application.setCurrentFileName(f.getName());
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Display.getDefault().syncExec(new Runnable() {
				  public void run() {
						MessageDialog.openError(new Shell(), Messages.File_LoadErrorTitle, Messages.File_LoadErrorMessage);
						PreferenceStore ps=RpgmPreferenceStore.getInstance();
						ps.setValue(RpgmPreferenceStore.LAST_SAVED_CAMPAIGNS, ""); //$NON-NLS-1$
						try {
							ps.save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			
			e.printStackTrace();
		}
		//bypass window restoration if no campaigns file
		if (Application.getCurrentFileName()==null){
			return new Status(IStatus.CANCEL, Application.PLUGIN_ID, "");
		}
		
		
		return super.restoreState(memento);
	}

	@Override
	public boolean preShutdown() {
		//warning, not saved
		if (Application.isDirty()){
			String[] btns={Messages.UnsavedChanges_Save,Messages.UnsavedChanges_Ignore,Messages.UnsavedChanges_Cancel};
			MessageDialog input=new MessageDialog( getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow().getShell(), Messages.UnsavedChanges_Title, null, Messages.UnsavedChanges_Message,MessageDialog.WARNING,btns , 1);
			int ret=input.open();
			if (ret==0){
				SaveAction act=new SaveAction(getWorkbenchConfigurer().getWorkbench().getActiveWorkbenchWindow());
				act.run();
			} else if (ret==2){
				//cancel close
				return false;
			}		
		}
		return super.preShutdown();
	}
	

	
}
