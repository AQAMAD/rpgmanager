package com.delegreg.library.model;

import java.util.ArrayList;
import java.util.Iterator;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;
import com.delegreg.library.Messages;


public class Libraries extends BaseContaineableContainer<Library> implements ILibraryElement {

	private static ArrayList<Libraries> existingLibraries=new ArrayList<Libraries>();
	
	public static void registerLibraries(Libraries newitem){
		if (!existingLibraries.contains(newitem)){
			existingLibraries.add(newitem);
		}
	}
	
	public static Libraries[] getRegisteredLibraries(){
		Libraries[] libs=new Libraries[existingLibraries.size()];
		for (int i = 0; i < existingLibraries.size(); i++) {
			Libraries lib=existingLibraries.get(i);
			libs[i]=lib;
		}
		return libs;
	}
	
	private String solidFolderPath;
	
	public Libraries(IContainer<? extends IContaineable> parent) {
		setName(Messages.LibrariesName);
		setContainer(parent);
	}

	public void setSolidFolderPath(String solidFolderPath) {
		this.solidFolderPath = solidFolderPath;
		fireChanged();
	}

	public String getSolidFolderPath() {
		return solidFolderPath;
	}


}
