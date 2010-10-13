package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;


public class Group extends BaseContaineableContainer<Actor> {

	private Groups subGroups;

	public Group() {
		subGroups=new Groups(this);
	}

	public Group(String name) {
		this();
		this.setName(name);
	}

	public Group(String name, IContainer<IContaineable> parent) {
		this(name);
		this.setContainer(parent);
	}

	public void setSubGroups(Groups subGroups) {
		this.subGroups = subGroups;
	}

	public Groups getSubGroups() {
		return subGroups;
	}

}
