package com.delegreg.rpgm.games.d20.model;

public class Sizes {
	
	public static final int Fine=-8;
	public static final int Diminutive=-4;
	public static final int Tiny=-2;
	public static final int Small=-1;
	public static final int Medium=0;
	public static final int Large=1;
	public static final int Huge=2;
	public static final int Gargantuan=4;
	public static final int Colossal=8;
	
	public static int[] getSizeCodes(){
		return new int[]{Fine,Diminutive,Tiny,Small,Medium,Large,Huge,Gargantuan,Colossal};
	}
	
	public static String[] getSizeNames(){
		return new String[]{"Fine","Diminutive","Tiny","Small","Medium","Large","Huge","Gargantuan","Colossal"};
	}
	
}
