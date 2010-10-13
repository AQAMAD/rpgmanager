package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContainer;


public class StatBlocks extends BaseContaineableContainer<IStatBlock> {
	
	public StatBlocks() {
	}

	public StatBlocks(String name) {
		this();
		this.setName(name);
	}

	public StatBlocks(String name, IContainer<StatBlocks> parent) {
		this(name);
		this.setContainer(parent);
	}
	
}
