package com.delegreg.rpgm.ui.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.core.RpgmPreferenceStore;
import com.delegreg.rpgm.core.Serializer;
import com.delegreg.rpgm.model.Campaigns;
import com.delegreg.rpgm.ui.IImageKeys;

public class SaveAsAction extends Action implements IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.saveAs"; //$NON-NLS-1$

	public SaveAsAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText(Messages.SaveAsAction_Name);
		setToolTipText(Messages.SaveAsAction_ToolTip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.SAVEAS));
	}


	public static String getID() {
		return ID;
	}


	public void run() {
		try {
			Campaigns campaigns=Application.getCurrentCampaigns();
			boolean saveMe=false;
			//save all open editors
			PlatformUI.getWorkbench().saveAllEditors(false);
			//get the xml from the root object
			String str=Serializer.getInstance().toXML(campaigns);
			//we got the XML, get a filename
			FileDialog dialog=new FileDialog(window.getShell(),SWT.SAVE);
			dialog.setFilterExtensions(new String[]{"*.rpgm"}); //$NON-NLS-1$
			dialog.setFilterNames(new String[]{"RPGM Campaigns files"}); //$NON-NLS-1$
			if (RpgmPreferenceStore.getInstance().getString(RpgmPreferenceStore.CAMPAIGNS_DIRECTORY)!=null){
				dialog.setFilterPath(RpgmPreferenceStore.getInstance().getString(RpgmPreferenceStore.CAMPAIGNS_DIRECTORY));
			}
			String fileName=dialog.open();
			if (fileName!=null) {
				//filename gotten, 
				//confirm rewrite
				File testFile=new File(fileName);
				if (testFile.exists()) {
					String[] btns={Messages.SaveAsAction_Overwrite,Messages.SaveAsAction_Cancel};
					MessageDialog input=new MessageDialog(window.getShell(), Messages.SaveAsAction_OverwriteDialogTitle, null, Messages.SaveAsAction_OverwriteDialogMessage,MessageDialog.WARNING,btns , 1);
					int ret=input.open();
					if (ret==0){
						saveMe=true;
					}
				} else {
					saveMe=true;
				}
				if (saveMe){
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
						out.write(str);
						out.close();
						RpgmPreferenceStore.getInstance().putValue(RpgmPreferenceStore.LAST_SAVED_CAMPAIGNS, fileName);
						if (RpgmPreferenceStore.getInstance().getBoolean(RpgmPreferenceStore.SAVE_CAMPAIGNS_DIRECTORY)){
							File f=new File(fileName);
							RpgmPreferenceStore.getInstance().putValue(RpgmPreferenceStore.SAVE_CAMPAIGNS_DIRECTORY, f.getParentFile().getPath());
						}
						RpgmPreferenceStore.getInstance().save();
						Application.setCurrentFileName((new File(fileName)).getName());
						Application.setDirty(false);
					} catch (IOException e) {
						MessageDialog.openError(window.getShell(), Messages.SaveAsAction_ErrorTitle, Messages.SaveAsAction_ErrorMessage + e.getLocalizedMessage());
						e.printStackTrace();
					}
				}
				
			}
			//System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	@Override
	public void dispose() {
		
	}	

}
