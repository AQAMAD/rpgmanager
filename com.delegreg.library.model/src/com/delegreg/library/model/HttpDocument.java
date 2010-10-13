package com.delegreg.library.model;

import com.delegreg.core.BaseContaineableContainer;

public class HttpDocument extends BaseContaineableContainer<IDocRessource> implements IDocument{


	private String url;
	
	public HttpDocument() {
	}

	public HttpDocument(String name) {
		this();
		this.setName(name);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
