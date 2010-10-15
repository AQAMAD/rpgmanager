/**
 * 
 */
package com.delegreg.core.ui.wizardpages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;

/**
 * @author Delegreg
 *
 */
public class SummaryPage extends WizardPage implements
		INewContaineableDetailPage {

	private Composite container;

	public SummaryPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	/* (non-Javadoc)
	 * @see com.delegreg.core.ui.wizardpages.INewContaineableDetailPage#onEnterPage()
	 */
	@Override
	public void onEnterPage() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.delegreg.core.ui.wizardpages.INewContaineableDetailPage#setModel(com.delegreg.core.IContaineable)
	 */
	@Override
	public void setModel(IContaineable model) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.delegreg.core.ui.wizardpages.INewContaineableDetailPage#setParent(com.delegreg.core.IContainer)
	 */
	@Override
	public void setParent(IContainer parent) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent,SWT.NULL);
		container.setLayout(new GridLayout(1, false));

		setControl(container);
		{
			Label lbl1 = new Label(container, SWT.NONE);
		}
	}

	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 */
	@Override
	public boolean canFlipToNextPage() {
		return false;
	}	
}
