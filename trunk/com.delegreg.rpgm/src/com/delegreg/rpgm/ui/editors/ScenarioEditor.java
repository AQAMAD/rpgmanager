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
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;

import com.delegreg.library.model.IDocOrRessource;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Scenario;
import com.delegreg.rpgm.ui.widgets.RpgmWidgetFactory;

public class ScenarioEditor extends EditorPart {
	public static final String ID = "com.delegreg.rpgm.editors.scenario"; //$NON-NLS-1$
	private FormToolkit toolkit;
	private ScrolledForm form; 
	private Scenario scenar;
	private boolean dirty=false;
	private Text txtName;
	private IDocOrRessource selectedRessource;
	

	/** The description. */
	private Browser description;
	
	/** The hyperlink. */
	private Hyperlink hprDocRessource;
	
	public ScenarioEditor() {
	}

	@Override
	public final void doSave(IProgressMonitor monitor) {
		scenar.setName(txtName.getText());
		//scenar.setGameMaster(gmName.getText());
		scenar.setDescription(description.getText());
		scenar.setDocRessource(selectedRessource);
		dirty=false;
		firePropertyChange(ScenarioEditor.PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
		System.out.println("ScenarioEditor.doSaveAs()"); //$NON-NLS-1$
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
	    setSite(site);
	    setInput(input);
	    scenar=(Scenario)input.getAdapter(Scenario.class);
	    setPartName(getName());
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		//System.out.println("CampaignEditor.isSaveAsAllowed()"); //$NON-NLS-1$
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay()); 
		form = toolkit.createScrolledForm(parent); 
		form.setText(Messages.ScenarioEditor_Title + getName()); 
		toolkit.decorateFormHeading(form.getForm());
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		form.getBody().setLayout(layout); 
		{
			Label lblScenarioName = toolkit.createLabel(form.getBody(), Messages.ScenarioEditor_ScenarioNameLabel, SWT.NONE);
			lblScenarioName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		}
		{
			txtName = toolkit.createText(form.getBody(), scenar.getName(), SWT.NONE);
			txtName.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					dirty=(!txtName.getText().equals(scenar.getName()));
					firePropertyChange(ScenarioEditor.PROP_DIRTY);				
				}
			});
			txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		
		hprDocRessource = RpgmWidgetFactory.createDocRessourcePopup(toolkit, form.getForm(), getSite(),scenar.getDocRessource(), new Runnable(){
			@Override
			public void run() {
				if (scenar.getDocRessource()==null){
					dirty=(hprDocRessource.getData()!=null);
				}else{
					dirty=(!scenar.getDocRessource().equals(hprDocRessource.getData()));
				}
				firePropertyChange(ScenarioEditor.PROP_DIRTY);
			}
			
		}) ;

		description = RpgmWidgetFactory.createDescriptionPopup(toolkit, form.getBody(),scenar.getDescription(), new Runnable(){
			@Override
			public void run() {
				dirty=(!description.getText().equals(scenar.getDescription()));
				firePropertyChange(ScenarioEditor.PROP_DIRTY);		
			}
			
		}) ;

		if (scenar.getDescription()==null){
			scenar.setDescription(description.getText());
		}		

	}

	
	@Override
	public void setFocus() {
		form.setFocus(); 

	}

	private String getName() {
	    return scenar.getName();
	}

	@Override
	public void dispose() {
		toolkit.dispose(); 
		super.dispose();
	}
	
}
