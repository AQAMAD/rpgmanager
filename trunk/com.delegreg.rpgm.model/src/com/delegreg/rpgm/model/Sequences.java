package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;


public class Sequences extends BaseContaineableContainer<Sequence> {
	
	public Sequences() {
	}

	public Sequences(String name) {
		this();
		this.setName(name);
	}

	public Sequences(String name, IContainer<IContaineable> parent) {
		this(name);
		this.setContainer(parent);
	}
	
}
