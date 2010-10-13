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
import com.delegreg.rpgm.model.Location;
import com.delegreg.rpgm.ui.widgets.RpgmWidgetFactory;

public class LocationEditorMainPage extends FormPage {
	public static final String ID = "com.delegreg.rpgm.editors.location.main"; //$NON-NLS-1$
	private IEditorInput input; 
	private Location lieu;
	
	private ScrolledForm form; 

	private Text txtName;

	/** The description. */
	private Browser description;
	
	/** The hyperlink. */
	private Hyperlink hprDocRessource;
	
	private LocationEditor editor;
	
	
	public LocationEditorMainPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
		this.editor=(LocationEditor)editor;
	}

	public LocationEditorMainPage(FormEditor editor) {
		this(editor, ID, Messages.LocationEditorMainPage_Title);
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
		Composite parent=managedForm.getForm().getParent();
		FormToolkit toolkit = managedForm.getToolkit();
		form = managedForm.getForm(); 
		form.setText(Messages.LocationEditor_Title + getName()); 
		toolkit.decorateFormHeading(form.getForm());
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout); 
		{
			Label lblScenarioName = toolkit.createLabel(form.getBody(), Messages.LocationEditor_LocationNameLabel, SWT.NONE);
			lblScenarioName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		}
		{
			txtName = toolkit.createText(form.getBody(), lieu.getName(), SWT.NONE);
			txtName.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					editor.setDirty(!txtName.getText().equals(lieu.getName()));
				}
			});
			txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		
		hprDocRessource = RpgmWidgetFactory.createDocRessourcePopup(toolkit, form.getForm(), getSite(),lieu.getDocRessource(), new Runnable(){
			@Override
			public void run() {
				if (lieu.getDocRessource()==null){
					editor.setDirty(hprDocRessource.getData()==null);
				}else{
					editor.setDirty(!lieu.getDocRessource().equals(hprDocRessource.getData()));
				}
			}
			
		}) ;

		description = RpgmWidgetFactory.createDescriptionPopup(toolkit, form.getBody(),lieu.getDescription(), new Runnable(){
			@Override
			public void run() {
				editor.setDirty(!description.getText().equals(lieu.getDescription()));
			}
			
		}) ;

		if (lieu.getDescription()==null){
			lieu.setDescription(description.getText());
		}
		
	
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		lieu.setName(txtName.getText());
		lieu.setDescription(description.getText());
		lieu.setDocRessource((IDocOrRessource) hprDocRessource.getData());
		editor.setDirty(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return editor.isDirty();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
		this.input=input;
		lieu=(Location)input.getAdapter(Location.class);
	}

	private String getName() {
	    return lieu.getName();
	}	
}
