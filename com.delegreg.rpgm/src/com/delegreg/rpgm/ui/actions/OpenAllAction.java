/**
 * 
 */
package com.delegreg.rpgm.ui.actions;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.core.Serializer;
import com.delegreg.rpgm.model.Campaigns;
import com.delegreg.rpgm.ui.IImageKeys;

/**
 * @author Administrateur
 *
 */
public class OpenAllAction extends Action implements IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.openAll"; //$NON-NLS-1$

	public OpenAllAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setText(Messages.OpenCampaignAction_Name);
		setToolTipText(Messages.OpenCampaignAction_Tooltip);
		setImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin(
						Application.PLUGIN_ID, IImageKeys.OPENALL)); //$NON-NLS-1$
	}


	public void run() {
		try {
			//get the xml from the root object
			//we got the XML, get a filename
			FileDialog dialog=new FileDialog(window.getShell(),SWT.OPEN);
			dialog.setFilterExtensions(new String[]{"*.rpgm"}); //$NON-NLS-1$
			dialog.setFilterNames(new String[]{"RPGM Campaigns files"}); //$NON-NLS-1$
			String fileName=dialog.open();
			if (fileName!=null) {
				//action validated
				try {
					Campaigns newRoot=(Campaigns)Serializer.getInstance().fromFileName(fileName);
					Application.setCurrentFileName((new File(fileName)).getName());
					Application.setCurrentCampaigns(newRoot);
				} catch (IOException e) {
					MessageDialog.openError(window.getShell(), Messages.OpenAllAction_ErrorTitle, Messages.OpenAllAction_ErrorMessage);
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}	
	
	
}
