package com.delegreg.library.model;

import com.delegreg.core.BaseContaineableContainer;

public class PDFDocument extends BaseContaineableContainer<IDocRessource> implements IDocument,com.delegreg.core.IFile {


	private String fileName;
	
	public PDFDocument() {
	}

	public PDFDocument(String name) {
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
