package com.delegreg.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class BaseContaineableContainer<V extends IContaineable> extends BaseContainer<V> implements IContaineable,INameable,List<V> {

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
	
	private ArrayList<V> internal=new ArrayList<V>();
	
	public boolean addlink(V arg0) {
		boolean result=internal.add(arg0);
		if (result){
			fireChanged();
			return true;
		}
		return false;
	}

	@Override
	public void add(int arg0, V arg1) {
		arg1.setContainer(this);
		internal.add(arg0,arg1);
		fireChanged();
	}

	@Override
	public boolean addAll(Collection<? extends V> arg0) {
		for (Iterator<? extends V> iterator = arg0.iterator(); iterator.hasNext();) {
			V v = (V) iterator.next();
			v.setContainer(this);
		}
		boolean result=internal.addAll(arg0);
		if (result){
			fireChanged();
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends V> arg1) {
		for (Iterator<? extends V> iterator = arg1.iterator(); iterator.hasNext();) {
			V v = (V) iterator.next();
			v.setContainer(this);
		}
		boolean result=internal.addAll(arg0,arg1);
		if (result){
			fireChanged();
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		internal.clear();
		fireChanged();
	}	
	
	@Override
	public boolean remove(Object arg0) {
		boolean result=internal.remove(arg0);
		if (result){
			fireChanged();
			return true;
		}
		return false;
	}

	@Override
	public V remove(int arg0) {
		V temp=internal.remove(arg0);
		fireChanged();
		return temp;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		boolean result=internal.removeAll(arg0);
		if (result){
			fireChanged();
			return true;
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		boolean result=internal.retainAll(arg0);
		if (result){
			fireChanged();
			return true;
		}
		return false;
	}

	@Override
	public V set(int arg0, V arg1) {
		V temp=internal.set(arg0, arg1);
		fireChanged();
		return temp;
	}

	//add the capacity to be contained by others
	private IContainer<? extends IContaineable> container=null;
	
	@Override
	public IContainer<? extends IContaineable> getContainer() {
		return container;
	}

	@Override
	public BaseRootContainer<? extends IContaineable> getRoot() {
		if (container==null){
			//System.out.println("un-contained containeable");
			return null;
		}
		if (container instanceof BaseRootContainer<?>){
			//System.out.println("un-contained containeable");
			return (BaseRootContainer<? extends IContaineable>) container;
		}else if(container instanceof IContaineable){
			//System.out.println("un-contained containeable");
			return (BaseRootContainer<? extends IContaineable>) ((IContaineable) container).getRoot();
		}else {
			return null;
		}
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
