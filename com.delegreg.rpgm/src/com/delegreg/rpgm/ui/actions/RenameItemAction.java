package com.delegreg.rpgm.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.INameable;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.ui.IImageKeys;

public class RenameItemAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.renameItem"; //$NON-NLS-1$
	private Object selectedItem;

	public RenameItemAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText(Messages.RenameItemAction_Name);
		setToolTipText(Messages.RenameItemAction_Tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, IImageKeys.RENAME));
		window.getSelectionService().addSelectionListener(this);
	}

	public static String getID() {
		return ID;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		// TODO Auto-generated method stub
		if (incoming instanceof IStructuredSelection) {
			selectedItem = ((IStructuredSelection) incoming).getFirstElement();
			processSelection();
		} else {
			// Other selections, for example containing text or of other kinds.
			setEnabled(false);
		}

	}

	public void setSelection(Object item){
		selectedItem=item;
		processSelection();
	}
	
	public void processSelection(){
		if (selectedItem instanceof INameable) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}		
	}
	
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		window.getSelectionService().removeSelectionListener(this);
	}

	public void run() {
		if (selectedItem instanceof INameable) {
			INameable item=(INameable)selectedItem;
			InputDialog id=new InputDialog(window.getShell(),Messages.RenameItemAction_DialogTitle , Messages.RenameItemAction_DialogMessage, item.getName(),
					new IInputValidator(){

						@Override
						public String isValid(String newText) {
							if (newText==null){
								return Messages.RenameItemAction_ErrorMessage;
							}else{
								return null;
							}
						}
				
			});
			if (id.open()==id.OK){
				item.setName(id.getValue());
				Application.getCurrentCampaigns().fireContentChanged();
			}
		}
	}

}
