package com.delegreg.rpgm.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;

public class IntegerText extends TextBox{

	protected int value;

	public int getValue() {
		try {
			return Integer.parseInt(text.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}


	public void setValue(int val) {
		text.setText(String.valueOf(val));
	}


	public IntegerText(final Composite parent, int style) {
		super(parent, style);
		super.text.setText("0");
        
		super.text.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				String thetext=text.getText();
				value=Integer.parseInt(thetext);
				if (!(String.valueOf(value)==thetext)){
					text.setText(String.valueOf(value));
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
        
        super.text.addKeyListener(new KeyListener() {

        	@Override
        	public void keyReleased(KeyEvent e) {
        		// TODO Auto-generated method stub

        	}

        	@Override
        	public void keyPressed(KeyEvent e) {
        		// TODO Auto-generated method stub
        		if ( e.character == '-' && text.getCaretPosition()==0 && !text.getText().startsWith("-") )
        		{
        			e.doit=true;
        		}
        		else if ( e.character == '0' && text.getCaretPosition()==text.getText().length())
        		{
        			e.doit=true;
        		}
        		else if ( Character.isDigit(e.character) )
        		{
        			e.doit=true;
        		}
        		else if ( e.character==SWT.BS 
        				|| e.character==SWT.TAB 
        				|| e.character==SWT.DEL
        				|| e.character==SWT.ARROW_LEFT
        				|| e.character==SWT.ARROW_RIGHT
        				)
        		{
        			e.doit=true;
        		}
        		else
        		{
        			e.doit=false;
        		}
        	}
        });
        
        
	}
	
}
