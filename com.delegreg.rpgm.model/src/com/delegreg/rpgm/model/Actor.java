package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.library.model.IDocOrRessource;


public class Actor extends BaseContaineableContainer<StatBlocks> {

	private IDocOrRessource docRessource;
	private String description=""; //$NON-NLS-1$
	
	public Actor() {
		// TODO Auto-generated constructor stub
		statBlocks=new StatBlocks("Stats", this);
	}

	public Actor(String name) {
		// TODO Auto-generated constructor stub
		this();
		this.setName(name);
	}

	public void setDocRessource(IDocOrRessource docRessource) {
		this.docRessource = docRessource;
	}

	public IDocOrRessource getDocRessource() {
		return docRessource;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setStatBlocks(StatBlocks statBlocks) {
		this.statBlocks = statBlocks;
	}

	public StatBlocks getStatBlocks() {
		return statBlocks;
	}

	private StatBlocks statBlocks;
	
	

}
