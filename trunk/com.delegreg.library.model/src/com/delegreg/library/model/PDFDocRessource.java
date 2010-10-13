package com.delegreg.library.model;

import com.delegreg.core.BaseContaineableContainer;


public class PDFDocRessource extends BaseContaineableContainer<IDocRessource> implements IDocRessource {

	private int pageNumber;
	
	public PDFDocRessource() {
		// TODO Auto-generated constructor stub
	}

	public PDFDocRessource(String name) {
		// TODO Auto-generated constructor stub
		this();
		this.setName(name);
	}

	public String getPdfDocument() {
		return ((PDFDocument) getDocument()).getFileName();
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public String getDocumentName() {
		return ((PDFDocument) getDocument()).getName();
	}

	@Override
	public IDocument getDocument() {
		// TODO Auto-generated method stub
		Object item=getContainer();
		while (!(item instanceof PDFDocument)) {
			item=((PDFDocRessource)item).getContainer();
		}
		return (IDocument) item;
	}

}
