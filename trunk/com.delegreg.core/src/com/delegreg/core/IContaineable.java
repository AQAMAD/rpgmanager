package com.delegreg.core;

public interface IContaineable extends INameable{
	/**
	 * @return  Returns the name.
	 * @uml.property  name="name"
	 */
	public IContainer<? extends IContaineable> getContainer();

	/**
	 * Setter of the property <tt>name</tt>
	 * @param name  The name to set.
	 * @uml.property  name="name"
	 */
	public void setContainer(IContainer<? extends IContaineable> container);


	public IContainer<? extends IContaineable> getRoot();
	
	public void fireChanged();
	
}
