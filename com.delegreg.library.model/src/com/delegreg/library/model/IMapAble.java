package com.delegreg.library.model;

import org.eclipse.swt.graphics.Point;

import com.delegreg.core.INameable;

public interface IMapAble extends INameable {
	//add the capacity to be placed on a map
	public void setMapLocation(Point mapLocation); 

	public Point getMapLocation();

}
