package com.delegreg.core.ui.wizardpages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;

import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;

public interface INewContaineableDetailPage extends IWizardPage {

	void setModel(IContaineable model);
	void setParent(IContainer parent);
	void onEnterPage();
	boolean canFinish();
	
}
