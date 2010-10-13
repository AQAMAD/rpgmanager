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
import com.delegreg.rpgm.model.Campaign;
import com.delegreg.rpgm.ui.widgets.RpgmWidgetFactory;

/**
 * The Class CampaignEditor.
 */
public class CampaignEditor extends EditorPart {
	
	/** The Constant ID. */
	public static final String ID = "com.delegreg.rpgm.editors.campaign"; //$NON-NLS-1$
	
	private IDocOrRessource selectedRessource;
	
	/** The toolkit. */
	private FormToolkit toolkit;
	
	/** The form. */
	private ScrolledForm form; 
	
	/** The gm name. */
	private Text gmName;
	
	/** The campaign name. */
	private Text campaignName;
	
	/** The campagne. */
	private Campaign campagne;
	
	/** The lbl campaign name. */
	private Label lblCampaignName;
	
	/** The dirty. */
	private boolean dirty=false;
	
	/** The description. */
	private Browser description;
	
	/** The hyperlink. */
	private Hyperlink hprDocRessource;

	
	/**
	 * Instantiates a new campaign editor.
	 */
	public CampaignEditor() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public final void doSave(IProgressMonitor monitor) {
		campagne.setName(campaignName.getText());
		campagne.setGameMaster(gmName.getText());
		campagne.setDescription(description.getText());
		campagne.setDocRessource(selectedRessource);
		dirty=false;
		firePropertyChange(CampaignEditor.PROP_DIRTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		System.out.println("CampaignEditor.doSaveAs()"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
	    setSite(site);
	    setInput(input);
	    campagne=(Campaign)input.getAdapter(Campaign.class);
	    setPartName(getName());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return dirty;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		//System.out.println("CampaignEditor.isSaveAsAllowed()"); //$NON-NLS-1$
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay()); 
		form = toolkit.createScrolledForm(parent); 
		form.setText(Messages.CampaignEditor_Title + getName()); 
		toolkit.decorateFormHeading(form.getForm());
		
		GridLayout layout = new GridLayout(); 
		layout.numColumns = 2;
		form.getBody().setLayout(layout); 
				
		lblCampaignName = toolkit.createLabel(form.getBody(),Messages.CampaignEditor_CampaignNameLabel);
		lblCampaignName.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));

		campaignName = toolkit.createText(form.getBody(), campagne.getName()); 
		campaignName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dirty=(!campaignName.getText().equals(campagne.getName()));
				firePropertyChange(CampaignEditor.PROP_DIRTY);
			}
		});
		campaignName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = toolkit.createLabel(form.getBody(), Messages.CampaignEditor_GameMasterLabel); 
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));

		gmName = toolkit.createText(form.getBody(), campagne.getGameMaster()); 
		gmName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dirty=(!gmName.getText().equals(campagne.getGameMaster()));
				firePropertyChange(CampaignEditor.PROP_DIRTY);
			}
		});
		gmName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		hprDocRessource = RpgmWidgetFactory.createDocRessourcePopup(toolkit, form.getForm(), getSite(),campagne.getDocRessource(), new Runnable(){
			@Override
			public void run() {
				if (campagne.getDocRessource()==null){
					dirty=(hprDocRessource.getData()!=null);
				}else{
					dirty=(!campagne.getDocRessource().equals(hprDocRessource.getData()));
				}
				firePropertyChange(CampaignEditor.PROP_DIRTY);
			}
			
		}) ;

		description = RpgmWidgetFactory.createDescriptionPopup(toolkit, form.getBody(),campagne.getDescription(), new Runnable(){
			@Override
			public void run() {
				dirty=(!description.getText().equals(campagne.getDescription()));
				firePropertyChange(CampaignEditor.PROP_DIRTY);		
			}
			
		}) ;

		if (campagne.getDescription()==null){
			campagne.setDescription(description.getText());
		}		
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		form.setFocus(); 

	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	private String getName() {
	    return campagne.getName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		toolkit.dispose(); 
		super.dispose();
	}
	
}
