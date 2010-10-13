package com.delegreg.rpgm.games.basic.model;

import java.util.ArrayList;

public class D20 {
	
	private static D20 instance;

	private ArrayList<Category> scoreCategories;
	
	private ArrayList<Category> bonusCategories;
	
	private ArrayList<Category> rollCategories;
	
	private D20(){
		scoreCategories=new ArrayList<Category>();
		scoreCategories.add(new Category("Abilities"));
		
		bonusCategories=new ArrayList<Category>();
		bonusCategories.add(new Category("Ability bonus"));

		rollCategories=new ArrayList<Category>();
		rollCategories.add(new Category("Ability roll"));
	}
	
	
	public static D20 getInstance() {
		if (instance == null){
			instance=new D20();
		}
		return instance;
	}
	

}
