package com.delegreg.library.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IFile;

public class ImageDocument extends BaseContaineableContainer<IDocRessource> implements IDocument,IFile{
	
	private String fileName;
	
	public ImageDocument() {
	}

	public ImageDocument(String name) {
		this();
		this.setName(name);
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}




	
}
