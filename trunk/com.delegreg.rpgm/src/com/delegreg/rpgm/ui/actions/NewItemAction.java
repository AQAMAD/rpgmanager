package com.delegreg.rpgm.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.BaseContainer;
import com.delegreg.core.IContainer;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.core.RpgmRelationalAdapter;
import com.delegreg.rpgm.ui.IImageKeys;
import com.delegreg.rpgm.ui.views.CampaignsView;
import com.delegreg.rpgm.ui.wizards.NewItemWizard;

public class NewItemAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.newItem"; //$NON-NLS-1$
	private IStructuredSelection selection;
	private IContainer item;
	private CampaignsView updateableViewer;

	public NewItemAction(IWorkbenchWindow window) {
	  this.window = window;
	  setId(ID);
	  setActionDefinitionId(ID);
	  setText(Messages.NewItemAction_Name);
	  setToolTipText(Messages.NewItemAction_ToolTip);
	  setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.NEWITEM));
	  window.getSelectionService().addSelectionListener(this);
	}

	
	public static String getID() {
		return ID;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		if (incoming instanceof IStructuredSelection) {
			if (part instanceof CampaignsView) {
				updateableViewer= (CampaignsView)part;
			}
			selection = (IStructuredSelection) incoming;
			    if (selection.size() == 1){
			    	setEnabled(true);
			    	//on n'ajoute rien dans les acteurs
			    	item=(BaseContaineableContainer) selection.getFirstElement();
			    	RpgmRelationalAdapter adapter=new RpgmRelationalAdapter((BaseContaineableContainer) item);
			    	if (adapter.getContentTypes().size()==0){
					    setEnabled(false);
			    	}
			    }else if (selection.size() != 0){
				    setEnabled(false);
				}else if (selection.size() == 0){
			    	item=(BaseContainer) updateableViewer.getCampaigns();
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
			IWorkbenchPage page = window.getActivePage();
			  NewItemWizard wizard = new NewItemWizard();
			  wizard.init(window.getWorkbench(),(BaseContainer) item);
			  WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
			  dialog.open();		  
			  //force it
			  updateableViewer.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
  

		}	
	
}
