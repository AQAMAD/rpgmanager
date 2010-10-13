package com.delegreg.rpgm.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.ui.IImageKeys;
import com.delegreg.rpgm.ui.dialogs.PickWorkspaceDialog;

public class SwitchWorkspaceAction extends Action  implements IWorkbenchAction{

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.switchWorkspace"; //$NON-NLS-1$
	public SwitchWorkspaceAction(IWorkbenchWindow window) { 
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText("Switch Workspace");
		setToolTipText("Change your actual work location");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.WORKSPACE));
	} 
	@Override 
	public void run() { 
		PickWorkspaceDialog pwd = new PickWorkspaceDialog(true, AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.BIGWORKSPACE).createImage()); 
		int pick = pwd.open(); 
		if (pick == Dialog.CANCEL) 
			return; 
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Switch Workspace", "The client will now restart with the new workspace"); 
		// restart client 
		PlatformUI.getWorkbench().restart(); 
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	} 

}
