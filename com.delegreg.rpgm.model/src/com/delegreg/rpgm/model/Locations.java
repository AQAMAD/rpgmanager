package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;

public class Locations extends BaseContaineableContainer<Location> {

	
	public Locations(Campaign campaign) {
		setContainer(campaign);
	}

	public Locations(Sequence seq) {
		setContainer(seq);
	}

	public Locations(Location location) {
		setContainer(location);
	}

}
