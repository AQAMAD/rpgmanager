package com.delegreg.rpgm.games.basic.model;

import com.delegreg.core.BaseContaineableContainer;


public class Bonuses extends BaseContaineableContainer<Bonus>{
	
	private Category cat=null;
	
	private int value=0;
	
	private boolean permanent=false;
	
	private boolean conditional = false;
	
	private Class appliesTo; 

}