package com.delegreg.core;


public abstract class BaseContaineable implements IContaineable {
	//add the capacity to be contained by others
	private IContainer<? extends IContaineable> container=null;
	
	@Override
	public IContainer<? extends IContaineable> getContainer() {
		return container;
	}

	@Override
	public IContainer<? extends IContaineable> getRoot() {
		if (container==null){
			//System.out.println("un-contained containeable");
			return null;
		}
		return ((IContaineable)container).getRoot();
	}


	@Override
	public void setContainer(IContainer<? extends IContaineable> container) {
		this.container=container;
	}

	public void fireChanged(){
		IContainer<? extends IContaineable> root;
		root=getRoot();
		if (root!=null){
			//TODO : fix this 
			root.fireContentChanged();
		}
	}	
	
}
