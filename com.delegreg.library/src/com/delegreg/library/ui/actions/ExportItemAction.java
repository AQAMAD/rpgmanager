package com.delegreg.library.ui.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

import com.delegreg.core.INameable;
import com.delegreg.library.model.Libraries;
import com.delegreg.library.Activator;
import com.delegreg.library.Messages;
import com.delegreg.library.util.LibrarySerializer;
import com.delegreg.library.ui.IImageKeys;

public class ExportItemAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.exportItem"; //$NON-NLS-1$
	private Object selectedItem;

	public ExportItemAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText(Messages.ExportItemAction_Name);
		setToolTipText(Messages.ExportItemAction_Tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, IImageKeys.EXPORT ));
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
		if (selectedItem instanceof Libraries) {
			setEnabled(false);
		} else {
			setEnabled(true);
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
			boolean saveMe=false;
			//get the xml from the root object
			String str=LibrarySerializer.getInstance().toXML(selectedItem);
			//we got the XML, get a filename
			FileDialog dialog=new FileDialog(window.getShell(),SWT.SAVE);
			dialog.setFilterExtensions(new String[]{"*.rpgm.fragment"}); //$NON-NLS-1$
			dialog.setFilterNames(new String[]{"RPGM Fragment files"}); //$NON-NLS-1$
			dialog.setFileName(((INameable)selectedItem).getName());
			String fileName=dialog.open();
			if (fileName!=null) {
				//filename gotten, 
				//confirm rewrite
				File testFile=new File(fileName);
				if (testFile.exists()) {
					String[] btns={Messages.ExportItemAction_OverWrite,Messages.ExportItemAction_Cancel};
					MessageDialog input=new MessageDialog(window.getShell(), Messages.ExportItemAction_DialogTitle, null, Messages.ExportItemAction_DialogMessage,MessageDialog.WARNING,btns , 1);
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
					} catch (IOException e) {
						MessageDialog.openError(window.getShell(), Messages.ExportItemAction_ErrorTitle, Messages.ExportItemAction_ErrorMessage + e.getLocalizedMessage());
						e.printStackTrace();
					}
				}
				
			}
			//System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

}
