package com.delegreg.library.ui.wizardpages;

import org.eclipse.jface.resource.ImageDescriptor;

import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;
import com.delegreg.core.ui.wizardpages.INewContaineableDetailPage;
import com.delegreg.library.model.Library;

public class NewLibraryElementPageFactory {
	
	public static INewContaineableDetailPage getWizardPage(String pageName,String title,ImageDescriptor titleImage,IContainer parent,IContaineable model){
		if (model instanceof Library){
			NewLibraryItemPage wp=new NewLibraryItemPage(pageName, title, titleImage);
			wp.setModel(model);
			wp.setParent(parent);
			return wp;
		}
		return null;
	}
	
	
}
