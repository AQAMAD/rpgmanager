package com.delegreg.rpgm.ui.wizards;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.delegreg.core.IContaineable;
import com.delegreg.core.IContainer;
import com.delegreg.core.INameable;
import com.delegreg.core.ui.wizardpages.INewContaineableDetailPage;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.core.RpgmRelationalAdapter;

public class SelectItemTypeWizardPage extends WizardPage implements INewContaineableDetailPage{

	private ArrayList<INameable> baseTypes=new ArrayList<INameable>();
	private TableViewer listView;

	public TableViewer getListView() {
		return listView;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public SelectItemTypeWizardPage(String pageName) {
		super(pageName);
	}

	public SelectItemTypeWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		this(pageName);
		this.setTitle(title);
		this.setImageDescriptor(titleImage);
	}


	private SelectItemTypeWizardPage getInstance(){
		return this;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 */
	@Override
	public boolean canFlipToNextPage() {
		if (((NewItemWizard)getWizard()).getModel()==null) {
			return false;
		} else {
			return super.canFlipToNextPage();
		}
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, false));
		setControl(container);
		{
			Label lblSelectTheType = new Label(container, SWT.NONE);
			lblSelectTheType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			lblSelectTheType.setText(Messages.SelectItemTypeWizardPage_Text);
		}
		{
			listView = new TableViewer(container, SWT.BORDER);
			listView.setLabelProvider(new WorkbenchLabelProvider());
			listView.setContentProvider(new ArrayContentProvider());
			listView.setInput(baseTypes);
			Table list = listView.getTable();
			list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

			listView.addSelectionChangedListener(new ISelectionChangedListener(){
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					// TODO Auto-generated method stub
					StructuredSelection selection=(StructuredSelection) event.getSelection();
					((NewItemWizard)getWizard()).setModel(null);
					if (selection.isEmpty()) {
						//System.out.println("no selected");
						getWizard().getContainer().updateButtons();
						return;
					}else {
						//System.out.println("selected");
						IContaineable elem = (IContaineable) selection.getFirstElement();
						((NewItemWizard)getWizard()).setModel(elem);						
						getWizard().getContainer().updateButtons();
					}
				}
			});
			listView.addDoubleClickListener(new IDoubleClickListener(){

				@Override
				public void doubleClick(DoubleClickEvent event) {
					// TODO Auto-generated method stub
					if (canFlipToNextPage()){
						getWizard().getContainer().showPage(getWizard().getNextPage(getInstance()));
					}
				}
				
			}
			
			);
			
		}
	}

	public void init(IContainer initialSelection) {
		try {
			RpgmRelationalAdapter adapter=new RpgmRelationalAdapter(initialSelection);
			for (Iterator iterator = adapter.getContentTypes().iterator(); iterator
					.hasNext();) {
				Class type = (Class) iterator.next();
				INameable obj =(INameable)type.newInstance();
				obj.setName(Messages.getByName("SelectItemTypeWizardPage_"+ type.getSimpleName())); //$NON-NLS-1$
				baseTypes.add(obj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
		//((WizardPage)super.getNextPage()).onEnterPage();
		return super.getNextPage();
	}

	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		return !(((NewItemWizard)getWizard()).getModel()==null);
	}

	@Override
	public void onEnterPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setModel(IContaineable model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(IContainer parent) {
		// TODO Auto-generated method stub
		
	}

	
	
}
