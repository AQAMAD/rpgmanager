package com.delegreg.rpgm.ui.views;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import com.delegreg.library.model.IDocOrRessource;
import com.delegreg.library.model.ImageDocument;
import com.delegreg.rpgm.ui.widgets.RpgmImageCanvas;


public class ImageDocView extends ViewPart {
	
	public static final String ID = "com.delegreg.rpgm.views.image"; //$NON-NLS-1$
		
	private String fileName = ""; //$NON-NLS-1$
	private IDocOrRessource doc ; //$NON-NLS-1$
	private FormToolkit toolkit;
	private ScrolledForm form;
	private RpgmImageCanvas canvas; 	
	
	/**
	 * @wbp.parser.constructor
	 */
	public ImageDocView() {
		super();
	}
	
	public ImageDocView(String fileName) {
		this();
		this.openImage(fileName);
	}
	
	@Override
	public final void createPartControl(final Composite parent) {
		// TODO Auto-generated method stub
		try{


			toolkit = new FormToolkit(parent.getDisplay()); 
			form = toolkit.createScrolledForm(parent); 
			toolkit.decorateFormHeading(form.getForm());

			form.getBody().setLayout(new GridLayout(2,false)); 

			// Create the canvas for drawing  
			canvas = new RpgmImageCanvas( form.getBody());  
			canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

			
			canvas.contribute(getViewSite());
			
		}catch(Exception e){
			e.printStackTrace();
		}catch(Error e){
			e.printStackTrace();
		}

	}




	
	@Override
	public final void setFocus() {
		canvas.setFocus();
	}

	public final void dispose() {
		toolkit.dispose(); 
		super.dispose();
	}

	public final void openImage(final ImageDocument doc) {
		this.doc=doc;
		openImage(doc.getFileName());
	}

	public final void openImage(final String fileName) {
		
		if (fileName.equals(this.fileName)){
			return;
		}
		
		String shortFileName;
		
		this.fileName=fileName;

		boolean isEnabled=true;

		File checkFile;
		try {
			checkFile=new File(fileName);
			shortFileName=checkFile.getName();
		} catch (Exception e) {
			checkFile=new File(""); //$NON-NLS-1$
			shortFileName=""; //$NON-NLS-1$
		}
		
		if(!checkFile.exists()){
			System.out.println("file missing"); //$NON-NLS-1$
			isEnabled=false;
			return;
		} else {
			isEnabled=true;
			setPartName("Image Viewer : " + shortFileName);
		}

		if (isEnabled){
			canvas.loadImage(fileName);
		}
		/**
		 * add count and then buttons
		 */
		/**
		 * activate SWT buttons
		 **/
	}
}
