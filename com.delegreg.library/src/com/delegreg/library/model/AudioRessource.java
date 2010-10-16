package com.delegreg.library.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IFile;


public class AudioRessource extends BaseContaineableContainer<IDocRessource> implements IDocRessource,IFile{

	private String fileName;//$NON-NLS-1$
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public AudioRessource() {
		super();
	}

	public AudioRessource(String name) {
		this();
		this.setName(name);
	}


	@Override
	public IDocument getDocument() {
		// TODO Auto-generated method stub
		Object item=getContainer();
		if (item!=null){
			while (!(item instanceof AudioDocument)) {
				item=((AudioRessource)item).getContainer();
			}
		}
		return (IDocument) item;
	}	

	
}
