package com.delegreg.rpgm.ui.widgets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Spinner;

public class DBSpinner implements ModifyListener {

	private Spinner spn;
	private Object dataBoundObject;
	private Method getProperty;
	private Method setProperty;
	
	public DBSpinner(Spinner spn) {
		this.spn=spn;
		spn.addModifyListener(this);
	}

	public DBSpinner(Spinner spn,Object DBO,String PN) {
		this(spn);
		setDataBoundObject(DBO,PN);
	}

	public void setDataBoundObject(Object dataBoundObject,String propertyName) {
		this.dataBoundObject = dataBoundObject;
		try {
			getProperty=dataBoundObject.getClass().getMethod("get" + propertyName);
			setProperty=dataBoundObject.getClass().getMethod("set" + propertyName, Integer.TYPE);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			spn.setSelection((Integer) getProperty.invoke(dataBoundObject));
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
		// TODO Auto-generated method stub
		try {
			setProperty.invoke(dataBoundObject, spn.getSelection());
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

	public void setLayoutData(Object ld) {
		spn.setLayoutData(ld);
	}

	public void addModifyListener(ModifyListener lstn) {
		spn.addModifyListener(lstn);
	}


	
	
}
