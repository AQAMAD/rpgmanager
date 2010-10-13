package com.delegreg.rpgm.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Campaigns;
import com.delegreg.rpgm.ui.IImageKeys;

public class ClearAllAction extends Action implements IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.clearAll"; //$NON-NLS-1$

	public ClearAllAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText(Messages.ClearAllAction_Name);
		setToolTipText(Messages.ClearAllAction_Tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.CLEARALL));
	}


	public static String getID() {
		return ID;
	}


	public void run() {
		try {
			String[] btns={Messages.ClearAllAction_Confirm,Messages.ClearAllAction_Cancel};
			MessageDialog input=new MessageDialog(window.getShell(), Messages.ClearAllAction_ConfirmDialogTitle, null, Messages.ClearAllAction_ConfirmDialogMessage,MessageDialog.WARNING,btns , 1);
			int ret=input.open();
			if (ret==0){
				Application.setCurrentFileName(null);
				Application.setCurrentCampaigns(new Campaigns());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	@Override
	public void dispose() {
		
	}	

}
