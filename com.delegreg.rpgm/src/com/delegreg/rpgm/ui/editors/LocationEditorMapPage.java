package com.delegreg.rpgm.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.delegreg.library.model.ImageDocument;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Location;
import com.delegreg.rpgm.ui.widgets.MapImageCanvas;
import com.delegreg.rpgm.ui.widgets.RpgmWidgetFactory;

public class LocationEditorMapPage extends FormPage {

	final Point [] offset = new Point [1];

	public static final String ID = "com.delegreg.rpgm.editors.location.map"; //$NON-NLS-1$

	private IEditorInput input; 
	private Location lieu;

	private LocationEditor editor;

	private ScrolledForm form; 

	private MapImageCanvas canvas;

	
	public LocationEditorMapPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
		this.editor=(LocationEditor)editor;
	}

	public LocationEditorMapPage(FormEditor editor) {
		this(editor, ID, Messages.LocationEditorMapPage_Title);
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
		Composite parent=managedForm.getForm().getParent();
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		form = managedForm.getForm(); 
		form.setText(Messages.LocationEditor_Title + getName()); 
		toolkit.decorateFormHeading(form.getForm());

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		form.getBody().setLayout(layout); 

		canvas = RpgmWidgetFactory.createMapRessourcePopup(toolkit, form.getForm(), getSite(),lieu.getMapImage(), new Runnable(){
			@Override
			public void run() {
				if (lieu.getMapImage()==null){
					editor.setDirty(canvas.getData()!=null);
				}else{
					editor.setDirty(!lieu.getMapImage().equals(canvas.getData()));
				}
			}

		}) ;

		canvas.buildLocations(lieu.getSubLocations().toArray());
		
		canvas.contribute(form);
		
	}



	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return editor.isDirty();
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		if (canvas.getData()!=null){
			lieu.setMapImage(((ImageDocument) canvas.getData()));
		}
		editor.setDirty(false);
		super.doSave(monitor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
		this.input=input;
		lieu=(Location)input.getAdapter(Location.class);
	}

	private String getName() {
		return lieu.getName();
	}		

}
