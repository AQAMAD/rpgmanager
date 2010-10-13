package com.delegreg.rpgm.model;

import org.eclipse.swt.graphics.Point;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.library.model.IDocOrRessource;
import com.delegreg.library.model.IMapAble;
import com.delegreg.library.model.ImageDocument;

 
public class Location extends BaseContaineableContainer<Locations> implements IMapAble {

	private Point mapLocation;
	
	private String description=""; //$NON-NLS-1$
	
	private IDocOrRessource docRessource;
	
	private ImageDocument mapImage;

	public Location() {
		subLocations=new Locations(this);
	}

	public Location(String name) {
		this();
		this.setName(name);
	}

	/**
	 * @uml.property  name="subLocations"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="location2:com.delegreg.rpgm.model.Location"
	 */
	private Locations subLocations;

	/**
	 * Getter of the property <tt>subLocations</tt>
	 * @return  Returns the subLocations.
	 * @uml.property  name="subLocations"
	 */
	public Locations getSubLocations() {
		return subLocations;
	}

	/**
	 * Setter of the property <tt>subLocations</tt>
	 * @param subLocations  The subLocations to set.
	 * @uml.property  name="subLocations"
	 */
	public void setSubLocations(Locations subLocations) {
		this.subLocations = subLocations;
	}

	public void setDocRessource(IDocOrRessource docRessource) {
		this.docRessource = docRessource;
		fireChanged();
	}

	public IDocOrRessource getDocRessource() {
		return docRessource;
	}

	public void setDescription(String description) {
		this.description = description;
		fireChanged();
	}

	public String getDescription() {
		return description;
	}

	public void setMapLocation(Point mapLocation) {
		this.mapLocation = mapLocation;
		fireChanged();
	}

	public Point getMapLocation() {
		return mapLocation;
	}

	public void setMapImage(ImageDocument mapImage) {
		this.mapImage = mapImage;
		fireChanged();
	}

	public ImageDocument getMapImage() {
		return mapImage;
	}

	
	
}
