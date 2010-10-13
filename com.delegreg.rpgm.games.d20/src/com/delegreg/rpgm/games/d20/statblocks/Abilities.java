package com.delegreg.rpgm.games.d20.statblocks;


public class Abilities {

	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;

	public Abilities() {
		super();
		strength=10;
		constitution=10;
		dexterity=10;
		intelligence=10;
		wisdom=10;
		charisma=10;
	}
	
	public int getStrength() {
		return strength;
	}


	public void setStrength(int strength) {
		this.strength = strength;
	}


	public int getDexterity() {
		return dexterity;
	}


	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}


	public int getConstitution() {
		return constitution;
	}


	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}


	public int getIntelligence() {
		return intelligence;
	}


	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}


	public int getWisdom() {
		return wisdom;
	}


	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}


	public int getCharisma() {
		return charisma;
	}


	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	public static int getModifier(int ability){
		if (ability==0){
			return 0;
		}else if (ability>=10)
		{
			return (ability-10)/2;
		}else
		{
			return -(11-ability)/2;
		}
	}
	
}
