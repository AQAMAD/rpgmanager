package com.delegreg.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class BaseRootContainer<V extends IContaineable> extends BaseContainer<V> {

	private ArrayList<V> internal=new ArrayList<V>();
	
	public boolean addlink(V arg0) {
		boolean result=internal.add(arg0);
		if (result){
			return true;
		}
		return false;
	}

	@Override
	public void add(int arg0, V arg1) {
		arg1.setContainer(this);
		internal.add(arg0,arg1);
	}

	@Override
	public boolean addAll(Collection<? extends V> arg0) {
		for (Iterator<? extends V> iterator = arg0.iterator(); iterator.hasNext();) {
			V v = (V) iterator.next();
			v.setContainer(this);
		}
		boolean result=internal.addAll(arg0);
		if (result){
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
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		internal.clear();
	}	
	
	@Override
	public boolean remove(Object arg0) {
		boolean result=internal.remove(arg0);
		if (result){
			return true;
		}
		return false;
	}

	@Override
	public V remove(int arg0) {
		V temp=internal.remove(arg0);
		return temp;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		boolean result=internal.removeAll(arg0);
		if (result){
			return true;
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		boolean result=internal.retainAll(arg0);
		if (result){
			return true;
		}
		return false;
	}

	@Override
	public V set(int arg0, V arg1) {
		V temp=internal.set(arg0, arg1);
		return temp;
	}


}
