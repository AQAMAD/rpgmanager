package com.delegreg.core;

import java.util.List;

public interface IContainer<V extends IContaineable> extends List<V>{

	public void fireContentChanged();
}
