package com.delegreg.library;

import java.lang.reflect.Field;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.delegreg.library.messages"; //$NON-NLS-1$
	public static String LibrariesName;
	public static String NewLibrary;
	public static String NewPdfDocument;
	public static String NewWebDocument;
	public static String NewAudioDocument;
	public static String NewAudioRessource;
	public static String WizardPage_NameMandatory;
	public static String WizardPage_ChosenItemType;
	public static String WizardPage_WillBeAddedTo;
	public static String WizardPage_Name;
	public static String DeleteItemAction_Name;
	public static String DeleteItemAction_ToolTip;
	public static String EditItemAction_Name;
	public static String EditItemAction_Tooltip;
	public static String ExportItemAction_Name;
	public static String ExportItemAction_Tooltip;
	public static String ExportItemAction_OverWrite;
	public static String ExportItemAction_ErrorMessage;
	public static String ExportItemAction_ErrorTitle;
	public static String ExportItemAction_DialogMessage;
	public static String ExportItemAction_DialogTitle;
	public static String ExportItemAction_Cancel;
	public static String ImportItemAction_Name;
	public static String ImportItemAction_Tooltip;
	public static String ImportItemAction_Add;
	public static String ImportItemAction_Import;
	public static String ImportItemAction_ErrorTitle;
	public static String ImportItemAction_ErrorMessage;
	public static String ImportItemAction_DialogTitle;
	public static String ImportItemAction_AlternateDialogMessage;
	public static String ImportItemAction_Cancel;
	public static String ImportItemAction_DialogMessage;
	public static String RenameItemAction_Name;
	public static String RenameItemAction_Tooltip;
	public static String RenameItemAction_ErrorMessage;
	public static String RenameItemAction_DialogMessage;
	public static String RenameItemAction_DialogTitle;
	public static String SolidifyAction_Name;
	public static String SolidifyAction_Tooltip;
	public static String SolidifyAction_ErrorMessage;
	public static String SolidifyAction_ErrorTitle;
	public static String NewItemAction_Name;
	public static String NewItemAction_ToolTip;
	
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
