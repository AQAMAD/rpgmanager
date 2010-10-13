package com.delegreg.rpgm.games.basic.statblocks;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;

import com.delegreg.core.BaseContaineableNameable;
import com.delegreg.rpgm.games.basic.Activator;
import com.delegreg.rpgm.games.basic.Messages;
import com.delegreg.rpgm.games.basic.model.Sizes;
import com.delegreg.rpgm.games.basic.ui.IImageKeys;
import com.delegreg.rpgm.model.IStatBlock;

public class D20StatBlock extends BaseContaineableNameable implements IStatBlock {

	private int size=Sizes.Medium;

	private String type="";
	
	private ArrayList<String> subTypes=new ArrayList<String>();

	private String HD="";

	private int HP=0;

	private int init=0;

	private ArrayList<String> speeds=new ArrayList<String>();
	
	private int AC=0;

	private String ACDetails="";

	private int FFAC=0;

	private int TAC=0;

	private int BAB=0;

	private int grapple=0;
	
	private ArrayList<String> attacks=new ArrayList<String>();

	private ArrayList<String> fullAttacks=new ArrayList<String>();
	
	private String spaceReach="";

	private Abilities abilities=new Abilities();

	private String specialAttacks="";

	private String specialQualities="";

	private Saves saves=new Saves();

	private Skills skills=new Skills();
	
	private Feats feats=new Feats();
	
	private String environment="";

	private String organisation="";

	private int challengeRating=0;

	private String treasure="";
	
	private String alignment="";
	
	private String advancment="";
	
	private int levelAdjustment=0;
	
	static Image icon=Activator.getImageDescriptor(IImageKeys.D20).createImage();;

	static String STABLOCK_NLSKEY="D20StatBlock";
	
	public D20StatBlock(String name) {
		this();
		this.setName(name);
	}

	public D20StatBlock() {
		super();
	}
	
	public Abilities getAbilities() {
		return abilities;
	}

	@Override
	public Image ClassIcon() {
		// TODO Auto-generated method stub
		return icon;
	}

	@Override
	public String ClassName() {
		// TODO Auto-generated method stub
		return Messages.getByName(STABLOCK_NLSKEY);
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setHD(String hD) {
		HD = hD;
	}

	public String getHD() {
		return HD;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getHP() {
		return HP;
	}

	public void setInit(int init) {
		this.init = init;
	}

	public int getInit() {
		return init;
	}

	public void setBAB(int bAB) {
		BAB = bAB;
	}

	public int getBAB() {
		return BAB;
	}

	public void setGrapple(int grapple) {
		this.grapple = grapple;
	}

	public int getGrapple() {
		return grapple;
	}

	public void setAC(int aC) {
		AC = aC;
	}

	public int getAC() {
		return AC;
	}

	public void setACDetails(String aCDetails) {
		ACDetails = aCDetails;
	}

	public String getACDetails() {
		return ACDetails;
	}

	public void setFFAC(int fFAC) {
		FFAC = fFAC;
	}

	public int getFFAC() {
		return FFAC;
	}

	public void setTAC(int tAC) {
		TAC = tAC;
	}

	public int getTAC() {
		return TAC;
	}

	public void setSpaceReach(String spaceReach) {
		this.spaceReach = spaceReach;
	}

	public String getSpaceReach() {
		return spaceReach;
	}

	public void setSpecialAttacks(String specialAttacks) {
		this.specialAttacks = specialAttacks;
	}

	public String getSpecialAttacks() {
		return specialAttacks;
	}

	public void setSpecialQualities(String specialQualities) {
		this.specialQualities = specialQualities;
	}

	public String getSpecialQualities() {
		return specialQualities;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setChallengeRating(int challengeRating) {
		this.challengeRating = challengeRating;
	}

	public int getChallengeRating() {
		return challengeRating;
	}

	public void setTreasure(String treasure) {
		this.treasure = treasure;
	}

	public String getTreasure() {
		return treasure;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAdvancment(String advancment) {
		this.advancment = advancment;
	}

	public String getAdvancment() {
		return advancment;
	}

	public void setLevelAdjustment(int levelAdjustment) {
		this.levelAdjustment = levelAdjustment;
	}

	public int getLevelAdjustment() {
		return levelAdjustment;
	}

	
}
