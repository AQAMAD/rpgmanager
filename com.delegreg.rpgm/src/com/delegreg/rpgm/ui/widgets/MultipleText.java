package com.delegreg.rpgm.ui.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.ui.IImageKeys;

public class MultipleText extends Composite{

	private Image plusimg=AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.PLUS).createImage();
	private Image minusimg=AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.MINUS).createImage();
	
	private Label plusimage;
	private ArrayList<Label> minusimages;
	
	protected ArrayList<String> values;

	protected ArrayList<TextBox> boxes;
	
	public MultipleText(final Composite parent, int style) {
		super(parent, style);
		 values=new ArrayList<String>();
	     boxes = new ArrayList<TextBox>();
         minusimages=new ArrayList<Label>();

         this.setLayout(new RowLayout());
         
		 values.add("toto");
		 values.add("tata");
		
		 rebuild();

	 
		 
	  }

	private class BoxModifyListener implements ModifyListener{

		@Override
		public void modifyText(ModifyEvent e) {
			// TODO Auto-generated method stub
			int index=(Integer) e.widget.getData();
			Text box=(Text) e.widget;
			values.set(index, box.getText());			
		}
		
	}
	
	
	
	private void rebuild(){
		conditionalDisposeControlList(boxes);
		boxes.clear();
		conditionalDisposeControlList(minusimages);
		minusimages.clear();

		if ((plusimage != null) && (!plusimage.isDisposed())) {
			plusimage.dispose();
		}

		if (values.size()>1){
			for (int i = 0; i < values.size()-1; i++) {
				TextBox box=new TextBox(this,SWT.BORDER);
				box.setText(values.get(i));
				box.setData(i);
				box.addModifyListener(new BoxModifyListener());
				boxes.add(box);
			    Label minusimage = new Label(this, SWT.BORDER);
				//minusimage.setText("-");
				minusimage.setImage(minusimg);				
				minusimage.setData(i);
				minusimages.add(minusimage);
				minusimage.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseUp(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						int index=(Integer) e.widget.getData();
						values.remove(index);
						rebuild();
					}
					
					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		}
		 
		TextBox box=new TextBox(this,SWT.BORDER);
		if (values.size()==0){
			values.add("");
		}
		box.setText(values.get(values.size()-1));
		box.setData(values.size()-1);
		box.addModifyListener(new BoxModifyListener());
		boxes.add(box);
		plusimage = new Label(this, SWT.BORDER);
		plusimage.setImage(plusimg);				
		//plusimage.setText("+");
		plusimage.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				values.add("");
				rebuild();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		layout(true);
	}
	
	
	private void conditionalDisposeControlList(List boxes){
		for (Iterator iterator = boxes.iterator(); iterator.hasNext();) {
			Control box = (Control) iterator.next();
			if ((box != null) && (!box.isDisposed())) {
				box.dispose();
			}
		}
	}
	
	
	public void setText(ArrayList<String> values){
		//TODO
	}

	public ArrayList<String> getText(){
		return values;
	}
	
}
