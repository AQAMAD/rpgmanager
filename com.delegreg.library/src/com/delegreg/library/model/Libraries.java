package com.delegreg.library.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;
import com.delegreg.library.Messages;


public class Libraries extends BaseContaineableContainer<Library> implements ILibraryElement {

	private String solidFolderPath;
	
	public Libraries(IContainer<? extends IContaineable> parent) {
		setName(Messages.LibrariesName);
		setContainer(parent);
	}

	public void setSolidFolderPath(String solidFolderPath) {
		this.solidFolderPath = solidFolderPath;
		fireChanged();
	}

	public String getSolidFolderPath() {
		return solidFolderPath;
	}


}
