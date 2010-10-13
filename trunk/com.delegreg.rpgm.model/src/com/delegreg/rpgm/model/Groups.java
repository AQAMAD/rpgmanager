package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;


public class Groups extends BaseContaineableContainer<Group> {

	public Groups(String name) {
		this();
		this.setName(name);
	}

	public Groups() {
		super();
	}

	public Groups(String name, Campaign campaign) {
		this(name);
		this.setContainer(campaign);
	}

	public Groups(Group group) {
		this.setContainer(group);
	}
	
	
}
