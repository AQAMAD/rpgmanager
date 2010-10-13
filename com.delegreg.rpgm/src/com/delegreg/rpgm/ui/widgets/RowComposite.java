package com.delegreg.rpgm.ui.widgets;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class RowComposite extends Composite{


	public RowComposite(Composite parent, int style) {
		super(parent, style);
		RowLayout rl=new RowLayout();
		rl.marginLeft=0;
		this.setLayout(rl);
		// TODO Auto-generated constructor stub
	}

	public boolean setFocus() {
		//text.setSelection(0, text.getText().length());
		Control[] c;
		c=this.getChildren();
		if (!(c.length==0)){
			return c[0].setFocus();
		}
		return false;
	}



}
