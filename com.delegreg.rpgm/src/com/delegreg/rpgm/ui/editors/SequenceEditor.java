package com.delegreg.rpgm.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.delegreg.rpgm.model.Sequence;

public class SequenceEditor extends FormEditor {
	public static final String ID = "com.delegreg.rpgm.editors.sequence"; //$NON-NLS-1$
	private Sequence sequ;
	private boolean dirty=false;

	private SequenceEditorMainPage mainPage; 
	private SequenceEditorEncountersPage encountersPage;
	private SequenceEditorLocationsPage locationsPage; 	
	private SequenceEditorAudioRessourcesPage audioPage; 	

	
	public SequenceEditor() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
	    setSite(site);
	    setInput(input);
	    sequ=(Sequence)input.getAdapter(Sequence.class);
	    setPartName(getName());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		mainPage.doSave(monitor);
		encountersPage.doSave(monitor);
	}

	public boolean isDirty() {
		return dirty;
	}



	private String getName() {
	    return sequ.getName();
	}

	@Override
	protected void addPages() {
		try {
			mainPage=new SequenceEditorMainPage(this);
			encountersPage=new SequenceEditorEncountersPage(this);
			locationsPage=new SequenceEditorLocationsPage(this);
			audioPage=new SequenceEditorAudioRessourcesPage(this);
			addPage(mainPage);
			addPage(encountersPage);
			addPage(locationsPage);
			addPage(audioPage);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
		firePropertyChange(SequenceEditor.PROP_DIRTY);				
	}


	@Override
	public void doSaveAs() {}


	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}	
}
