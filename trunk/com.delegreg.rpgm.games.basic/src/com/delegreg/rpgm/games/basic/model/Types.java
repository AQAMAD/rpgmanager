package com.delegreg.rpgm.games.basic.model;

public class Types {
	
	public static final String Aberration="Aberration"; 
	public static final String Animal="Animal"; 
	public static final String Construct="Construct"; 
	public static final String Dragon="Dragon"; 
	public static final String Elemental="Elemental"; 
	public static final String Fey="Fey"; 
	public static final String Giant="Giant"; 
	public static final String Humanoid="Humanoid"; 
	public static final String MagicalBeast="Magical Beast"; 
	public static final String MonstrousHumanoid="Monstrous Humanoid"; 
	public static final String Ooze="Ooze"; 
	public static final String Outsider="Outsider"; 
	public static final String Plant="Plant"; 
	public static final String Undead="Undead"; 
	public static final String Vermin="Vermin"; 

	public static String[] getTypes(){
		return new String[]{Aberration,
							Animal,
							Construct,
							Dragon,
							Elemental,
							Fey,
							Giant,
							Humanoid,
							MagicalBeast,
							MonstrousHumanoid,
							Ooze,
							Outsider,
							Plant,
							Undead,
							Vermin};
	}
	
}
