package com.delegreg.rpgm.ui.editors;

import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.Libraries;
import com.delegreg.library.model.Library;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Sequence;
import com.delegreg.rpgm.ui.IImageKeys;
import com.delegreg.rpgm.ui.actions.EditItemAction;

public class SequenceEditorAudioRessourcesPage extends FormPage {
	public static final String ID = "com.delegreg.rpgm.editors.sequence.audio"; //$NON-NLS-1$
	private ScrolledForm form; 
	private Sequence sequ;
	private IEditorInput input; 
	
	private SequenceEditor editor;
	private TreeViewer viewer;
	private EditItemAction editAction;
	
	public SequenceEditorAudioRessourcesPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
		this.editor=(SequenceEditor)editor;
	}

	public SequenceEditorAudioRessourcesPage(FormEditor editor) {
		this(editor, ID, "Audio Resources");
		//block=new StatBlocksBlock(this);
	}	
	
	@Override
	public final void doSave(IProgressMonitor monitor) {
		editor.setDirty(false);
	}

	@Override
	public void doSaveAs() {
		System.out.println("SequenceEditor.doSaveAs()"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
		this.input=input;
	    sequ=(Sequence)this.input.getAdapter(Sequence.class);
	}

	@Override
	public boolean isDirty() {
		return editor.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		//System.out.println("CampaignEditor.isSaveAsAllowed()"); //$NON-NLS-1$
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
		editAction = new EditItemAction(getSite().getWorkbenchWindow());
		FormToolkit toolkit = managedForm.getToolkit();
		form = managedForm.getForm(); 
		form.setText(Messages.SequenceEditor_Title + getName()); 
		toolkit.decorateFormHeading(form.getForm());
		GridLayout layout = new GridLayout();
		form.getBody().setLayout(layout); 
		Section section = toolkit.createSection(form.getBody(), Section.DESCRIPTION|Section.TITLE_BAR);
		section.setText("Audio resources for this sequence"); //$NON-NLS-1$
		section.setDescription("Manage the audio ressources for this sequence, audio ressources can be background music, sound effects, etc."); //$NON-NLS-1$
		section.marginWidth = 10;
		section.marginHeight = 5;
		section.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		client.setLayoutData(gd);

		Tree t = toolkit.createTree(client, SWT.NULL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalSpan = 2;
		t.setLayoutData(gd);
		toolkit.paintBordersFor(client);
		Button b = toolkit.createButton(client, "Add playlist" , SWT.PUSH); //$NON-NLS-1$
		b.setToolTipText("Add a playlist to this sequence");
		b.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.AUDIO).createImage());
		//b.setData(sb);
		b.addSelectionListener(new SelectionListener() { 
			@Override
			public void widgetSelected(SelectionEvent e) {
				ElementTreeSelectionDialog dialog=new ElementTreeSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
				dialog.setInput(Application.getCurrentCampaigns());
				dialog.addFilter(new ViewerFilter(){
					@Override
					public boolean select(Viewer viewer, Object parentElement,
							Object element) {
						return element instanceof Libraries || element instanceof Library || 
						element instanceof AudioDocument 
						;
					}});
				dialog.setValidator(new ISelectionStatusValidator(){
					private IStatus fgErrorStatus = new Status(IStatus.ERROR,Application.PLUGIN_ID, ""); //$NON-NLS-1$
					private IStatus fgOKStatus = new Status(IStatus.OK,Application.PLUGIN_ID,""); //$NON-NLS-1$
					@Override
					public IStatus validate(Object[] selection) {
						for (int i = 0; i < selection.length; i++) {
							if (!(selection[i] instanceof AudioDocument)){
								return fgErrorStatus;
							}
						}
						return fgOKStatus;
					}});
				dialog.setTitle("Select a playlist to add");
				dialog.setDoubleClickSelects(true);
				dialog.setMessage("Select a playlist to add to this sequence");
				dialog.open();
				//run the runnable to update ressources
				AudioDocument a=(AudioDocument)dialog.getFirstResult();
				sequ.getAudioRessources().addlink(a);
				viewer.refresh();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		b.setLayoutData(gd);

		Button b2 = toolkit.createButton(client, "Remove", SWT.PUSH); //$NON-NLS-1$
		b2.setImage(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageKeys.DELETEITEM).createImage());
		b2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Library audio=sequ.getAudioRessources();
				StructuredSelection sel=(StructuredSelection) viewer.getSelection();
				for (Iterator iterator = sel.iterator(); iterator
						.hasNext();) {
					Object o = iterator.next();
					if (audio.contains(o)){
						audio.remove(o);
					}
				}
				viewer.refresh();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		b2.setLayoutData(gd);

		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TreeViewer(t);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection){
					IStructuredSelection sel=(IStructuredSelection) event.getSelection();
					editAction.setSelection(sel.getFirstElement());
				}
			}
		});
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				editAction.run();
			}
		});
		viewer.setLabelProvider(new WorkbenchLabelProvider());
		viewer.setContentProvider(new BaseWorkbenchContentProvider());
		viewer.setInput(sequ.getAudioRessources());
		//viewer.setInput(Application.getCurrentCampaigns());
		}
	
	
	@Override
	public void setFocus() {
		form.setFocus(); 

	}

	private String getName() {
	    return sequ.getName();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
}
