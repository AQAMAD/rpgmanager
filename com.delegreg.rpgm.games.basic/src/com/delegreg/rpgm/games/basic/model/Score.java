package com.delegreg.rpgm.games.basic.model;

import com.delegreg.core.BaseContaineableNameable;

public class Score extends BaseContaineableNameable{

	private Category cat=null;
	
	private int value=0;
	
	private Bonuses bonii;
	
	Score(String name,Category category,int value){
		this(name,category);
		this.value=value;
	}

	Score(String name,Category category){
		this(name);
		this.cat=category;
	}
	
	Score(String name){
		this();
		this.setName(name);
	}

	Score(){
		super();
	}
	
	
}
