package com.delegreg.rpgm.ui.wizards;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;
import com.delegreg.core.INameable;
import com.delegreg.core.ui.wizardpages.INewContaineableDetailPage;
import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.AudioRessource;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.ImageDocument;
import com.delegreg.library.model.Library;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.core.RpgmPreferenceStore;
import com.delegreg.rpgm.model.Actor;
import com.delegreg.rpgm.model.Campaign;
import com.delegreg.rpgm.model.Group;
import com.delegreg.rpgm.model.Location;
import com.delegreg.rpgm.model.Scenario;
import com.delegreg.rpgm.model.Sequence;

public class NewItemDetailsWizardPage extends WizardPage  implements INewContaineableDetailPage {
	private Label lblChosenItemType ;
	private Text txtName;
	private Spinner spnField1;
	private Label lblNumField1;
	private Text txtField3;
	private Label lblField3;
	private Text txtField2;
	private Label lblField2;
	private Label lblName;
	private Label lblWillBeAdded;
	private Label lblParentName;
	private Composite container;
	private Label lblFile1;
	private Text txtFile1;
	private Button btnFileChoose1;
	
	//the models
	private Object model;
	private Object parent;
	private boolean noUIUpdate=false;
	private Text txtDir1;
	private Button btnDirChoose1;
	private Label lblDir1;
	private FileDialog dialog;
	private boolean valid=false;
	
	/**
	 * Create the wizard.
	 */
	public NewItemDetailsWizardPage(String pageName) {
		super(pageName);
	}

	/**
	 * @wbp.parser.constructor
	 */
	public NewItemDetailsWizardPage(String pageName, String title, ImageDescriptor titleImage) {
		this(pageName);
		this.setTitle(title);
		this.setImageDescriptor(titleImage);
	}
	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		container = new Composite(parent,SWT.NULL);
		container.setLayout(new GridLayout(3, false));
		
		dialog=new FileDialog(container.getShell(),SWT.OPEN);
		//répertoire par défaut si renseigné
		String defaultrep=RpgmPreferenceStore.getInstance().getString(RpgmPreferenceStore.LIBRARY_DIRECTORY);
		if (!defaultrep.equals("")){ //$NON-NLS-1$
			dialog.setFilterPath(defaultrep); 
		}

