package com.delegreg.rpgm.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.AudioRessource;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.ImageDocument;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.model.Actor;
import com.delegreg.rpgm.model.Campaign;
import com.delegreg.rpgm.model.Location;
import com.delegreg.rpgm.model.Scenario;
import com.delegreg.rpgm.model.Sequence;
import com.delegreg.rpgm.ui.IImageKeys;
import com.delegreg.rpgm.ui.editors.ActorEditor;
import com.delegreg.rpgm.ui.editors.CampaignEditor;
import com.delegreg.rpgm.ui.editors.EditorInputFactory;
import com.delegreg.rpgm.ui.editors.LocationEditor;
import com.delegreg.rpgm.ui.editors.ScenarioEditor;
import com.delegreg.rpgm.ui.editors.SequenceEditor;
import com.delegreg.rpgm.ui.views.AudioPlayer;
import com.delegreg.rpgm.ui.views.HttpDocView;
import com.delegreg.rpgm.ui.views.ImageDocView;
import com.delegreg.rpgm.ui.views.PDFDocView;

public class EditItemAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.rpgm.editItem"; //$NON-NLS-1$
	private Object selectedItem;

	public EditItemAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText(Messages.EditItemAction_Name);
		setToolTipText(Messages.EditItemAction_Tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, IImageKeys.EDITITEM));
		window.getSelectionService().addSelectionListener(this);
	}

	public static String getID() {
		return ID;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {
		// TODO Auto-generated method stub
		if (incoming instanceof IStructuredSelection) {
			selectedItem = ((IStructuredSelection) incoming).getFirstElement();
			processSelection();
		} else {
			// Other selections, for example containing text or of other kinds.
			setEnabled(false);
		}

	}

	public void setSelection(Object item){
		selectedItem=item;
		processSelection();
	}
	
	public void processSelection(){
		if (selectedItem instanceof Campaign) {
			setEnabled(true);
		} else if (selectedItem instanceof Scenario) {
			setEnabled(true);
		} else if (selectedItem instanceof Sequence) {
			setEnabled(true);
		} else if (selectedItem instanceof Location) {
			setEnabled(true);
		} else if (selectedItem instanceof Actor) {
			setEnabled(true);
		} else if (selectedItem instanceof PDFDocRessource) {
			setEnabled(true);
		} else if (selectedItem instanceof PDFDocument) {
			setEnabled(true);
		} else if (selectedItem instanceof HttpDocRessource) {
			setEnabled(true);
		} else if (selectedItem instanceof HttpDocument) {
			setEnabled(true);
		} else if (selectedItem instanceof AudioRessource) {
			setEnabled(true);
		} else if (selectedItem instanceof AudioDocument) {
			setEnabled(true);
		} else {
			setEnabled(false);
		}		
	}
	
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		window.getSelectionService().removeSelectionListener(this);
	}

	public void run() {
		IWorkbenchPage page = window.getActivePage();
		EditorInputFactory factory = new EditorInputFactory();
		if (selectedItem instanceof Campaign) {
			Campaign campaign = (Campaign) selectedItem;
			IEditorInput input = factory.getEditorInput(campaign);
			try {
				page.openEditor(input, CampaignEditor.ID);
			} catch (PartInitException e) {
				System.out.println("Open editor failed: " + campaign.getName()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof Sequence) {
			// Open an editor on that Sequence.
			Sequence scn = (Sequence) selectedItem;
			try {
				IEditorInput input = factory.getEditorInput(scn);
				page.openEditor(input, SequenceEditor.ID);
			} catch (PartInitException e) {
				System.out.println("Open editor failed: " + scn.getName()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof Scenario) {
			// Open an editor on that scenario.
			Scenario scn = (Scenario) selectedItem;
			try {
				IEditorInput input = factory.getEditorInput(scn);
				page.openEditor(input, ScenarioEditor.ID);
			} catch (PartInitException e) {
				System.out.println("Open editor failed: " + scn.getName()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof Location) {
			// Open an editor on that scenario.
			Location lieu = (Location) selectedItem;
			try {
				IEditorInput input = factory.getEditorInput(lieu);
				page.openEditor(input, LocationEditor.ID);
			} catch (PartInitException e) {
				System.out.println("Open editor failed: " + lieu.getName()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof Actor) {
			// Open an editor on that actor.
			Actor personne = (Actor) selectedItem;
			try {
				IEditorInput input = factory.getEditorInput(personne);
				page.openEditor(input, ActorEditor.ID);
			} catch (PartInitException e) {
				System.out.println("Open editor failed: " + personne.getName()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof ImageDocument) {
			// Open a view on that file.
			ImageDocument doc = (ImageDocument) selectedItem;
			try {
				ImageDocView view = (ImageDocView) page.showView(ImageDocView.ID, doc
						.getName(), IWorkbenchPage.VIEW_ACTIVATE);
				view.openImage(doc);
			} catch (PartInitException e) {
				System.out.println("Open view failed: " + doc.getFileName()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof PDFDocument) {
			// Open a view on that file.
			PDFDocument doc = (PDFDocument) selectedItem;
			try {
				PDFDocView view = (PDFDocView) page.showView(PDFDocView.ID, doc
						.getName(), IWorkbenchPage.VIEW_ACTIVATE);
				view.openPDF(doc);
			} catch (PartInitException e) {
				System.out.println("Open view failed: " + doc.getFileName()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof PDFDocRessource) {
			// Open a view on that file.
			PDFDocRessource doc = (PDFDocRessource) selectedItem;
			try {

				PDFDocView view = (PDFDocView) page.showView(PDFDocView.ID, doc
						.getDocument().getName(), IWorkbenchPage.VIEW_ACTIVATE);
				view.openPDF(doc);
				view.gotoPage(doc.getPageNumber());
			} catch (PartInitException e) {
				System.out.println("Open view failed: " + doc.getPdfDocument()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof HttpDocument) {
			// Open a view on that file.
			HttpDocument doc = (HttpDocument) selectedItem;
			try {
				HttpDocView view = (HttpDocView) page.showView(HttpDocView.ID,
						doc.getName(), IWorkbenchPage.VIEW_ACTIVATE);
				view.openURL(doc);
			} catch (PartInitException e) {
				System.out.println("Open view failed: " + doc.getUrl()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof HttpDocRessource) {
			// Open a view on that file.
			HttpDocRessource doc = (HttpDocRessource) selectedItem;
			try {
				HttpDocView view = (HttpDocView) page.showView(HttpDocView.ID,
						((HttpDocument) doc.getDocument()).getName(),
						IWorkbenchPage.VIEW_ACTIVATE);
				view.openURL(doc);
			} catch (PartInitException e) {
				System.out.println("Open view failed: " + doc.getUrl()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof AudioDocument) {
			// Open a view on that file.
			AudioDocument doc = (AudioDocument) selectedItem;
			try {
				AudioPlayer view = (AudioPlayer) page.showView(AudioPlayer.ID,
						doc.getName(), IWorkbenchPage.VIEW_ACTIVATE);
				view.setDocument(doc);
			} catch (PartInitException e) {
				System.out.println("Open view failed: " + doc.getName()); //$NON-NLS-1$
			}
		} else if (selectedItem instanceof AudioRessource) {
			// Open a view on that file.
			AudioRessource doc = (AudioRessource) selectedItem;
			try {
				AudioPlayer view = (AudioPlayer) page.showView(AudioPlayer.ID,
						((AudioDocument) doc.getContainer()).getName(), IWorkbenchPage.VIEW_ACTIVATE);
				view.addRessource(doc);
			} catch (PartInitException e) {
				System.out.println("Open view failed: " + doc.getFileName()); //$NON-NLS-1$
			}
		}
	}

}
