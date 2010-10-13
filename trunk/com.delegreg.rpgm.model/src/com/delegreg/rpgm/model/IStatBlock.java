package com.delegreg.rpgm.model;

import org.eclipse.swt.graphics.Image;

import com.delegreg.core.IContaineable;
import com.delegreg.core.INameable;

public interface IStatBlock extends IContaineable, INameable {

	public String ClassName();
	public Image ClassIcon();
	
}
