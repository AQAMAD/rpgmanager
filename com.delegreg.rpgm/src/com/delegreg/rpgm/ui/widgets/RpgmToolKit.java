package com.delegreg.rpgm.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.HyperlinkGroup;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.internal.forms.widgets.FormUtil;

public class RpgmToolKit extends FormToolkit {

	FormToolkit toolkit;

	VisibilityHandler visibilityHandler;

	public RpgmToolKit(FormToolkit toolkit) {
		super(toolkit.getColors());
		visibilityHandler = new VisibilityHandler();
		this.toolkit=toolkit;
	}

	private static class VisibilityHandler extends FocusAdapter {
		public void focusGained(FocusEvent e) {
			Widget w = e.widget;
			if (w instanceof Control) {
				FormUtil.ensureVisible((Control) w);
			}
		}
	}
	
	@Override
	public void adapt(Composite composite) {
		// TODO Auto-generated method stub
		toolkit.adapt(composite);
	}

	@Override
	public void adapt(Control control, boolean trackFocus, boolean trackKeyboard) {
		// TODO Auto-generated method stub
		toolkit.adapt(control, trackFocus, trackKeyboard);
	}

	@Override
	public Button createButton(Composite parent, String text, int style) {
		// TODO Auto-generated method stub
		return toolkit.createButton(parent, text, style);
	}

	@Override
	public Composite createComposite(Composite parent, int style) {
		// TODO Auto-generated method stub
		return toolkit.createComposite(parent, style);
	}

	@Override
	public Composite createComposite(Composite parent) {
		// TODO Auto-generated method stub
		return toolkit.createComposite(parent);
	}

	@Override
	public Composite createCompositeSeparator(Composite parent) {
		// TODO Auto-generated method stub
		return toolkit.createCompositeSeparator(parent);
	}

	@Override
	public ExpandableComposite createExpandableComposite(Composite parent,
			int expansionStyle) {
		// TODO Auto-generated method stub
		return toolkit.createExpandableComposite(parent, expansionStyle);
	}

	@Override
	public Form createForm(Composite parent) {
		// TODO Auto-generated method stub
		return toolkit.createForm(parent);
	}

	@Override
	public FormText createFormText(Composite parent, boolean trackFocus) {
		// TODO Auto-generated method stub
		return toolkit.createFormText(parent, trackFocus);
	}

	@Override
	public Hyperlink createHyperlink(Composite parent, String text, int style) {
		// TODO Auto-generated method stub
		return toolkit.createHyperlink(parent, text, style);
	}

	@Override
	public ImageHyperlink createImageHyperlink(Composite parent, int style) {
		// TODO Auto-generated method stub
		return toolkit.createImageHyperlink(parent, style);
	}

	@Override
	public Label createLabel(Composite parent, String text, int style) {
		// TODO Auto-generated method stub
		return toolkit.createLabel(parent, text, style);
	}

	@Override
	public Label createLabel(Composite parent, String text) {
		// TODO Auto-generated method stub
		return toolkit.createLabel(parent, text);
	}

	@Override
	public ScrolledPageBook createPageBook(Composite parent, int style) {
		// TODO Auto-generated method stub
		return toolkit.createPageBook(parent, style);
	}

	@Override
	public ScrolledForm createScrolledForm(Composite parent) {
		// TODO Auto-generated method stub
		return toolkit.createScrolledForm(parent);
	}

	@Override
	public Section createSection(Composite parent, int sectionStyle) {
		// TODO Auto-generated method stub
		return toolkit.createSection(parent, sectionStyle);
	}

	@Override
	public Label createSeparator(Composite parent, int style) {
		// TODO Auto-generated method stub
		return toolkit.createSeparator(parent, style);
	}

	@Override
	public Table createTable(Composite parent, int style) {
		// TODO Auto-generated method stub
		return toolkit.createTable(parent, style);
	}

	@Override
	public Text createText(Composite parent, String value, int style) {
		// TODO Auto-generated method stub
		return toolkit.createText(parent, value, style);
	}

