package com.delegreg.rpgm.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.BaseContaineable;
import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.library.model.IDocument;
import com.delegreg.library.model.Library;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.core.DocumentIndexer;
import com.delegreg.rpgm.core.RpgmRelationalAdapter;
import com.delegreg.rpgm.ui.IImageKeys;

public class NewItemWizard extends Wizard implements IWizard{

	private SelectItemTypeWizardPage selectTypePage;
	private NewItemDetailsWizardPage itemDetailsPage;
	private BaseContaineableContainer initialSelection;
	private boolean isComplete=false;
	/**
	 * @param isComplete the isComplete to set
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * @return the initialSelection
	 */
	public BaseContaineableContainer getInitialSelection() {
		return initialSelection;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		if (!isComplete){return false;}
		return super.canFinish();
	}

	private BaseContaineable model;
	private boolean isSideQuest;
	
	public void init(	IWorkbench workbench,	BaseContaineableContainer selection) 
	{
		initialSelection = selection;
	}
	
	public void addPages() {
		setWindowTitle(Messages.NewItemWizard_Name);
		selectTypePage = new SelectItemTypeWizardPage(Messages.NewItemWizard_Page1Name,
														Messages.NewItemWizard_Page1Text,
														AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.BIGITEM));
		addPage(selectTypePage);
		itemDetailsPage = new NewItemDetailsWizardPage(Messages.NewItemWizard_Page2Name,
														Messages.NewItemWizard_Page2Text,
														AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.BIGITEM));
		addPage(itemDetailsPage);
		selectTypePage.init(initialSelection);
	}
	/**
	 * This method is called by the wizard framework when
	 * the user presses the finish button
	 */
	public boolean performFinish() {
		// Perform the operation in a separate thread
		// so that the operation can be canceled
		itemDetailsPage.updateModel();
		try {
			getContainer().run(false, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)	throws	InvocationTargetException,	InterruptedException {
					performOperation(monitor);
				}
			});
		}
		catch (InvocationTargetException e) {
			// Log and report the exception
			e.printStackTrace();
			return false;
		}
		catch (InterruptedException e) {
			// User canceled, so stop but don't close wizard
			return false;
		}
		return true;
	}
	/**
	 * Called by the performFinish method on a separate thread
	 * to add the new item to the selectedItem
	 *
	 * @param monitor the progress monitor
	 */
	private void performOperation(IProgressMonitor monitor) {
		//get the model and the parent, and add them to one another
		//System.out.println("po"); //$NON-NLS-1$
		BaseContaineableContainer parent=getInitialSelection();
		RpgmRelationalAdapter adapter=new RpgmRelationalAdapter(parent);
		adapter.addSubObject(model);
		if (parent instanceof Library && model instanceof IDocument){
			//((Library)parent).add( (IDocument)model );
			if (model instanceof IDocument) {
				IDocument doc=(IDocument)model;
				DocumentIndexer indexer=new DocumentIndexer();
				indexer.index(doc);
			}
		}
	}

	public void setModel(BaseContaineable model) {
		this.model = model;
		isSideQuest=false;
	}

	public void setModel(BaseContaineable model,boolean isSideQuest) {
		this.model = model;
		this.isSideQuest=isSideQuest;
	}

	public Object getModel() {
		return model;
	}


}
