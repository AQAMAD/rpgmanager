package com.delegreg.rpgm.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Text;

public class TextBox extends Composite{

	protected Text text;
	
	public void addControlListener(ControlListener listener) {
		text.addControlListener(listener);
	}

	public void addDisposeListener(DisposeListener listener) {
		text.addDisposeListener(listener);
	}

	public void addDragDetectListener(DragDetectListener listener) {
		text.addDragDetectListener(listener);
	}

	public void addFocusListener(FocusListener listener) {
		text.addFocusListener(listener);
	}

	public void addHelpListener(HelpListener listener) {
		text.addHelpListener(listener);
	}

	public void addKeyListener(KeyListener listener) {
		text.addKeyListener(listener);
	}

	public void addListener(int eventType, Listener listener) {
		text.addListener(eventType, listener);
	}

	public void addMenuDetectListener(MenuDetectListener listener) {
		text.addMenuDetectListener(listener);
	}

	public void addModifyListener(ModifyListener listener) {
		text.addModifyListener(listener);
	}

	public void addMouseListener(MouseListener listener) {
		text.addMouseListener(listener);
	}

	public void addMouseMoveListener(MouseMoveListener listener) {
		text.addMouseMoveListener(listener);
	}

	public void addMouseTrackListener(MouseTrackListener listener) {
		text.addMouseTrackListener(listener);
	}

	public void addMouseWheelListener(MouseWheelListener listener) {
		text.addMouseWheelListener(listener);
	}

	public void addPaintListener(PaintListener listener) {
		text.addPaintListener(listener);
	}

	public void addSelectionListener(SelectionListener listener) {
		text.addSelectionListener(listener);
	}

	public void addTraverseListener(TraverseListener listener) {
		text.addTraverseListener(listener);
	}

	public void addVerifyListener(VerifyListener listener) {
		text.addVerifyListener(listener);
	}

	public void append(String string) {
		text.append(string);
	}

	public void clearSelection() {
		text.clearSelection();
	}

	public void copy() {
		text.copy();
	}

	public void cut() {
		text.cut();
	}

	public void dispose() {
		text.dispose();
		super.dispose();
	}

	public boolean dragDetect(Event event) {
		return text.dragDetect(event);
	}

	public boolean dragDetect(MouseEvent event) {
		return text.dragDetect(event);
	}

	public boolean forceFocus() {
		return text.forceFocus();
	}

	public Accessible getAccessible() {
		return text.getAccessible();
	}

	public Color getBackground() {
		return text.getBackground();
	}

	public int getCaretLineNumber() {
		return text.getCaretLineNumber();
	}

	public Point getCaretLocation() {
		return text.getCaretLocation();
	}

	public int getCaretPosition() {
		return text.getCaretPosition();
	}

	public int getCharCount() {
		return text.getCharCount();
	}

	public Cursor getCursor() {
		return text.getCursor();
	}

	public Object getData() {
		return text.getData();
	}

	public Object getData(String key) {
		return text.getData(key);
	}

	public boolean getDoubleClickEnabled() {
		return text.getDoubleClickEnabled();
	}

	public boolean getDragDetect() {
		return text.getDragDetect();
	}

	public char getEchoChar() {
		return text.getEchoChar();
	}

	public boolean getEditable() {
		return text.getEditable();
	}

	public boolean getEnabled() {
		return text.getEnabled();
	}

	public Font getFont() {
		return text.getFont();
	}

	public Color getForeground() {
		return text.getForeground();
	}

	public ScrollBar getHorizontalBar() {
		return text.getHorizontalBar();
	}

	public int getLineCount() {
		return text.getLineCount();
	}

	public String getLineDelimiter() {
		return text.getLineDelimiter();
	}

	public int getLineHeight() {
		return text.getLineHeight();
	}

	public Listener[] getListeners(int eventType) {
		return text.getListeners(eventType);
	}

	public Menu getMenu() {
		return text.getMenu();
	}

	public String getMessage() {
		return text.getMessage();
	}

	public Point getSelection() {
		return text.getSelection();
	}

	public int getSelectionCount() {
		return text.getSelectionCount();
	}

	public String getSelectionText() {
		return text.getSelectionText();
	}


	public int getTabs() {
		return text.getTabs();
	}

	public String getText() {
		return text.getText();
	}

	public String getText(int start, int end) {
		return text.getText(start, end);
	}

	public int getTextLimit() {
		return text.getTextLimit();
	}

	public String getToolTipText() {
		return text.getToolTipText();
	}

	public ScrollBar getVerticalBar() {
		return text.getVerticalBar();
	}

