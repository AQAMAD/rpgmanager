package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;


public class Scenarios extends BaseContaineableContainer<Scenario>  {

	public Scenarios(Campaign campaign) {
		// TODO Auto-generated constructor stub
		setContainer(campaign);

	}

	public Scenarios(String name, Campaign campaign) {
		this(campaign);
		setName(name); 
	}

}
