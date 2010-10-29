package com.delegreg.rpgm.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;
import com.delegreg.core.ui.wizardpages.INewContaineableDetailPage;
import com.delegreg.core.ui.wizardpages.SummaryPage;
import com.delegreg.library.model.IDocument;
import com.delegreg.library.model.Library;
import com.delegreg.library.ui.wizardpages.NewLibraryElementPageFactory;
import com.delegreg.library.ui.wizardpages.NewLibraryItemPage;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.library.util.DocumentIndexer;
import com.delegreg.rpgm.core.RpgmRelationalAdapter;
import com.delegreg.rpgm.ui.IImageKeys;

public class NewItemWizard extends Wizard implements IWizard{

	private SelectItemTypeWizardPage firstPage;
	private IContainer initialSelection;
	private NewItemDetailsWizardPage defaultpage=null;
	private NewLibraryItemPage libraryPage=null;
	private IContaineable model;
	private SummaryPage summaryPage;

	public void addPages() {
		setWindowTitle(Messages.NewItemWizard_Name);
		firstPage = new SelectItemTypeWizardPage(Messages.NewItemWizard_Page1Name,
				Messages.NewItemWizard_Page1Text,
				AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.BIGITEM));
		addPage(firstPage);


		summaryPage = new SummaryPage("Summary",
				"Click Finish to add your element",
				AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.BIGITEM));
		addPage(summaryPage);

		firstPage.init(initialSelection);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		if (model==null){
			return false;
		}else if (model instanceof Library && !(libraryPage==null)){
			return libraryPage.canFinish();
		}else if (!(defaultpage==null)){
			return defaultpage.canFinish();
		}
		return false;
	}

	/**
	 * @return the initialSelection
	 */
	public IContainer getInitialSelection() {
		return initialSelection;
	}

	public Object getModel() {
		return model;
	}
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if(page == firstPage)
		{
			INewContaineableDetailPage newPage;
			if (model instanceof Library){
				if (libraryPage==null){
					libraryPage=(NewLibraryItemPage) NewLibraryElementPageFactory.getWizardPage(Messages.NewItemWizard_Page2Name,
							Messages.NewItemWizard_Page2Text,
							AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.BIGITEM), 
							initialSelection, 
							model);
					libraryPage.setWizard(this);
					addPage(libraryPage);
					libraryPage.setPreviousPage(firstPage);
				} 
				return libraryPage;
			} else { 
				if (defaultpage==null){
					defaultpage=new NewItemDetailsWizardPage(Messages.NewItemWizard_Page2Name,
							Messages.NewItemWizard_Page2Text,
							AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.BIGITEM));
					defaultpage.setWizard(this);
					addPage(defaultpage);
					defaultpage.setPreviousPage(firstPage);
				} 
				return defaultpage;
			}
			//newPage.setPageComplete(true); 
		} else if (page == summaryPage){
			//only two pages on this wizard
			return null;
		} else {
			//only two pages on this wizard
//			summaryPage.setPreviousPage(page);
//			return summaryPage;
			return null;
		}
		//return super.getNextPage(page);
	}
	public void init(	IWorkbench workbench,	IContainer item) 
	{
		initialSelection = item;
	}

	/**
	 * This method is called by the wizard framework when
	 * the user presses the finish button
	 */
	public boolean performFinish() {
		// Perform the operation in a separate thread
		// so that the operation can be canceled
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
		IContainer parent=getInitialSelection();
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

	public void setModel(IContaineable model) {
		this.model = model;
	}



}