	@Override
	public Text createText(Composite parent, String value) {
		// TODO Auto-generated method stub
		return toolkit.createText(parent, value);
	}

	public DBText createDBText(Composite parent, String value) {
		DBText text=new DBText(parent,SWT.SINGLE);
		text.setText(value);
		return text;
		
	}
	
	public DBSpinner createDBSpinner(Composite parent, int value,int min, int max) {
		Spinner spn=createSpinner(parent,min,max);
		spn.setSelection(value);
		return new DBSpinner(spn);
	}

	public DBIntegerText createDBNumericText(Composite parent, int value) {
		DBIntegerText nt=new DBIntegerText(parent,SWT.SINGLE);
		nt.setValue(value);
		return nt;
	}

	public DBCombo createDBCombo(Composite parent, int selected,int[] codeList,String[] libList) {
		Combo cmb=createCombo(parent);
		DBCombo dbcmb=new DBCombo(cmb);
		dbcmb.initialize(codeList,libList);
		return dbcmb;
	}

	public DBCombo createDBCombo(Composite parent, String selected,String[] libList) {
		Combo cmb=createCombo(parent,true);
		DBCombo dbcmb=new DBCombo(cmb);
		dbcmb.initialize(libList);
		return dbcmb;
	}
	
	public void createSpacer(Composite parent, int span) {
		Label spacer = toolkit.createLabel(parent, ""); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		spacer.setLayoutData(gd);
	}	
	
	public Composite createRow(Composite parent) {
		RowComposite row = new RowComposite(parent,parent.getStyle()); //$NON-NLS-1$
		return row;
	}	
	
	private Spinner createSpinner(Composite client,int min,int max){
		Spinner spn=new Spinner(client,client.getStyle());
		spn.setMinimum(min);
		spn.setMaximum(max);
		spn.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		return spn;
	}

	private Combo createCombo(Composite client){
		return createCombo(client,false);
	}
	
	private Combo createCombo(Composite client,boolean allowNew){
		Combo cmb;
		if (allowNew){
			cmb=new Combo(client,client.getStyle());
		}else{
			cmb=new Combo(client,client.getStyle()|SWT.READ_ONLY);
		}
		return cmb;
	}
	
	@Override
	public Tree createTree(Composite parent, int style) {
		// TODO Auto-generated method stub
		return toolkit.createTree(parent, style);
	}

	@Override
	public void decorateFormHeading(Form form) {
		// TODO Auto-generated method stub
		toolkit.decorateFormHeading(form);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		toolkit.dispose();
	}

	@Override
	public int getBorderMargin() {
		// TODO Auto-generated method stub
		return toolkit.getBorderMargin();
	}

	@Override
	public int getBorderStyle() {
		// TODO Auto-generated method stub
		return toolkit.getBorderStyle();
	}

	@Override
	public FormColors getColors() {
		// TODO Auto-generated method stub
		return toolkit.getColors();
	}

	@Override
	public HyperlinkGroup getHyperlinkGroup() {
		// TODO Auto-generated method stub
		return toolkit.getHyperlinkGroup();
	}

	@Override
	public int getOrientation() {
		// TODO Auto-generated method stub
		return toolkit.getOrientation();
	}

	@Override
	public void paintBordersFor(Composite parent) {
		// TODO Auto-generated method stub
		toolkit.paintBordersFor(parent);
	}

	@Override
	public void refreshHyperlinkColors() {
		// TODO Auto-generated method stub
		toolkit.refreshHyperlinkColors();
	}

	@Override
	public void setBackground(Color bg) {
		// TODO Auto-generated method stub
		toolkit.setBackground(bg);
	}

	@Override
	public void setBorderStyle(int style) {
		// TODO Auto-generated method stub
		toolkit.setBorderStyle(style);
	}

	@Override
	public void setOrientation(int orientation) {
		// TODO Auto-generated method stub
		toolkit.setOrientation(orientation);
	}


}
