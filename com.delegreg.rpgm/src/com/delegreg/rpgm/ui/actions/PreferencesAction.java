/**
 * 
 */
package com.delegreg.rpgm.ui.actions;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.library.util.LibraryPreferenceStore;
import com.delegreg.library.ui.LibraryPreferencePage;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.core.RpgmPreferenceStore;
import com.delegreg.rpgm.ui.GeneralPreferencePage;
import com.delegreg.rpgm.ui.IImageKeys;

/**
 * @author Administrateur
 *
 */
public class PreferencesAction extends Action implements IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.preferences"; //$NON-NLS-1$
	private PreferenceStore ps;
	
	public PreferencesAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setText(Messages.PreferencesAction_Name);
		setToolTipText(Messages.PreferencesAction_Tooltip);
		setImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin(
						Application.PLUGIN_ID, IImageKeys.PREFERENCES)); //$NON-NLS-1$
	}


	public void run() {
		try {
			//get the preference store and open the preference dialog
			PreferenceNode general = new PreferenceNode(RpgmPreferenceStore.NODE_GENERAL, new GeneralPreferencePage());
			PreferenceNode library = new PreferenceNode(LibraryPreferenceStore.NODE_LIBRARY, new LibraryPreferencePage());
			PreferenceManager mgr = new PreferenceManager();
			mgr.addToRoot(general);
			mgr.addToRoot(library);
			PreferenceDialog myPreferenceDialog = new PreferenceDialog(null, mgr);
			ps=RpgmPreferenceStore.getInstance();
			try {
				ps.load();
			} catch (IOException e) { e.printStackTrace(); }
			myPreferenceDialog.setPreferenceStore(ps);
			myPreferenceDialog.open();
			try {
				ps.save();
			} catch (IOException e) { e.printStackTrace(); }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


	
}
