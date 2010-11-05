package com.delegreg.library.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.library.Activator;
import com.delegreg.library.Messages;
import com.delegreg.library.model.AudioDocument;
import com.delegreg.library.model.AudioRessource;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.ImageDocument;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;
import com.delegreg.library.ui.IImageKeys;
import com.delegreg.library.ui.views.AudioPlayer;
import com.delegreg.library.ui.views.HttpDocView;
import com.delegreg.library.ui.views.ImageDocView;
import com.delegreg.library.ui.views.PDFDocView;

public class EditItemAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	public final static String ID = "com.delegreg.library.editItem"; //$NON-NLS-1$
	private Object selectedItem;

	public EditItemAction(IWorkbenchWindow window) {
		this.window = window;
		setId(ID);
		setActionDefinitionId(ID);
		setText(Messages.EditItemAction_Name);
		setToolTipText(Messages.EditItemAction_Tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, IImageKeys.EDITITEM));
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
		if (selectedItem instanceof PDFDocRessource) {
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
		if (selectedItem instanceof ImageDocument) {
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
				PDFDocView view ;
				IViewPart p;
				p=page.showView(PDFDocView.ID, doc.getName(), IWorkbenchPage.VIEW_ACTIVATE);
				view = (PDFDocView) p;
				view.openPDF(doc);
			} catch (PartInitException e) {
				System.out.println("Open view failed: " + doc.getFileName()); //$NON-NLS-1$
			} catch (Exception e) {
				e.printStackTrace(); //$NON-NLS-1$
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
