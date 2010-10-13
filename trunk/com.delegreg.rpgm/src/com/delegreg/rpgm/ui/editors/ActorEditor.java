package com.delegreg.rpgm.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.delegreg.rpgm.model.Actor;

public class ActorEditor extends FormEditor {
	public static final String ID = "com.delegreg.rpgm.editors.actor"; //$NON-NLS-1$
	private Actor personne;
	private boolean dirty=false;

	private ActorEditorMainPage mainPage; 
	private ActorEditorStatsPage statsPage; 
	
	
	public ActorEditor() {
	}


	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
	    setSite(site);
	    setInput(input);
	    personne=(Actor)input.getAdapter(Actor.class);
	    setPartName(getName());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		mainPage.doSave(monitor);
		statsPage.doSave(monitor);
	}

	public boolean isDirty() {
		return dirty;
	}



	private String getName() {
	    return personne.getName();
	}

	@Override
	protected void addPages() {
		try {
			mainPage=new ActorEditorMainPage(this);
			statsPage=new ActorEditorStatsPage(this);
			addPage(mainPage);
			addPage(statsPage);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
		firePropertyChange(ActorEditor.PROP_DIRTY);				
	}


	@Override
	public void doSaveAs() {}


	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	
}
