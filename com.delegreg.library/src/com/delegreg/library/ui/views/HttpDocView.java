package com.delegreg.library.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.BaseContaineableContainer;
import com.delegreg.core.IContaineable;
import com.delegreg.core.ui.widgets.WebBrowser;
import com.delegreg.library.model.HttpDocRessource;
import com.delegreg.library.model.HttpDocument;
import com.delegreg.library.model.IDocRessource;
import com.delegreg.library.Activator;
import com.delegreg.library.Messages;
import com.delegreg.library.util.DocumentIndexer;
import com.delegreg.library.ui.IImageKeys;

public class HttpDocView extends ViewPart {
	private final FormToolkit formToolkit = new FormToolkit(Display
			.getDefault());
	public static final String ID = "com.delegreg.library.views.http"; //$NON-NLS-1$
	private WebBrowser browser;
	private Action actionIndex;
	private HttpDocument originURL;
	private String currentURL;

	public HttpDocView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		{
			Form form = formToolkit.createForm(parent);
			form.getBody().setLayout(new GridLayout(1, false));
			{
				browser = new WebBrowser(form.getBody(), SWT.NONE);
				browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
						true, 1, 1));				
			}
			formToolkit.decorateFormHeading(form);

			makeActions();
			hookContextMenu();
			// hookDoubleClickAction();
			contributeToActionBars();

			browser.contribute(getViewSite());

		}
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IContributionManager manager) {
		fillMenu(manager, true);
	}

	private void fillContextMenu(IMenuManager manager) {
		fillMenu(manager, true);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		fillMenu(manager, false);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(browser);
		browser.setMenu(menu);
		// getSite().registerContextMenu(menuMgr, );
	}

	/*
	 * fill any object with action buttons
	 */
	private void fillMenu(IContributionManager manager, boolean noCombos) {

		manager.add(actionIndex);

		manager.update(true);

	}

	private void makeActions() {
		actionIndex = new Action("Create new web indexes from links in the page", AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
						IImageKeys.INDEX)) {
			public void run() {
				DocumentIndexer indexer=new DocumentIndexer();
				int prevnumindexes=originURL.size();
				indexer.indexHTML((BaseContaineableContainer<IDocRessource>) originURL, browser.getUrl(), browser.getText());
				int newnumindexes=originURL.size();
				if (prevnumindexes<newnumindexes){
					MessageDialog.openInformation(getSite().getShell(), "New indexes found", Integer.toString(newnumindexes-prevnumindexes) + " new indexes created.");
					((IContaineable)originURL).getRoot().fireContentChanged();
					}
				}
		};
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void openURL(HttpDocument doc) {
		originURL = doc;
		browser.setOriginURL(doc.getUrl());
		currentURL=doc.getUrl();
		openCurrentDoc();
	}

	public void openURL(HttpDocRessource doc) {
		originURL = (HttpDocument) doc.getDocument();
		browser.setOriginURL(originURL.getUrl());
		currentURL=doc.getUrl();
		openCurrentDoc();
	}

	private void openCurrentDoc(){
		if (originURL instanceof HttpDocument){
			HttpDocument doc=(HttpDocument)originURL;
			setPartName("Web browser" + " : " + doc.getName());
		}
		browser.setUrl(currentURL);
		browser.setFocus();
	}
	
}
