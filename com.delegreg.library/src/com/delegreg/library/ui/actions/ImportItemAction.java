package com.delegreg.library.ui.actions;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.library.Activator;
import com.delegreg.library.Messages;
//import com.delegreg.rpgm.core.RpgmRelationalAdapter;
import com.delegreg.library.util.LibrarySerializer;
import com.delegreg.library.ui.IImageKeys;
import com.delegreg.core.BaseContaineable;
import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;

public class ImportItemAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.importItem"; //$NON-NLS-1$
	private Object selectedItem;

	public ImportItemAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText(Messages.ImportItemAction_Name);
		setToolTipText(Messages.ImportItemAction_Tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, IImageKeys.IMPORT));
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
		if (selectedItem!=null){
//			RpgmRelationalAdapter adapter=new RpgmRelationalAdapter((BaseContaineableContainer) selectedItem); 
//			if (adapter.getContentTypes().size()>0) {
//				setEnabled(true);
//			} else if (adapter.getImportTypes().size()>0) {
//				setEnabled(true);
//			} else {
//				setEnabled(false);
//			}		
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
		IWorkbenchPage page = window.getActivePage();
		try {
			//get the xml from the root object
			//we got the XML, get a filename
			FileDialog dialog=new FileDialog(window.getShell(),SWT.OPEN);
			dialog.setFilterExtensions(new String[]{"*.rpgm.fragment"}); //$NON-NLS-1$
			dialog.setFilterNames(new String[]{"RPGM Fragment files"}); //$NON-NLS-1$
			String fileName=dialog.open();
			if (fileName!=null) {
				//action validated
				try {
					//what type of item is this
					Object newobj=LibrarySerializer.getInstance().fromFileName(fileName);
					if (newobj==null){return;}
					//can i add it to the currently selected item
//					RpgmRelationalAdapter adapter=new RpgmRelationalAdapter((BaseContaineableContainer) selectedItem);
//					if (adapter.doesAcceptSubItem((IContaineable) newobj)){
//						//add the subitem
//						String[] btns={Messages.ImportItemAction_Add,Messages.ImportItemAction_Cancel};
//						MessageDialog input=new MessageDialog(window.getShell(), Messages.ImportItemAction_DialogTitle, null, Messages.ImportItemAction_DialogMessage,MessageDialog.INFORMATION,btns , 1);
//						int ret=input.open();
//						if (ret==0){
//							adapter.addSubObject((IContaineable) newobj);
//						}
//					}else if (adapter.doesImportSubItem((IContaineable)newobj)){
//						//import the subitem
//						String[] btns={Messages.ImportItemAction_Import,Messages.ImportItemAction_Cancel};
//						MessageDialog input=new MessageDialog(window.getShell(), Messages.ImportItemAction_DialogTitle, null, Messages.ImportItemAction_AlternateDialogMessage,MessageDialog.WARNING,btns , 1);
//						int ret=input.open();
//						if (ret==0){
//							adapter.importSubObject((BaseContaineable) newobj);
//						}
//					}
					//Application.setCurrentCampaigns(newRoot);
				} catch (IOException e) {
					MessageDialog.openError(window.getShell(), Messages.ImportItemAction_ErrorTitle, Messages.ImportItemAction_ErrorMessage);
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

}
