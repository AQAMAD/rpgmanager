package com.delegreg.rpgm.ui.widgets;

import net.jonbuck.texteditor.dialog.TextEditorDialog;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.IDocOrRessource;
import com.delegreg.library.model.ImageDocument;
import com.delegreg.library.model.Library;
import com.delegreg.library.model.PDFDocRessource;
import com.delegreg.library.model.PDFDocument;
import com.delegreg.rpgm.Application;
import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.ui.actions.EditItemAction;

public class RpgmWidgetFactory {

	static public Hyperlink createDocRessourcePopup(FormToolkit toolkit,Form form,final IWorkbenchPartSite site,IDocOrRessource initialRessource,final Runnable runnable){
			Hyperlink hyperlink1 = toolkit.createHyperlink(form.getBody(), Messages.Widget_DocumentRessourceLabel, SWT.NONE);
			hyperlink1.setToolTipText(Messages.Widget_DocumentRessourceTooltip);
			final Hyperlink displaywidget = toolkit.createHyperlink(form.getBody(), "", SWT.NONE); //$NON-NLS-1$
			displaywidget.setToolTipText("Click here to display your doc ressource.");

			hyperlink1.addHyperlinkListener(new IHyperlinkListener() {
			public void linkActivated(HyperlinkEvent e) {
				//popup ElementTreeSelectionDialog and process results
				ElementTreeSelectionDialog dialog=new ElementTreeSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
				dialog.setInput(Application.getCurrentCampaigns().getReferences());
				dialog.addFilter(new ViewerFilter(){
					@Override
					public boolean select(Viewer viewer, Object parentElement,
							Object element) {
						return element instanceof Library || 
						element instanceof PDFDocument || element instanceof PDFDocRessource ||
						element instanceof HttpDocument || element instanceof HttpDocRessource ;
					}});
				dialog.setTitle(Messages.Widget_SelectDocRessource);
				dialog.setDoubleClickSelects(true);
				dialog.setMessage(Messages.Widget_SelectDocRessourceMessage);
				dialog.open();
				//run the runnable to update ressources
				IDocOrRessource	selectedRessource=(IDocOrRessource)dialog.getFirstResult();
				displaywidget.setData(selectedRessource);
				if (selectedRessource!=null){
					displaywidget.setText(selectedRessource.getName());
				}else{
					displaywidget.setText(Messages.Widget_Empty);
				}
				displaywidget.pack(true);
				runnable.run();
				//firePropertyChange(ActorEditor.PROP_DIRTY);
			}
			public void linkEntered(HyperlinkEvent e) {
			}
			public void linkExited(HyperlinkEvent e) {
			}
		});
		
		hyperlink1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		displaywidget.addHyperlinkListener(new IHyperlinkListener() {
			public void linkActivated(HyperlinkEvent e) {
				EditItemAction action=new EditItemAction(site.getWorkbenchWindow());
				action.setSelection(displaywidget.getData());
				if (action.isEnabled()) {
					// Open the corresponding item
					action.run();
				} 
			}
			public void linkEntered(HyperlinkEvent e) {
			}
			public void linkExited(HyperlinkEvent e) {
			}
		});

		displaywidget.setData(initialRessource);
		if (initialRessource!=null){
			displaywidget.setText(initialRessource.getName());
		}else{
			displaywidget.setText(Messages.Widget_Empty);
		}
		displaywidget.pack(true);
		
		return displaywidget;
	}
	
	static public Browser createDescriptionPopup(FormToolkit toolkit,final Composite client,String initialText,final Runnable runnable){
		return createBrowserPopup(toolkit,client,Messages.Widget_DescriptionLabel,"Click here to edit the description for your item.",initialText,runnable);
	}
	
	static public Browser createBrowserPopup(FormToolkit toolkit,final Composite client,String name,String tooltip,String initialText,final Runnable runnable){
		Hyperlink hyperlink2 =toolkit.createHyperlink(client, name, SWT.NONE);
		hyperlink2.setToolTipText(tooltip);
		final Browser displaywidget = new Browser(client, SWT.NONE);
		hyperlink2.addHyperlinkListener(new IHyperlinkListener() {
			public void linkActivated(HyperlinkEvent e) {
				String text=displaywidget.getText();
				if (text==null){text="";} //$NON-NLS-1$
				TextEditorDialog textEditor = new TextEditorDialog(client.getShell());
				textEditor.setContent(text);
				if (textEditor.open() == Window.OK) {
				    // Get the content back from the editor and do something with it...
					displaywidget.setText(textEditor.getContent());
					Display.getCurrent().asyncExec(runnable);
				}
			}
			public void linkEntered(HyperlinkEvent e) {
			}
			public void linkExited(HyperlinkEvent e) {
			}
		});
		hyperlink2.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		displaywidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		displaywidget.setText(initialText);
		return displaywidget;
	}

	static public MapImageCanvas createMapRessourcePopup(FormToolkit toolkit,Form form,final IWorkbenchPartSite site,ImageDocument initialRessource,final Runnable runnable){
		Hyperlink hyperlink1 = toolkit.createHyperlink(form.getBody(), Messages.Widget_MapRessource, SWT.NONE);
		hyperlink1.setToolTipText("Click here to select a map for this location.");
		final MapImageCanvas displaywidget=new MapImageCanvas(form.getBody());
		displaywidget.setBackgroundMode(SWT.INHERIT_FORCE);
		hyperlink1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		hyperlink1.addHyperlinkListener(new IHyperlinkListener() {
		public void linkActivated(HyperlinkEvent e) {
			//popup ElementTreeSelectionDialog and process results
			ElementTreeSelectionDialog dialog=new ElementTreeSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
			dialog.setInput(Application.getCurrentCampaigns().getReferences());
			dialog.setTitle(Messages.Widget_SelectDocRessource);
			dialog.addFilter(new ViewerFilter(){
				@Override
				public boolean select(Viewer viewer, Object parentElement,
						Object element) {
					return element instanceof Library || element instanceof ImageDocument ;
				}});
			dialog.setValidator(new ISelectionStatusValidator(){
				private IStatus fgErrorStatus = new Status(IStatus.ERROR,Application.PLUGIN_ID, ""); //$NON-NLS-1$
				private IStatus fgOKStatus = new Status(IStatus.OK,Application.PLUGIN_ID,""); //$NON-NLS-1$
				@Override
				public IStatus validate(Object[] selection) {
					for (int i = 0; i < selection.length; i++) {
						if (!(selection[i] instanceof ImageDocument)){
							return fgErrorStatus;
						}
					}
					return fgOKStatus;
				}});
			dialog.setDoubleClickSelects(true);
			dialog.setMessage(Messages.Widget_SelectDocRessourceMessage);
			dialog.open();
			//run the runnable to update ressources
			ImageDocument selectedRessource=(ImageDocument)dialog.getFirstResult();
			displaywidget.setData(selectedRessource);
			if (selectedRessource!=null){
				displaywidget.setVisible(true);
				displaywidget.loadImage(selectedRessource.getFileName());
			}else{
				displaywidget.setVisible(false);
			}
			//displaywidget.pack(true);
			runnable.run();
			//firePropertyChange(ActorEditor.PROP_DIRTY);
		}
		public void linkEntered(HyperlinkEvent e) {
		}
		public void linkExited(HyperlinkEvent e) {
		}
	});
	
	displaywidget.setData(initialRessource);
	if (initialRessource!=null){
		displaywidget.setVisible(true);
		displaywidget.loadImage(initialRessource.getFileName());
	}else{
		displaywidget.setVisible(false);
	}
	displaywidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	//displaywidget.pack(true);
	
	return displaywidget;
}

}