	public boolean getVisible() {
		return text.getVisible();
	}


	public void insert(String string) {
		text.insert(string);
	}


	public boolean isFocusControl() {
		return text.isFocusControl();
	}

	public boolean isListening(int eventType) {
		return text.isListening(eventType);
	}


	public void notifyListeners(int eventType, Event event) {
		text.notifyListeners(eventType, event);
	}


	public void paste() {
		text.paste();
	}

	public void removeControlListener(ControlListener listener) {
		text.removeControlListener(listener);
	}

	public void removeDisposeListener(DisposeListener listener) {
		text.removeDisposeListener(listener);
	}

	public void removeDragDetectListener(DragDetectListener listener) {
		text.removeDragDetectListener(listener);
	}

	public void removeFocusListener(FocusListener listener) {
		text.removeFocusListener(listener);
	}

	public void removeHelpListener(HelpListener listener) {
		text.removeHelpListener(listener);
	}

	public void removeKeyListener(KeyListener listener) {
		text.removeKeyListener(listener);
	}

	public void removeListener(int eventType, Listener listener) {
		text.removeListener(eventType, listener);
	}

	public void removeMenuDetectListener(MenuDetectListener listener) {
		text.removeMenuDetectListener(listener);
	}

	public void removeModifyListener(ModifyListener listener) {
		text.removeModifyListener(listener);
	}

	public void removeMouseListener(MouseListener listener) {
		text.removeMouseListener(listener);
	}

	public void removeMouseMoveListener(MouseMoveListener listener) {
		text.removeMouseMoveListener(listener);
	}

	public void removeMouseTrackListener(MouseTrackListener listener) {
		text.removeMouseTrackListener(listener);
	}

	public void removeMouseWheelListener(MouseWheelListener listener) {
		text.removeMouseWheelListener(listener);
	}

	public void removePaintListener(PaintListener listener) {
		text.removePaintListener(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		text.removeSelectionListener(listener);
	}

	public void removeTraverseListener(TraverseListener listener) {
		text.removeTraverseListener(listener);
	}

	public void removeVerifyListener(VerifyListener listener) {
		text.removeVerifyListener(listener);
	}

	public void selectAll() {
		text.selectAll();
	}

	public void setBackground(Color color) {
		text.setBackground(color);
	}



	public void setCapture(boolean capture) {
		text.setCapture(capture);
	}

	public void setCursor(Cursor cursor) {
		text.setCursor(cursor);
	}

	public void setData(Object data) {
		text.setData(data);
	}

	public void setData(String key, Object value) {
		text.setData(key, value);
	}

	public void setDoubleClickEnabled(boolean doubleClick) {
		text.setDoubleClickEnabled(doubleClick);
	}

	public void setDragDetect(boolean dragDetect) {
		text.setDragDetect(dragDetect);
	}

	public void setEchoChar(char echo) {
		text.setEchoChar(echo);
	}

	public void setEditable(boolean editable) {
		text.setEditable(editable);
	}

	public void setEnabled(boolean enabled) {
		text.setEnabled(enabled);
	}

	public boolean setFocus() {
		text.setSelection(0, text.getText().length());
		return text.setFocus();
	}

	public void setFont(Font font) {
		text.setFont(font);
	}



	public void setMessage(String message) {
		text.setMessage(message);
	}


	public void setSelection(int start, int end) {
		text.setSelection(start, end);
	}

	public void setSelection(int start) {
		text.setSelection(start);
	}

	public void setSelection(Point selection) {
		text.setSelection(selection);
	}



	public void setTabs(int tabs) {
		text.setTabs(tabs);
	}

	public void setText(String string) {
		text.setText(string);
	}

	public void setTextLimit(int limit) {
		text.setTextLimit(limit);
	}

	public void setToolTipText(String string) {
		text.setToolTipText(string);
	}


	public void setVisible(boolean visible) {
		text.setVisible(visible);
	}

	public void showSelection() {
		text.showSelection();
	}


	public String toString() {
		return text.toString();
	}


	public TextBox(Composite parent, int style) {
		super(parent, style);
        this.setLayout(new FillLayout( ));
        text = new Text(this, SWT.BORDER);
        
		text.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				if (text.getParent().getLayoutData() instanceof GridData) {
					GridData lo = (GridData) text.getParent().getLayoutData();
					if (!lo.grabExcessHorizontalSpace){
						text.pack();
						text.getParent().layout();
					}
				}else{
					text.pack();
					text.getParent().layout();
				}
				;
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				text.selectAll();
			}
		});
        
        
        
	}
	
}
