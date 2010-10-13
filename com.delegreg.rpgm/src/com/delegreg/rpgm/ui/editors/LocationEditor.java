package com.delegreg.rpgm.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.delegreg.rpgm.model.Location;

public class LocationEditor extends FormEditor {
	public static final String ID = "com.delegreg.rpgm.editors.location"; //$NON-NLS-1$
	private Location lieu;
	private boolean dirty;
	
	private LocationEditorMainPage mainPage; 
	private LocationEditorMapPage mapPage; 
	
	public LocationEditor() {
	}


	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
	    setSite(site);
	    setInput(input);
	    lieu=(Location)input.getAdapter(Location.class);
	    setPartName(getName());
	}


	@Override
	public boolean isSaveAsAllowed() {
		//System.out.println("CampaignEditor.isSaveAsAllowed()"); //$NON-NLS-1$
		return false;
	}


	private String getName() {
	    return lieu.getName();
	}	
	
	@Override
	protected void addPages() {
		try {
			mainPage=new LocationEditorMainPage(this);
			mapPage=new LocationEditorMapPage(this);
			addPage(mainPage);
			addPage(mapPage);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		mainPage.doSave(monitor);
		mapPage.doSave(monitor);
	}


	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
		firePropertyChange(LocationEditor.PROP_DIRTY);				
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}
	
}
