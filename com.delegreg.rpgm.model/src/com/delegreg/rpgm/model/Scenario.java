/*
 * 
 */
package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.library.model.IDocOrRessource;
import com.delegreg.rpgm.Messages;

/**
 * The Class Scenario represents a scenario in a campaign.
 */
public class Scenario extends BaseContaineableContainer<IContaineable> {


	/** The actors. */
	private Group actors;
	
	/** The description. */
	private String description;

	/** The doc ressource. */
	private IDocOrRessource docRessource;

	/** The sequences. */
	private Sequences sequences;

	/**
	 * Instantiates a new scenario.
	 */
	public Scenario() {
		sequences = new Sequences(Messages.Scenario_Sequences,this);
		actors = new Group(Messages.Scenario_Actors,this);
	}

	/**
	 * Instantiates a new scenario.
	 * 
	 * @param name
	 *            the name
	 */
	public Scenario(String name) {
		this();
		this.setName(name);
	}

	/**
	 * Getter of the property <tt>encounters</tt>.
	 * 
	 * @return Returns the encounters.
	 */
	public Group getActors() {
		return actors;
	}


	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		if (description!=null){
			return description;
		}
		return ""; //$NON-NLS-1$
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
	 * Getter of the property <tt>sequences</tt>.
	 * 
	 * @return Returns the sequences.
	 */
	public Sequences getSequences() {
		return sequences;
	}

	/**
	 * Setter of the property <tt>encounters</tt>.
	 * 
	 * @param actors
	 *            the actors
	 */
	public void setActors(Group actors) {
		this.actors = actors;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
		fireChanged();
	}

	/**
	 * Sets the doc ressource.
	 * 
	 * @param docRessource
	 *            the new doc ressource
	 */
	public void setDocRessource(IDocOrRessource docRessource) {
		this.docRessource = docRessource;
	}

	/**
	 * Setter of the property <tt>sequences</tt>.
	 * 
	 * @param sequences
	 *            The sequences to set.
	 */
	public void setSequences(Sequences sequences) {
		this.sequences = sequences;
	}

	
	
	
}
