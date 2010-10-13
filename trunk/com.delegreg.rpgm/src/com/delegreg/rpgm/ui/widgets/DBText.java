package com.delegreg.rpgm.ui.widgets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

public class DBText extends TextBox implements ModifyListener {

	private Object dataBoundObject;
	private Method getProperty;
	private Method setProperty;
	
	public DBText(Composite parent, int style) {
		super(parent, style);
		text.addModifyListener(this);

	}

	public DBText(Composite parent, int style,Object DBO,String PN) {
		this(parent,style);
		setDataBoundObject(DBO,PN);
	}

	public void setDataBoundObject(Object dataBoundObject,String propertyName) {
		this.dataBoundObject = dataBoundObject;
		try {
			getProperty=dataBoundObject.getClass().getMethod("get" + propertyName);
			setProperty=dataBoundObject.getClass().getMethod("set" + propertyName, String.class);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			text.setText((String) getProperty.invoke(dataBoundObject));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void modifyText(ModifyEvent e) {
		if (dataBoundObject == null){
			return;
		}
		try {
			setProperty.invoke(dataBoundObject, text.getText());
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}


	
}
