package com.delegreg.rpgm.ui.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
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
import com.delegreg.rpgm.ui.views.CampaignsView;

public class SaveAction extends Action implements IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.save"; //$NON-NLS-1$
	
	private SaveAsAction saa;
	
	public SaveAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText(Messages.SaveAction_Name);
		setToolTipText(Messages.SaveAction_ToolTip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.SAVE));
		saa=new SaveAsAction(window);
	}


	public static String getID() {
		return ID;
	}


	public void run() {
		try {
			Campaigns campaigns=Application.getCurrentCampaigns();
			//save all open editors
			PlatformUI.getWorkbench().saveAllEditors(false);
			//get the xml from the root object
			String str=Serializer.getInstance().toXML(campaigns);
			String fileName=Application.getCurrentFileName();
			if (fileName!=null){
				BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
				out.write(str);
				out.close();
				RpgmPreferenceStore.getInstance().putValue(RpgmPreferenceStore.LAST_SAVED_CAMPAIGNS, fileName);
				RpgmPreferenceStore.getInstance().save();
				Application.setCurrentFileName((new File(fileName)).getName());
				Application.setDirty(false);
			} else {
				saa.run();
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
