package com.delegreg.core;


public class BaseContaineableNameable extends BaseContaineable implements INameable,IContaineable {


	private String name=""; //$NON-NLS-1$
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name=name;
	}
	

}
