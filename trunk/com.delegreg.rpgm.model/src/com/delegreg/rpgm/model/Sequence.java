package com.delegreg.rpgm.model;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.library.model.IDocOrRessource;
import com.delegreg.library.model.Library;

public class Sequence extends BaseContaineableContainer<IContaineable> {

	
	/** The description. */
	private String description;

	/** The doc ressource. */
	private IDocOrRessource docRessource;

	public Sequence() {
		encounter=new Group();
		encounter.setContainer(this);
		locations=new Locations(this);
		audioRessources=new Library();
		audioRessources.setContainer(this);
	}

	public Sequence(String name) {
		this();
		this.setName(name);
	}

	/**
	 * @uml.property  name="location"
	 * @uml.associationEnd  inverse="sequence:com.delegreg.rpgm.model.Location"
	 */
	private Locations locations;

	/**
	 * Getter of the property <tt>location</tt>
	 * @return  Returns the location.
	 * @uml.property  name="location"
	 */
	public Locations getLocations() {
		return locations;
	}

	/**
	 * Setter of the property <tt>location</tt>
	 * @param location  The location to set.
	 * @uml.property  name="location"
	 */
	public void setLocation(Locations locations) {
		this.locations = locations;
	}

	/**
	 * @uml.property  name="encounter"
	 * @uml.associationEnd  inverse="sequence:com.delegreg.rpgm.model.IEncounter"
	 */
	private Group encounter;

	/**
	 * Getter of the property <tt>encounter</tt>
	 * @return  Returns the encounter.
	 * @uml.property  name="encounter"
	 */
	public Group getEncounter() {
		return encounter;
	}

	/**
	 * Setter of the property <tt>encounter</tt>
	 * @param encounter  The encounter to set.
	 * @uml.property  name="encounter"
	 */
	public void setEncounter(Group encounter) {
		this.encounter = encounter;
	}

	/**
	 * @uml.property  name="audioRessources"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="sequence:com.delegreg.rpgm.model.AudioRessource"
	 */
	private Library audioRessources;

	/**
	 * Getter of the property <tt>audioRessources</tt>
	 * @return  Returns the audioRessources.
	 * @uml.property  name="audioRessources"
	 */
	public Library getAudioRessources() {
		return audioRessources;
	}

	/**
	 * Setter of the property <tt>audioRessources</tt>
	 * @param audioRessources  The audioRessources to set.
	 * @uml.property  name="audioRessources"
	 */
	public void setAudioRessources(Library audioRessources) {
		this.audioRessources = audioRessources;
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
		
	
}
