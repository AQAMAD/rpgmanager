package com.delegreg.library.model;

import com.delegreg.core.BaseContaineableContainer;


public class Library extends BaseContaineableContainer<IDocument> implements ILibraryElement{
	
	private Libraries subLibraries;

	public Library() {
		super();
		subLibraries=new Libraries(this);
	}

	public Library(String name) {
		this();
		this.setName(name);
		
	}

	private String folder=""; //$NON-NLS-1$


	/**
	 * Setter of the property <tt>name</tt>
	 * @param name  The name to set.
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		super.setName(name);
		super.fireChanged();
	}


	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getFolder() {
		return folder;
	}

	public void setSubLibraries(Libraries subLibraries) {
		this.subLibraries = subLibraries;
	}

	public Libraries getSubLibraries() {
		return subLibraries;
	}
	
	
}
