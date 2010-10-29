package com.delegreg.library.util;

import org.eclipse.jface.preference.PreferenceStore;

public class LibraryPreferenceStore {
	public static final String LIBRARY_DIRECTORY = "library_directory"; //$NON-NLS-1$
	public static final String ALLOW_INTERNAL_BROWSING = "allow_internal_browsing"; //$NON-NLS-1$
	public static final String HIRES_PDF = "use_hires_pdf"; //$NON-NLS-1$
	public static final String NODE_LIBRARY = "Library"; //$NON-NLS-1$
	
	private final static PreferenceStore ps = new PreferenceStore("library.properties"); //$NON-NLS-1$

	static {
		ps.setDefault(LIBRARY_DIRECTORY, System.getProperty("user.home")); //$NON-NLS-1$
		ps.setDefault(ALLOW_INTERNAL_BROWSING, false);
		ps.setDefault(HIRES_PDF, 100);
	}
	
	public static PreferenceStore getInstance() {
		return ps;
	}
	
	
	
}
