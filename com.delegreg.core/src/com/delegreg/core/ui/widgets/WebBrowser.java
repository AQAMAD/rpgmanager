package com.delegreg.core.ui.widgets;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.delegreg.core.Activator;
import com.delegreg.core.ui.IImageKeys;

public class WebBrowser extends Composite {
	private IToolBarManager toolbar; 
	private IMenuManager menu;
	private Action actionHome;
	private Action actionRefresh;
	private Action actionStop;
	private Action actionForward;
	private Action actionBackward;
	private String originURL;
	private Browser browser;
	
	public String getOriginURL() {
		return originURL;
	}

	public void setOriginURL(String originURL) {
		this.originURL = originURL;
	}

	
	
	@Override
	public int getBorderWidth() {
		//return super.getBorderWidth();
		return 1;
	}

	public WebBrowser(Composite parent, int style) {
		super(parent,style);
		GridLayout gl=new GridLayout(1, false);
		gl.marginHeight=0;
		gl.marginWidth=0;
		setLayout(gl);
		browser=new Browser(this, style);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		browser.addLocationListener(new LocationListener() {
			public void changing(LocationEvent event) {
				// Displays the new location in the text field.
				// textLocation.setText(event.location);
			}

			public void changed(LocationEvent event) {
				// Update tool bar items.
				actionBackward.setEnabled(browser.isBackEnabled());
				actionForward.setEnabled(browser.isForwardEnabled());
				// manager.update(false);
			}
		});
		browser.addProgressListener(new ProgressListener() {
	        public void changed(ProgressEvent event) {
	        	actionStop.setEnabled(true);
	        }

	        public void completed(ProgressEvent event) {
	        	actionStop.setEnabled(false);
	        }
	      });

		}

		public void contribute(IViewSite frm){
			IActionBars bars = frm.getActionBars();
			toolbar=bars.getToolBarManager();
			menu=bars.getMenuManager();
			makeActions();
			hookContextMenu();
			contributeToActionBars();
		}
		public void contribute(ScrolledForm frm){
			toolbar=frm.getToolBarManager();
			makeActions();
			hookContextMenu();
			contributeToActionBars();
		}
		

		private void makeActions() {
			actionBackward = new Action("Back", AbstractUIPlugin
					.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
							IImageKeys.BACKWARD)) {
				public void run() {
					browser.back();
				}
			};
			actionBackward.setEnabled(false); // action is disabled at start up.

			actionForward = new Action("Forward", AbstractUIPlugin
					.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
							IImageKeys.FORWARD)) {
				public void run() {
					browser.forward();
				}
			};
			actionForward.setEnabled(false); // action is disabled at start up.

			actionStop = new Action("Stop", AbstractUIPlugin
					.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
							IImageKeys.STOP)) {
				public void run() {
					browser.stop();
				}
			};

			actionRefresh = new Action("Refresh", AbstractUIPlugin
					.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
							IImageKeys.REFRESH)) {
				public void run() {
					browser.refresh();
				}
			};

			actionHome = new Action("Home", AbstractUIPlugin
					.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
							IImageKeys.HOME)) {
				public void run() {
					browser.setUrl(originURL);
				}
			};

		}



		private void contributeToActionBars() {
			fillLocalPullDown(menu);
			fillLocalToolBar(toolbar);
		}
		
		private void fillLocalPullDown(IContributionManager manager) {
			fillMenu( manager,true);
		}
		
		private void fillLocalToolBar(IToolBarManager manager) {
			fillMenu(manager,false);
		}

		private void fillContextMenu(IMenuManager manager) {
			fillMenu(manager,true);
			// Other plug-ins can contribute there actions here
			manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		}	

		private void fillMenu(IContributionManager manager,boolean noCombos) {
			if (manager==null){return;}
			if (manager.getItems().length>0){
				manager.add(new Separator());
			}

			manager.add(actionBackward);
			manager.add(actionForward);
			manager.add(actionHome);
			manager.add(actionRefresh);
			manager.add(actionStop);
			manager.update(true);
		}		

		private void hookContextMenu() {
			MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager manager) {
					fillContextMenu(manager);
				}
			});
			Menu menu = menuMgr.createContextMenu(this);
			this.setMenu(menu);
			//getSite().registerContextMenu(menuMgr, );
		}	
		/**
		 * Dispose the garbage here
		 */
		public void dispose() {
		}

		public String getUrl() {
			return browser.getUrl();
		}

		public String getText() {
			return browser.getText();
		}

		public void setUrl(String currentURL) {
			browser.setUrl(currentURL);
		}
}
