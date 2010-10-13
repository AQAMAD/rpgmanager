package com.delegreg.rpgm.games.basic;

import java.lang.reflect.Field;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.delegreg.rpgm.games.basic.messages"; //$NON-NLS-1$
	public static String Sex_Female;
	public static String Sex_Male;
	public static String Sex_Undefined;
	public static String D20StatBlock;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String getByName(String name){
		Field[] fields=Messages.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(name)){
				try {
					return (String)fields[i].get(Messages.class.newInstance());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} 
			}
		}
		System.out.println("no field found"); //$NON-NLS-1$
		return null;		
	}
}
