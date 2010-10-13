package com.delegreg.rpgm.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.delegreg.library.model.IDocOrRessource;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Actor;
import com.delegreg.rpgm.ui.widgets.RpgmWidgetFactory;

public class ActorEditorMainPage extends FormPage {
	public static final String ID = "com.delegreg.rpgm.editors.actor.main"; //$NON-NLS-1$
	private ScrolledForm form; 
	private Actor personne;
	private Text txtName;
	private IEditorInput input; 
	
	/** The description. */
	private Browser description;
	
	/** The hyperlink. */
	private Hyperlink hprDocRessource;
	
	private ActorEditor editor;
	
	
	public ActorEditorMainPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
		this.editor=(ActorEditor)editor;
	}

	public ActorEditorMainPage(FormEditor editor) {
		this(editor, ID, Messages.ActorEditorMainPage_Title);
	}	
	

	@Override
	public final void doSave(IProgressMonitor monitor) {
		personne.setName(txtName.getText());
		//scenar.setGameMaster(gmName.getText());
		personne.setDescription(description.getText());
		personne.setDocRessource((IDocOrRessource)hprDocRessource.getData());
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
		super.createFormContent(managedForm);
		Composite parent=managedForm.getForm().getParent();
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		form = managedForm.getForm(); 
		form.setText(Messages.ActorEditor_Title + getName()); 
		toolkit.decorateFormHeading(form.getForm());
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout); 
		{
			Label lblScenarioName = toolkit.createLabel(form.getBody(), Messages.ActorEditor_ActorNameLabel, SWT.NONE);
			lblScenarioName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		}
		{
			txtName = toolkit.createText(form.getBody(), personne.getName(), SWT.NONE);
			txtName.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					editor.setDirty(!txtName.getText().equals(personne.getName()));
				}
			});
			txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		
		hprDocRessource = RpgmWidgetFactory.createDocRessourcePopup(toolkit, form.getForm(), getSite(),personne.getDocRessource(), new Runnable(){
			@Override
			public void run() {
				if (personne.getDocRessource()==null){
					editor.setDirty(hprDocRessource.getData()!=null);
				}else{
					editor.setDirty(!personne.getDocRessource().equals(hprDocRessource.getData()));
				}
			}
			
		}) ;

		description = RpgmWidgetFactory.createDescriptionPopup(toolkit, form.getBody(),personne.getDescription(), new Runnable(){
			@Override
			public void run() {
				editor.setDirty(!description.getText().equals(personne.getDescription()));
			}
			
		}) ;

		if (personne.getDescription()==null){
			personne.setDescription(description.getText());
		}			
		
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
