package com.delegreg.rpgm.ui.widgets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;

public class DBCombo implements ModifyListener {

	private Combo cmb;
	private Object dataBoundObject;
	private Method getProperty;
	private Method setProperty;
	private int[] codeList;
	private String[] libList;
	
	public DBCombo(Combo text) {
		this.cmb=text;
		text.addModifyListener(this);
	}

	public void initialize(int[] cl,String[] ll){
		codeList=cl;
		initialize(ll);
	}
	
	public void initialize(String[] ll){
		libList=ll;
		cmb.setItems(ll);
	}
	
	public DBCombo(Combo text,Object DBO,String PN) {
		this(text);
		setDataBoundObject(DBO,PN);
	}

	public void setDataBoundObject(Object dataBoundObject,String propertyName) {
		this.dataBoundObject = dataBoundObject;
		try {
			if (codeList==null){
				setProperty=dataBoundObject.getClass().getMethod("set" + propertyName, String.class);
				getProperty=dataBoundObject.getClass().getMethod("get" + propertyName);
			}else {
				setProperty=dataBoundObject.getClass().getMethod("set" + propertyName, Integer.TYPE);
				getProperty=dataBoundObject.getClass().getMethod("get" + propertyName);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (codeList==null){
				cmb.setText((String) getProperty.invoke(dataBoundObject));
			}else {
				//match code with text
				int code=(Integer) getProperty.invoke(dataBoundObject);
				for (int i = 0; i < codeList.length; i++) {
					if (codeList[i]==code){
						cmb.select(i);
					}
				}
			}
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
			if (codeList==null){
				setProperty.invoke(dataBoundObject, cmb.getText());
			}else {
				//match code with text
				int code=cmb.getSelectionIndex();
				setProperty.invoke(dataBoundObject, codeList[code]);
			}
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
		cmb.setLayoutData(ld);
	}


	
	
}
