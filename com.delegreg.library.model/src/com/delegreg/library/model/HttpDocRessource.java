package com.delegreg.library.model;

import com.delegreg.core.BaseContaineableContainer;


public class HttpDocRessource extends BaseContaineableContainer<IDocRessource> implements IDocRessource{


	private String url="";//$NON-NLS-1$

	public HttpDocRessource() {
	}

	public HttpDocRessource(String name) {
		this();
		this.setName(name);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public IDocument getDocument() {
		Object item=getContainer();
		while (!(item instanceof HttpDocument)) {
			item=((HttpDocRessource)item).getContainer();
		}
		return (IDocument) item;
	}	


	
	
}
