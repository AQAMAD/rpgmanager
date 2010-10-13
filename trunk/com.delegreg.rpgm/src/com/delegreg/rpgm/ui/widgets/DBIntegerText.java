package com.delegreg.rpgm.ui.widgets;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

public class DBIntegerText extends IntegerText implements ModifyListener {

	private Object dataBoundObject;
	private String propertyName;
	
	public DBIntegerText(Composite parent, int style) {
		super(parent, style);
		super.text.addModifyListener(this);
		
	}

	public DBIntegerText(Composite parent, int style,Object DBO,String PN) {
		this(parent,style);
		setDataBoundObject(DBO,PN);
	}

	public void setDataBoundObject(Object dataBoundObject,String propertyName) {
		this.dataBoundObject = dataBoundObject;
		this.propertyName = propertyName;
		super.setValue((Integer) PojoDataBinder.getSafeObjectProperty(dataBoundObject, propertyName, Integer.TYPE));
	}

	@Override
	public void modifyText(ModifyEvent e) {
		PojoDataBinder.setSafeObjectProperty(dataBoundObject, propertyName, Integer.TYPE, super.getValue());	
	}


	
}
