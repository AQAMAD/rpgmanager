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
package com.delegreg.rpgm.games.basic.statblocks.ui;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.delegreg.rpgm.games.basic.model.Sizes;
import com.delegreg.rpgm.games.basic.model.Types;
import com.delegreg.rpgm.games.basic.statblocks.Abilities;
import com.delegreg.rpgm.games.basic.statblocks.D20StatBlock;
import com.delegreg.rpgm.ui.widgets.DBCombo;
import com.delegreg.rpgm.ui.widgets.DBSpinner;
import com.delegreg.rpgm.ui.widgets.DBText;
import com.delegreg.rpgm.ui.widgets.RpgmToolKit;

/**
 * @author dejan
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class D20StatBlockDetailsPage implements IDetailsPage {
	private IManagedForm mform;
	private D20StatBlock input;
	private static final String RTEXT_DATA =
			"<form><p>An example of a D20 statblock "+ //$NON-NLS-1$
			"to be used with actors requiring D20 stats.</p>"+ //$NON-NLS-1$
			"<p>It is based on the monster statblock format, <b>other</b> statblocks <b>will</b> be added in the future.</p></form>"; //$NON-NLS-1$
//	private static final int browserHeightHint = 170;
	private DBText name;
	private DBCombo size;
	private DBCombo type;
	//code subtypes
	private DBText HD;
	private DBSpinner HP;
	
	private DBSpinner strength;
	private Label strmod;
	private DBSpinner dexterity;
	private Label dexmod;
	private DBSpinner constitution;
	private Label conmod;
	private DBSpinner intelligence;
	private Label intmod;
	private DBSpinner wisdom;
	private Label wismod;
	private DBSpinner charisma;
	private Label chamod;

	private modsUpdate modUpdate=new modsUpdate();
	private DBSpinner Init;
	private DBSpinner BAB;
	private DBSpinner Grapple;
	private DBSpinner AC;
	private DBText ACDetails;
	private DBSpinner TAC;
	private DBSpinner FFAC;
	private DBText Space;
	private DBText SpecialAttacks;
	private DBText SpecialQualities;
	private DBText Environment;
	private DBText Organization;
	private DBText Treasure;
	private DBText Alignment;
	private DBText Advancement;
	private DBSpinner CR;
	private DBSpinner LA;
	
	private final class modsUpdate implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			updateMods();
		}};
	
	public D20StatBlockDetailsPage() {
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
		RpgmToolKit toolkit = new RpgmToolKit(mform.getToolkit());
		//layout is ok, start entering data
		//first section should concern only with the name of the SB
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
		glayout.numColumns = 3;
		client.setLayout(glayout);
		GridData gd;
		toolkit.createSpacer(client, 3);
		toolkit.createLabel(client, "Statblock Name"); //$NON-NLS-1$
		name = toolkit.createDBText(client, "");
		gd = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 10;
		gd.horizontalSpan = 2;
		name.setLayoutData(gd);
		toolkit.createSpacer(client, 3);
		// size and type
		toolkit.createLabel(client, "Size/Type"); //$NON-NLS-1$
		size = toolkit.createDBCombo(client, Sizes.Medium,Sizes.getSizeCodes(),Sizes.getSizeNames());
		type = toolkit.createDBCombo(client, Types.Humanoid,Types.getTypes());
		toolkit.createLabel(client, "HD/HP"); //$NON-NLS-1$
		HD = toolkit.createDBText(client, "");
		HP = toolkit.createDBSpinner(client, 0,0,10000);
		toolkit.createLabel(client, "Init"); //$NON-NLS-1$
		Init = toolkit.createDBSpinner(client, 0,-50,50);
		gd = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 10;
		gd.horizontalSpan = 2;
		Init.setLayoutData(gd);
		toolkit.createLabel(client, "BAB/Grapple"); //$NON-NLS-1$
		BAB = toolkit.createDBSpinner(client, 0,-100,100);
		Grapple = toolkit.createDBSpinner(client, 0,-100,100);
		toolkit.createLabel(client, "Armor Class"); //$NON-NLS-1$
		AC = toolkit.createDBSpinner(client, 0,-100,100);
		ACDetails = toolkit.createDBText(client, "");
		toolkit.createLabel(client, "Touch/Flatfooted AC"); //$NON-NLS-1$
		TAC = toolkit.createDBSpinner(client, 0,-100,100);
		FFAC = toolkit.createDBSpinner(client, 0,-100,100);
		Space=createFullWidthDBText(toolkit, client, "Space/Reach");
		SpecialAttacks=createFullWidthDBText(toolkit, client, "Special Attacks");
		SpecialQualities=createFullWidthDBText(toolkit, client, "Special Qualities");
		Environment=createFullWidthDBText(toolkit, client, "Environment");
		Organization=createFullWidthDBText(toolkit, client, "Organization");
		Treasure=createFullWidthDBText(toolkit, client, "Treasure");
		Alignment=createFullWidthDBText(toolkit, client, "Alignment");
		Advancement=createFullWidthDBText(toolkit, client, "Advancement");
		toolkit.createLabel(client, "CR/LA"); //$NON-NLS-1$
		CR = toolkit.createDBSpinner(client, 0,0,20);
		LA = toolkit.createDBSpinner(client, 0,0,10);
		
		
		toolkit.createSpacer(client, 3);
		toolkit.createLabel(client, "Ability"); 
		toolkit.createLabel(client, "Score"); 
		toolkit.createLabel(client, "Modifier"); 
		toolkit.createLabel(client, "Strength"); 
		//strength = toolkit.createText(client, "10");
		strength = toolkit.createDBSpinner(client,3,0,45);
		strength.addModifyListener(modUpdate);
		strmod=toolkit.createLabel(client, "0"); //$NON-NLS-1$
		toolkit.createLabel(client, "Dexterity"); 
		dexterity = toolkit.createDBSpinner(client,3,0,45);
		dexterity.addModifyListener(modUpdate);
		dexmod=toolkit.createLabel(client, "0"); //$NON-NLS-1$
		toolkit.createLabel(client, "Constitution"); 
		constitution = toolkit.createDBSpinner(client,3,0,45);
		constitution.addModifyListener(modUpdate);
		conmod=toolkit.createLabel(client, "0"); //$NON-NLS-1$
		toolkit.createLabel(client, "Intelligence"); 
		intelligence = toolkit.createDBSpinner(client,3,0,45);
		intelligence.addModifyListener(modUpdate);
		intmod=toolkit.createLabel(client, "0"); //$NON-NLS-1$
		toolkit.createLabel(client, "Wisdom"); 
		wisdom = toolkit.createDBSpinner(client,3,0,45);
		wisdom.addModifyListener(modUpdate);
		wismod=toolkit.createLabel(client, "0"); //$NON-NLS-1$
		toolkit.createLabel(client, "Charisma"); 
		charisma = toolkit.createDBSpinner(client,3,0,45);
		charisma.addModifyListener(modUpdate);
		chamod=toolkit.createLabel(client, "0"); //$NON-NLS-1$
		toolkit.createSpacer(client, 3);

		FormText rtext = toolkit.createFormText(parent, true);
		rtext.setText(RTEXT_DATA, true, false);
		td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		rtext.setLayoutData(td);
		
		toolkit.paintBordersFor(s1);
		s1.setClient(client);
	}

	private DBText createFullWidthDBText(RpgmToolKit toolkit,Composite client,String label){
		toolkit.createLabel(client, label); //$NON-NLS-1$
		DBText dbt=toolkit.createDBText(client, "");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 10;
		gd.horizontalSpan = 2;
		dbt.setLayoutData(gd);
		return dbt;
	}
	
	private void update() {
		name.setDataBoundObject(input, "Name");
		size.setDataBoundObject(input, "Size");
		type.setDataBoundObject(input, "Type");
		//name.setText(input.getName());
		HD.setDataBoundObject(input, "HD");
		HP.setDataBoundObject(input, "HP");
		Init.setDataBoundObject(input, "Init");
		BAB.setDataBoundObject(input, "BAB");
		Grapple.setDataBoundObject(input, "Grapple");
		//
		AC.setDataBoundObject(input, "AC");
		ACDetails.setDataBoundObject(input, "ACDetails");
		TAC.setDataBoundObject(input, "TAC");
		FFAC.setDataBoundObject(input, "FFAC");

		Space.setDataBoundObject(input, "SpaceReach");
		SpecialAttacks.setDataBoundObject(input, "SpecialAttacks");
		SpecialQualities.setDataBoundObject(input, "SpecialQualities");
		Environment.setDataBoundObject(input, "Environment");
		Organization.setDataBoundObject(input, "Organisation");
		Treasure.setDataBoundObject(input, "Treasure");
		Alignment.setDataBoundObject(input, "Alignment");
		Advancement.setDataBoundObject(input, "Advancment");

		CR.setDataBoundObject(input, "ChallengeRating");
		LA.setDataBoundObject(input, "LevelAdjustment");
		
		//strength.setText(Integer.toString(input.getAbilities().getStrength()));
		strength.setDataBoundObject(input.getAbilities(), "Strength");
		dexterity.setDataBoundObject(input.getAbilities(), "Dexterity");
		constitution.setDataBoundObject(input.getAbilities(), "Constitution");
		intelligence.setDataBoundObject(input.getAbilities(), "Intelligence");
		wisdom.setDataBoundObject(input.getAbilities(), "Wisdom");
		charisma.setDataBoundObject(input.getAbilities(), "Charisma");
		updateMods();

	}

	private void updateMods(){
		strmod.setText(Integer.toString(Abilities.getModifier(input.getAbilities().getStrength())));
		dexmod.setText(Integer.toString(Abilities.getModifier(input.getAbilities().getDexterity())));
		conmod.setText(Integer.toString(Abilities.getModifier(input.getAbilities().getConstitution())));
		intmod.setText(Integer.toString(Abilities.getModifier(input.getAbilities().getIntelligence())));
		wismod.setText(Integer.toString(Abilities.getModifier(input.getAbilities().getWisdom())));
		chamod.setText(Integer.toString(Abilities.getModifier(input.getAbilities().getCharisma())));
		strmod.pack();
		dexmod.pack();
		conmod.pack();
		intmod.pack();
		wismod.pack();
		chamod.pack();
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#inputChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection)selection;
		if (ssel.size()==1) {
			input = (D20StatBlock)ssel.getFirstElement();
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
		//choices[0].setFocus();
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
