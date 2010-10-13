/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.delegreg.rpgm.statblocks.ui;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.delegreg.rpgm.statblocks.DefaultStatBlock;
import com.delegreg.rpgm.ui.widgets.RpgmWidgetFactory;

/**
 * @author dejan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DefaultStatBlockDetailsPage implements IDetailsPage {
	private IManagedForm mform;
	private DefaultStatBlock input;
	private Button [] choices;
	private static final String RTEXT_DATA =
			"<form><p>An example of a default statblock "+ //$NON-NLS-1$
			"to be used with generic actors.</p>"+ //$NON-NLS-1$
			"<p>It can contain simple details like <b>description</b> and <b>background</b>.</p></form>"; //$NON-NLS-1$
	private static final int browserHeightHint = 170;
	private Text name;
	private Text age;
	private Text height;
	private Text weight;
	private Browser desc;
	private Browser behav;
	private Browser backg;
	
	public DefaultStatBlockDetailsPage() {
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#initialize(org.eclipse.ui.forms.IManagedForm)
	 */
	public void initialize(IManagedForm mform) {
		this.mform = mform;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 5;
		layout.leftMargin = 5;
		layout.rightMargin = 2;
		layout.bottomMargin = 2;
		parent.setLayout(layout);

		FormToolkit toolkit = mform.getToolkit();
		Section s1 = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
		s1.marginWidth = 10;
		s1.setText("Default Statblock"); //$NON-NLS-1$
		s1.setDescription("Type the actor details you want to keep in those fields"); //$NON-NLS-1$
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		s1.setLayoutData(td);
		Composite client = toolkit.createComposite(s1);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = glayout.marginHeight = 0;
		glayout.numColumns = 2;
		client.setLayout(glayout);
		
		GridData gd;
		createSpacer(toolkit, client, 2);
		toolkit.createLabel(client, "Statblock Name"); //$NON-NLS-1$
		name = toolkit.createText(client, "");
		gd = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 10;
		name.setLayoutData(gd);
		name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				input.setName(name.getText());
			}
		});
		createSpacer(toolkit, client, 2);
		toolkit.createLabel(client, "Age"); //$NON-NLS-1$
		age = toolkit.createText(client, "0");
		toolkit.createLabel(client, "Height"); //$NON-NLS-1$
		height = toolkit.createText(client, "0");
		toolkit.createLabel(client, "Weight"); //$NON-NLS-1$
		weight = toolkit.createText(client, "0");
		createSpacer(toolkit, client, 2);
		desc = RpgmWidgetFactory.createDescriptionPopup(toolkit, client, "", 
				new Runnable() {
					@Override
					public void run() {
						System.out.println(desc.getText());
						input.setDescription(desc.getText());
						System.out.println(input.getDescription());
					}
				});
		((GridData)desc.getLayoutData()).heightHint=browserHeightHint;
		createSpacer(toolkit, client, 2);
		behav= RpgmWidgetFactory.createBrowserPopup(toolkit, client,"Behavior :","Click here to edit this actor's behavior", "",  
				new Runnable() {
					@Override
					public void run() {
						input.setBehavior(behav.getText());
					}
				});
		((GridData)behav.getLayoutData()).heightHint=browserHeightHint;
		createSpacer(toolkit, client, 2);
		backg=RpgmWidgetFactory.createBrowserPopup(toolkit, client,"Background :","Click here to edit this actor's background", "",  
				new Runnable() {
					@Override
					public void run() {
						input.setBackground(backg.getText());
					}
				});
		((GridData)backg.getLayoutData()).heightHint=browserHeightHint;
		createSpacer(toolkit, client, 2);
		
		FormText rtext = toolkit.createFormText(parent, true);
		rtext.setText(RTEXT_DATA, true, false);
		td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		rtext.setLayoutData(td);
		
		toolkit.paintBordersFor(s1);
		s1.setClient(client);
	}
	private void createSpacer(FormToolkit toolkit, Composite parent, int span) {
		Label spacer = toolkit.createLabel(parent, ""); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		spacer.setLayoutData(gd);
	}
	private void update() {
		name.setText(input.getName());
		age.setText(Integer.toString(input.getAge()));
		height.setText(Float.toString(input.getHeight()));
		weight.setText(Float.toString(input.getWeight()));
		if (input.getDescription()==null){
			desc.setText("");
		} else {
			desc.setText(input.getDescription());
		}
		if (input.getBehavior()==null){
			behav.setText("");
		} else {
			behav.setText(input.getBehavior());
		}
		if (input.getBackground()==null){
			backg.setText("");
		} else {
			backg.setText(input.getBackground());
		}
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#inputChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size()==1) {
			input = (DefaultStatBlock)ssel.getFirstElement();
		}
		else
			input = null;
		update();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#commit()
	 */
	public void commit(boolean onSave) {
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#setFocus()
	 */
	public void setFocus() {
		choices[0].setFocus();
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#dispose()
	 */
	public void dispose() {
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#isDirty()
	 */
	public boolean isDirty() {
		return false;
	}
	public boolean isStale() {
		return false;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#refresh()
	 */
	public void refresh() {
		update();
	}
	public boolean setFormInput(Object input) {
		return false;
	}
}
