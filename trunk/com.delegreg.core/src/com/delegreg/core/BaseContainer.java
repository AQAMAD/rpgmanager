package com.delegreg.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.ListenerList;

public abstract class BaseContainer<V extends IContaineable> implements IContainer<V> {

	public BaseContainer() {
		super();
		readResolve();
		// TODO Auto-generated constructor stub
	}


	private transient ListenerList listeners;

	public void addContentListener(IContentListener listener){
		listeners.add(listener);
	}
	
	public void removeContentListener(IContentListener listener){
		listeners.remove(listener);
	}
	
	public void fireContentChanged(){
		for (int i = 0; i < listeners.getListeners().length; i++) {
			IContentListener listen = (IContentListener) listeners.getListeners()[i];
			listen.contentChanged();
		}
	}	

	
  private Object readResolve() {
		listeners=new ListenerList();
	    return this;
  }	
  
	private ArrayList<V> internal=new ArrayList<V>();

	@Override
	public boolean add(V arg0) {
		arg0.setContainer(this);
		return addlink(arg0);
	}

	public abstract boolean addlink(V arg0);

	@Override
	public boolean contains(Object arg0) {
		return internal.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return internal.containsAll(arg0);
	}

	@Override
	public V get(int arg0) {
		return internal.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return internal.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return internal.isEmpty();
	}

	@Override
	public Iterator<V> iterator() {
		return internal.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return internal.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<V> listIterator() {
		return internal.listIterator();
	}

	@Override
	public ListIterator<V> listIterator(int arg0) {
		return internal.listIterator(arg0);
	}



	@Override
	public int size() {
		return internal.size();
	}

	@Override
	public List<V> subList(int arg0, int arg1) {
		return internal.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return internal.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return internal.toArray(arg0);
	}
  
}
