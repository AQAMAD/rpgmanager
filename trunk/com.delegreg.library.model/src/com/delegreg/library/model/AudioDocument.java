package com.delegreg.library.model;

import com.delegreg.core.*;

public class AudioDocument extends BaseContaineableContainer<IDocRessource> implements IDocument{
	
	private String folderOrSite;
	

	public String getFolder() {
		return folderOrSite;
	}

	public void setFolder(String folder) {
		this.folderOrSite = folder;
	}

	public AudioDocument() {
	}

	public AudioDocument(String name) {
		this();
		this.setName(name);
	}



	
}