		ModifyListener basicModifyListener=new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModel();
			}
		};

		setControl(container);
		{
			Label lbl1 = new Label(container, SWT.NONE);
			lbl1.setText(Messages.NewItemDetailsWizardPage_ChosenItemType);
		}
		{
			lblChosenItemType = new Label(container, SWT.NONE);
			lblChosenItemType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			lblChosenItemType.setText(Messages.NewItemDetailsWizardPage_ChosenItemType);
		}
		new Label(container, SWT.NONE);
		{
			lblWillBeAdded = new Label(container, SWT.NONE);
			lblWillBeAdded.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblWillBeAdded.setText(Messages.NewItemDetailsWizardPage_WillBeAddedTo);
		}
		{
			lblParentName = new Label(container, SWT.NONE);
			lblParentName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		}
		{
			lblName = new Label(container, SWT.NONE);
			lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblName.setText(Messages.NewItemDetailsWizardPage_Name);
		}
		{
			txtName = new Text(container, SWT.BORDER);
			txtName.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					if (txtName.getVisible()) {
						updateModel();
					}
				}
			});
			txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		}
		{
			lblFile1 = new Label(container, SWT.NONE);
			lblFile1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		}
		{
			txtFile1 = new Text(container, SWT.BORDER);
			txtFile1.addModifyListener(basicModifyListener);
			//txtFile1.
			txtFile1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			btnFileChoose1 = new Button(container, SWT.NONE);
			btnFileChoose1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					String fileName=dialog.open();
					if (fileName!=null) {
						txtFile1.setText(fileName);
						File testFile=new File(txtFile1.getText());
						if (testFile.exists()) {
							txtName.setText(testFile.getName().substring(0, testFile.getName().length()-4)); 
							updateModel();
						} 
					}
					
				}
			});
			btnFileChoose1.setText("..."); //$NON-NLS-1$
		}
		{
			lblField2 = new Label(container, SWT.NONE);
			lblField2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		}
		{
			txtField2 = new Text(container, SWT.BORDER);
			txtField2.addModifyListener(basicModifyListener);
			txtField2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		}
		{
			lblField3 = new Label(container, SWT.NONE);
			lblField3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		}
		{
			txtField3 = new Text(container, SWT.BORDER);
			txtField3.addModifyListener(basicModifyListener);
			txtField3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		}
		{
			lblNumField1 = new Label(container, SWT.NONE);
			lblNumField1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		}
		{
			spnField1 = new Spinner(container, SWT.BORDER);
			spnField1.addModifyListener(basicModifyListener);
			spnField1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
			spnField1.setMaximum(1000);
		}
		{
			lblDir1 = new Label(container, SWT.NONE);
			lblDir1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		}
		{
			txtDir1 = new Text(container, SWT.BORDER);
			txtDir1.addModifyListener(basicModifyListener);
			txtDir1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			btnDirChoose1 = new Button(container, SWT.NONE);
			btnDirChoose1.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					DirectoryDialog dialog=new DirectoryDialog(container.getShell(),SWT.OPEN);
					//répertoire par défaut si renseigné
					String defaultrep=RpgmPreferenceStore.getInstance().getString(RpgmPreferenceStore.LIBRARY_DIRECTORY);
					if (!defaultrep.equals("")){ //$NON-NLS-1$
						dialog.setFilterPath(defaultrep); 
					}
					String fileName=dialog.open();
					if (fileName!=null) {
						txtDir1.setText(fileName);
						File testFile=new File(txtDir1.getText());
						if (testFile.exists()) {
							txtName.setText(testFile.getName()); //$NON-NLS-1$ //$NON-NLS-2$
							updateModel();
						} 
					}
					
				}
			});
			btnDirChoose1.setText("..."); //$NON-NLS-1$
		}
		onEnterPage();		
	}


	private void hideAllFields(){
		spnField1.setVisible(false);
		lblNumField1.setVisible(false);
		txtField3.setVisible(false);
		lblField3.setVisible(false);
		txtField2.setVisible(false);
		lblField2.setVisible(false);
		lblFile1.setVisible(false);
		txtFile1.setVisible(false);
		btnFileChoose1.setVisible(false);
		lblDir1.setVisible(false);
		txtDir1.setVisible(false);
		btnDirChoose1.setVisible(false);
		//txtName.setText("");
		txtField2.setText(""); //$NON-NLS-1$
		txtField3.setText(""); //$NON-NLS-1$
		txtFile1.setText(""); //$NON-NLS-1$
	}
			
	
	public void onEnterPage() {
		//update UI to reflect previous choices
		noUIUpdate=true;
		hideAllFields();
		model=((NewItemWizard)getWizard()).getModel();
		parent=((NewItemWizard)getWizard()).getInitialSelection();
		if (model==null){
			System.out.println("model null"); //$NON-NLS-1$
			lblChosenItemType.setText("Null !!!"); //$NON-NLS-1$
		}
		if (parent==null){
			System.out.println("parent null"); //$NON-NLS-1$
			lblParentName.setText("Null !!!"); //$NON-NLS-1$
		}
		try {
			lblParentName.setText(((INameable)parent).getName());
		} catch (ClassCastException e) {
			//System.out.println("oep parent " +parent.getClass().getSimpleName());
			lblParentName.setText(parent.getClass().getSimpleName());
		} catch (NullPointerException e) {
			System.out.println("oep parent nullpointer " +parent.getClass().getSimpleName()); //$NON-NLS-1$
			lblParentName.setText(parent.getClass().getSimpleName());
		}
		if (model instanceof Campaign) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewCampaign);
			txtField2.setVisible(true);
			lblField2.setVisible(true);
			lblField2.setText(Messages.NewItemDetailsWizardPage_GameMaster);
			String defaultgm=RpgmPreferenceStore.getInstance().getString(RpgmPreferenceStore.DEFAULT_GM_NAME);
			if (!defaultgm.equals("")){ //$NON-NLS-1$
				txtField2.setText(defaultgm); 
			}			
			lblField2.pack();
		}
		if (model instanceof Library) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewLibrary);
		}
		if (model instanceof HttpDocument) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewWebDocument);
			//fichier
			lblField2.setVisible(true);
			lblField2.setText(Messages.NewItemDetailsWizardPage_URL);
			txtField2.setVisible(true);
			txtField2.setText(""); //$NON-NLS-1$
		}
		if (model instanceof HttpDocRessource) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewWebPage);
			//fichier
			lblField2.setVisible(true);
			lblField2.setText(Messages.NewItemDetailsWizardPage_URL);
			txtField2.setVisible(true);
			txtField2.setText(""); //$NON-NLS-1$
		}
		if (model instanceof PDFDocument) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewPdfDocument);
			//fichier
			lblFile1.setVisible(true);
			lblFile1.setText(Messages.NewItemDetailsWizardPage_PdfFileName);
			txtFile1.setVisible(true);
			btnFileChoose1.setVisible(true);
			dialog.setFilterExtensions(new String[]{"*.pdf"}); //$NON-NLS-1$
			dialog.setFilterNames(new String[]{Messages.NewItemDetailsWizardPage_PDFFiles}); 

			txtFile1.setText(""); //$NON-NLS-1$
		}
		if (model instanceof PDFDocRessource) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(((PDFDocument)parent).getName());
			//n° de page
			spnField1.setVisible(true);
			lblNumField1.setVisible(true);
			lblNumField1.setText(Messages.NewItemDetailsWizardPage_PageNumber);
		}
		if (model instanceof AudioDocument) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewAudioDocument);
			//fichier
			lblDir1.setVisible(true);
			lblDir1.setText(Messages.NewItemDetailsWizardPage_AudioFolderName);
			txtDir1.setVisible(true);
			btnDirChoose1.setVisible(true);
			//txtDir1.setText(""); //$NON-NLS-1$
		}
		if (model instanceof AudioRessource) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewAudioRessource);
			//fichier
			lblFile1.setVisible(true);
			lblFile1.setText(Messages.NewItemDetailsWizardPage_AudioFileName);
			txtFile1.setVisible(true);
			btnFileChoose1.setVisible(true);
			dialog.setFilterExtensions(new String[]{"*.mp3","*.ogg","*.wma","*.aac"}); //$NON-NLS-1$
			dialog.setFilterNames(new String[]{Messages.NewItemDetailsWizardPage_AudioFiles}); 
			txtFile1.setText(((AudioDocument)parent).getFolder());
		}
		if (model instanceof ImageDocument) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText("New Image Document");
			//fichier
			lblFile1.setVisible(true);
			lblFile1.setText("Image File");
			txtFile1.setVisible(true);
			btnFileChoose1.setVisible(true);
			dialog.setFilterExtensions(new String[]{"*.png","*.jpg","*.gif","*.bmp"}); //$NON-NLS-1$
			dialog.setFilterNames(new String[]{Messages.NewItemDetailsWizardPage_ImageFiles}); 
			txtFile1.setText(""); //$NON-NLS-1$
		}
		if (model instanceof Scenario) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewScenario);
		}
		if (model instanceof Sequence) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewSequence);
		}
		if (model instanceof Location) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewLocation);
		}
		if (model instanceof Group) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewGroup);
		}
		if (model instanceof Actor) {
			lblChosenItemType.setText(((INameable)model).getName());
			txtName.setText(Messages.NewItemDetailsWizardPage_NewActor);
		}		
		container.layout();
		noUIUpdate=false;
		canFlipToNextPage();
		txtName.setFocus();
		txtName.setSelection(0, txtName.getText().length());
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 */
	@Override
	public boolean canFlipToNextPage() {
		//System.out.println("nidwp.cftnp");
		//System.out.println(model.getClass().getSimpleName());
		//System.out.println(parent.getClass().getSimpleName());
		valid=false;
		if (txtName.getText().equals("")) { //$NON-NLS-1$
			setErrorMessage(Messages.NewItemDetailsWizardPage_NameMandatory);
			return false;
		} 
		if (txtField2.getText().equals("")) { //$NON-NLS-1$
			if (txtField2.getVisible()) {
				setErrorMessage(lblField2.getText() + Messages.NewItemDetailsWizardPage_Mandatory);
				return false;
			} 
		}
		if (txtField3.getText().equals("")) { //$NON-NLS-1$
			if (txtField3.getVisible()) {
				setErrorMessage(lblField3.getText() + Messages.NewItemDetailsWizardPage_Mandatory);
				return false;
			} 
		} 
		if (txtFile1.getVisible()) {
			if (txtFile1.getText().equals("")) { //$NON-NLS-1$
				setErrorMessage(lblFile1.getText() + Messages.NewItemDetailsWizardPage_Mandatory);
				return false;
			} 
			File testFile=new File(txtFile1.getText());
			if (!testFile.exists()) {
				setErrorMessage(lblFile1.getText() + Messages.NewItemDetailsWizardPage_MustExist);
				return false;
			} 
		} 
		if (spnField1.getText().equals("")) { //$NON-NLS-1$
			if (lblNumField1.getVisible()) {
				setErrorMessage(lblNumField1.getText() + Messages.NewItemDetailsWizardPage_Mandatory);
				return false;
			} 
		} 
		this.setErrorMessage(null);
		valid=true;
		return super.canFlipToNextPage();
	}

	public void updateModel() {
		//System.out.println("um2");
		if (model instanceof Campaign) {
			((Campaign)model).setName(txtName.getText());
			((Campaign)model).setGameMaster(txtField2.getText());
		}
		if (model instanceof Library) {
			((Library)model).setName(txtName.getText());
		}
		if (model instanceof HttpDocument) {
			((HttpDocument)model).setName(txtName.getText());
			((HttpDocument)model).setUrl(txtField2.getText());
		}
		if (model instanceof HttpDocRessource) {
			((HttpDocRessource)model).setName(txtName.getText());
			((HttpDocRessource)model).setUrl(txtField2.getText());
		}
		if (model instanceof PDFDocument) {
			((PDFDocument)model).setName(txtName.getText());
			((PDFDocument)model).setFileName(txtFile1.getText());
		}
		if (model instanceof PDFDocRessource) {
			((PDFDocRessource)model).setName(txtName.getText());
			((PDFDocRessource)model).setPageNumber(Integer.parseInt(spnField1.getText()));
		}
		if (model instanceof AudioDocument) {
			((AudioDocument)model).setName(txtName.getText());
			((AudioDocument)model).setFolder(txtDir1.getText());
		}
		if (model instanceof AudioRessource) {
			((AudioRessource)model).setName(txtName.getText());
			((AudioRessource)model).setFileName((txtFile1.getText()));
		}
		if (model instanceof ImageDocument) {
			((ImageDocument)model).setName(txtName.getText());
			((ImageDocument)model).setFileName(txtFile1.getText());
		}
		if (model instanceof Scenario) {
			((Scenario)model).setName(txtName.getText());
		}
		if (model instanceof Sequence) {
			((Sequence)model).setName(txtName.getText());
		}
		if (model instanceof Location) {
			((Location)model).setName(txtName.getText());
		}
		if (model instanceof Group) {
			((Group)model).setName(txtName.getText());
		}
		if (model instanceof Actor) {
			((Actor)model).setName(txtName.getText());
		}	
		if (noUIUpdate){return;}
		canFlipToNextPage();
		getWizard().getContainer().updateButtons();
	}

	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		return valid;
	}

	@Override
	public void setModel(IContaineable model) {
		
		
	}

	@Override
	public void setParent(IContainer parent) {
		
		
	}	
	
	
	
	
}
