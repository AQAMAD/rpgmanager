package com.delegreg.rpgm.model;

import com.delegreg.core.BaseRootContainer;
import com.delegreg.library.model.Libraries;

public class Campaigns extends BaseRootContainer<Campaign> {

	
	private Libraries references;

	public Libraries getReferences() {
		return references;
	}

	public void setReferences(Libraries references) {
		this.references = references;
	}

	public Campaigns() {
		super();
		references=new Libraries(this);
	}

	@Override
	public boolean add(Campaign e) {
		// TODO Auto-generated method stub
		e.setContainer(this);
		return super.add(e);
	}
	
}
