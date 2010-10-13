package com.delegreg.rpgm.ui.widgets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PojoDataBinder {

	static public Object getSafeObjectProperty(Object dataBoundObject,String propertyName,Class propertyClass) {
		Method property;
		Object returnValue=null;
		if (dataBoundObject == null){
			return null;
		}		
		try {
			property=dataBoundObject.getClass().getMethod("get" + propertyName);
			returnValue=property.invoke(dataBoundObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	static public void setSafeObjectProperty(Object dataBoundObject,String propertyName,Class propertyClass,Object value) {
		Method property;
		if (dataBoundObject == null){
			return;
		}		
		try {
			property=dataBoundObject.getClass().getMethod("set" + propertyName,propertyClass);
			property.invoke(dataBoundObject,value);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
}
