package com.delegreg.rpgm.core;

import org.eclipse.jface.preference.PreferenceStore;

public class RpgmPreferenceStore {
	public static final String LIBRARY_DIRECTORY = "library_directory"; //$NON-NLS-1$
	public static final String CAMPAIGNS_DIRECTORY = "campaigns_directory"; //$NON-NLS-1$
	public static final String DEFAULT_GM_NAME = "default_gm_name"; //$NON-NLS-1$
	public static final String ALLOW_INTERNAL_BROWSING = "allow_internal_browsing"; //$NON-NLS-1$
	public static final String LOAD_LAST_SAVED_CAMPAIGNS = "load_last_saved_campaigns"; //$NON-NLS-1$
	public static final String LAST_SAVED_CAMPAIGNS = "last_saved_campaigns"; //$NON-NLS-1$
	public static final String HIRES_PDF = "use_hires_pdf"; //$NON-NLS-1$
	public static final String SAVE_CAMPAIGNS_DIRECTORY = "save_campaigns_directory"; //$NON-NLS-1$
	public static final String WORKSPACE_ROOT_DIRECTORY = "workspace_root_dir"; //$NON-NLS-1$
	public static final String REMEMBER_WORKSPACE = "remember_workspace"; //$NON-NLS-1$
	public static final String WORKSPACES_MRU = "workspaces_mru"; //$NON-NLS-1$

	public static final String NODE_GENERAL = "General"; //$NON-NLS-1$
	
	private final static PreferenceStore ps = new PreferenceStore("rpgm.properties"); //$NON-NLS-1$

	static {
		ps.setDefault(LIBRARY_DIRECTORY, System.getProperty("user.home")); //$NON-NLS-1$
		ps.setDefault(CAMPAIGNS_DIRECTORY, System.getProperty("user.home")); //$NON-NLS-1$
		ps.setDefault(DEFAULT_GM_NAME, System.getProperty("user.name")); //$NON-NLS-1$
		ps.setDefault(ALLOW_INTERNAL_BROWSING, false);
		ps.setDefault(LOAD_LAST_SAVED_CAMPAIGNS, true);
		ps.setDefault(SAVE_CAMPAIGNS_DIRECTORY,true); //$NON-NLS-1$
		ps.setDefault(HIRES_PDF, 100);
		ps.setDefault(LAST_SAVED_CAMPAIGNS, ""); //$NON-NLS-1$
		ps.setDefault(REMEMBER_WORKSPACE, false); //$NON-NLS-1$
		ps.setDefault(WORKSPACES_MRU, ""); //$NON-NLS-1$
		ps.setDefault(WORKSPACE_ROOT_DIRECTORY, ""); //$NON-NLS-1$
	}
	
	public static PreferenceStore getInstance() {
		return ps;
	}
	
	
	
}
