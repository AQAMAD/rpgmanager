package com.delegreg.rpgm.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Actor;

public class ActorEditorStatsPage extends FormPage {
	public static final String ID = "com.delegreg.rpgm.editors.actor.stats"; //$NON-NLS-1$
	private ScrolledForm form; 
	private Actor personne;
	private IEditorInput input; 
	private StatBlocksBlock block;
	
	private ActorEditor editor;
	
	public ActorEditorStatsPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
		this.editor=(ActorEditor)editor;
	}

	public ActorEditorStatsPage(FormEditor editor) {
		this(editor, ID, Messages.ActorEditorStatsPage_Title);
		block=new StatBlocksBlock(this);
	}	
	
	@Override
	public final void doSave(IProgressMonitor monitor) {
		editor.setDirty(false);
	}

	@Override
	public void doSaveAs() {
		System.out.println("ActorEditor.doSaveAs()"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
		this.input=input;
	    personne=(Actor)input.getAdapter(Actor.class);
	}

	@Override
	public boolean isDirty() {
		return editor.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		//System.out.println("CampaignEditor.isSaveAsAllowed()"); //$NON-NLS-1$
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		//FormToolkit toolkit = managedForm.getToolkit();
		form.setText(Messages.ActorEditor_Title + getName()); 
//		form.setBackgroundImage(FormArticlePlugin.getDefault().getImage(
//				FormArticlePlugin.IMG_FORM_BG));
		block.createContent(managedForm);
	}
	
	
	@Override
	public void setFocus() {
		form.setFocus(); 

	}

	private String getName() {
	    return personne.getName();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
}
