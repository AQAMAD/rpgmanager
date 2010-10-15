package com.delegreg.library.ui.wizardpages;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;
import com.delegreg.core.INameable;
import com.delegreg.core.ui.wizardpages.INewContaineableDetailPage;
import com.delegreg.library.Messages;
import com.delegreg.library.model.Library;

public class NewLibraryItemPage extends WizardPage implements INewContaineableDetailPage {
	private Label lblChosenItemType ;
	private Text txtName;
	private Label lblName;
	private Label lblWillBeAdded;
	private Label lblParentName;
	private Composite container;
	
	//the models
	private Library model;
	private IContainer parent;
	private boolean noUIUpdate=false;
	
	/**
	 * Create the wizard.
	 */
	public NewLibraryItemPage(String pageName) {
		super(pageName);
	}

	/**
	 * @wbp.parser.constructor
	 */
	public NewLibraryItemPage(String pageName, String title,
			ImageDescriptor titleImage) {
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
		
		ModifyListener basicModifyListener=new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModel();
			}
		};

		setControl(container);
		{
			Label lbl1 = new Label(container, SWT.NONE);
			lbl1.setText(Messages.WizardPage_ChosenItemType);
		}
		{
			lblChosenItemType = new Label(container, SWT.NONE);
			lblChosenItemType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			lblChosenItemType.setText(Messages.WizardPage_ChosenItemType);
		}
		new Label(container, SWT.NONE);
		{
			lblWillBeAdded = new Label(container, SWT.NONE);
			lblWillBeAdded.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblWillBeAdded.setText(Messages.WizardPage_WillBeAddedTo);
		}
		{
			lblParentName = new Label(container, SWT.NONE);
			lblParentName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		}
		{
			lblName = new Label(container, SWT.NONE);
			lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblName.setText(Messages.WizardPage_Name);
		}
		{
			txtName = new Text(container, SWT.BORDER);
			txtName.addModifyListener(basicModifyListener);
			txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		}
		onEnterPage();
	}

	public void onEnterPage() {
		//update UI to reflect previous choices
		noUIUpdate=true;
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
			lblParentName.setText(parent.getClass().getSimpleName());
		} catch (NullPointerException e) {
			lblParentName.setText(parent.getClass().getSimpleName());
		}
		lblChosenItemType.setText(((INameable)model).getName());
		txtName.setText(Messages.NewLibrary);
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
		//getWizard.setComplete(false);
		return super.canFlipToNextPage();
	}

	public void updateModel() {
		model.setName(txtName.getText());
		if (noUIUpdate){return;}
		getWizard().getContainer().updateButtons();
	}

	@Override
	public void setModel(IContaineable model) {
		this.model=(Library) model;
		
	}

	@Override
	public void setParent(IContainer parent) {
		this.parent=parent;
	}

	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		if (txtName==null) { //$NON-NLS-1$
			return false;
		}
		if (txtName.getText().equals("")) { //$NON-NLS-1$
			setErrorMessage(Messages.WizardPage_NameMandatory);
			return false;
		} 
		this.setErrorMessage(null);
		return true;
	}	
	
	
	
	
}
