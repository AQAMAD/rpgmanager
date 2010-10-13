package com.delegreg.rpgm.ui.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;
import com.delegreg.library.model.Libraries;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Groups;
import com.delegreg.rpgm.model.Locations;
import com.delegreg.rpgm.model.Scenarios;
import com.delegreg.rpgm.ui.IImageKeys;

public class DeleteItemAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.deleteItem"; //$NON-NLS-1$
	private IStructuredSelection selection;

	public DeleteItemAction(IWorkbenchWindow window) {
	  this.window = window;
	  setId(ID);
	  setActionDefinitionId(ID);
	  setText(Messages.DeleteItemAction_Name);
	  setToolTipText(Messages.DeleteItemAction_ToolTip);
	  setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.DELETEITEM));
	  window.getSelectionService().addSelectionListener(this);
	}

	
	public static String getID() {
		return ID;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		if (incoming instanceof IStructuredSelection) {
			selection = (IStructuredSelection) incoming;
			    if (selection.size() == 1){
			    	setEnabled(true);
			    	//on ne supprime pas les collections implicites
			    	if (selection.getFirstElement() instanceof Scenarios){
					    setEnabled(false);
			    	}
			    	if (selection.getFirstElement() instanceof Groups){
					    setEnabled(false);
			    	}
			    	if (selection.getFirstElement() instanceof Locations){
					    setEnabled(false);
			    	}
			    	if (selection.getFirstElement() instanceof Libraries){
					    setEnabled(false);
			    	}
			    }else if (selection.size() != 0){
				    setEnabled(true);
				}
			  } else {
			    // Other selections, for example containing text or of other kinds.
			    setEnabled(false);
			  }

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		  window.getSelectionService().removeSelectionListener(this);
	}

	public void run() {
		  try {
			  //remove the selected items from their parent
			  final Object[] items=selection.toArray();
			  ProgressMonitorDialog pd = new ProgressMonitorDialog(window.getShell());
			  try {
				  pd.run(false /*fork*/, true /*cancelable*/, new IRunnableWithProgress() {
					  public void run(IProgressMonitor monitor) {
						  monitor.beginTask("Deleting items", items.length );
						  for (int i = 0; i < items.length; i++) {
							  Object item=items[i];
							  if (item instanceof IContaineable){
								  IContainer<? extends IContaineable> parent=((IContaineable)item).getContainer();
								  parent.remove(item);
								  monitor.worked(1);
							  }
						  } 
						  monitor.done();
					  } 
				  });
			  } catch (Exception e) {
				  MessageDialog.openError(window.getShell(), "Delete error", "Error while deleting items");
				  e.printStackTrace();
			  }
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
  

		}	
	
}
