package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.library.model.IDocOrRessource;
import com.delegreg.rpgm.Messages;

// TODO: Auto-generated Javadoc
/**
 * The Class Campaign. represents the base work unit in RPGM : the Campaign.
 */
public class Campaign extends BaseContaineableContainer<IContaineable> {

	/**
	 * The actors.
	 */
	private Groups actors;

	/** The description. */
	private String description = "";//$NON-NLS-1$
	
	/** The doc ressource. */
	private IDocOrRessource docRessource;
	
	/** The game master. */
	private String gameMaster = "";//$NON-NLS-1$

	/** The locations. */
	private Locations locations;
	
	/** The scenarios. */
	private Scenarios scenarios;
	
	/** The side quests. */
	private Scenarios sideQuests;

	/**
	 * Instantiates a new campaign.
	 */
	public Campaign(){
		scenarios=new Scenarios(Messages.Campaign_Scenarios,this);
		sideQuests=new Scenarios(Messages.Campaign_SideQuests,this);
		locations=new Locations(this);
		actors=new Groups(Messages.Campaign_Actors,this);
	}

	/**
	 * Instantiates a new campaign.
	 * 
	 * @param name the name
	 */
	public Campaign(String name) {
		this();
		this.setName(name);
	}

	/**
	 * Gets the actors.
	 * 
	 * @return the actors
	 */
	public Groups getActors() {
		return actors;
	}


	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the doc ressource.
	 * 
	 * @return the doc ressource
	 */
	public IDocOrRessource getDocRessource() {
		return docRessource;
	}

	/**
	 * Getter of the property <tt>GM</tt>.
	 * 
	 * @return  Returns the gm.
	 * 
	 * @uml.property  name="gameMaster"
	 */
	public String getGameMaster() {
		return gameMaster;
	}

	/**
	 * Getter of the property <tt>locations</tt>.
	 * 
	 * @return  Returns the locations.
	 * 
	 * @uml.property  name="locations"
	 */
	public Locations getLocations() {
		return locations;
	}


	/**
	 * Getter of the property <tt>scenarios</tt>.
	 * 
	 * @return  Returns the scenarios.
	 * 
	 * @uml.property  name="scenarios"
	 */
	public Scenarios getScenarios() {
		return scenarios;
	}

	/**
	 * Gets the side quests.
	 * 
	 * @return the side quests
	 */
	public Scenarios getSideQuests() {
		return sideQuests;
	}
	

	/**
	 * Sets the actors.
	 * 
	 * @param actors the new actors
	 */
	public void setActors(Groups actors) {
		this.actors = actors;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
		fireChanged();
	}

	/**
	 * Sets the doc ressource.
	 * 
	 * @param docRessource the new doc ressource
	 */
	public void setDocRessource(IDocOrRessource docRessource) {
		this.docRessource = docRessource;
	}

	/**
	 * Setter of the property <tt>GM</tt>.
	 * 
	 * @param gameMaster the game master
	 * 
	 * @uml.property  name="gameMaster"
	 */
	public void setGameMaster(String gameMaster) {
		this.gameMaster = gameMaster;
		fireChanged();
	}

	/**
	 * Setter of the property <tt>locations</tt>.
	 * 
	 * @param locations  The locations to set.
	 * 
	 * @uml.property  name="locations"
	 */
	public void setLocations(Locations locations) {
		this.locations = locations;
	}
	

	/**
	 * Setter of the property <tt>scenarios</tt>.
	 * 
	 * @param scenarios  The scenarios to set.
	 * 
	 * @uml.property  name="scenarios"
	 */
	public void setScenarios(Scenarios scenarios) {
		this.scenarios = scenarios;
	}

	/**
	 * Sets the side quests.
	 * 
	 * @param sideQuests the new side quests
	 */
	public void setSideQuests(Scenarios sideQuests) {
		this.sideQuests = sideQuests;
	}

	
}
