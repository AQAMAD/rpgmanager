package com.delegreg.rpgm.ui.actions.update;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.update.search.BackLevelFilter;
import org.eclipse.update.search.EnvironmentFilter;
import org.eclipse.update.search.UpdateSearchRequest;
import org.eclipse.update.search.UpdateSearchScope;
import org.eclipse.update.ui.UpdateJob;
import org.eclipse.update.ui.UpdateManagerUI;

import com.delegreg.rpgm.Application;

public class AddExtensionsAction extends Action implements IWorkbenchAction {
	  private IWorkbenchWindow window;


	  public AddExtensionsAction(IWorkbenchWindow window) {
	    this.window = window;
	    setId("com.delegreg.rpgm.addExtensions");
	    setText("&Search for Extensions...");
	    setToolTipText("Search for new extensions for RPGM");
	    setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
	        Application.PLUGIN_ID, "icons/usearch_obj.gif"));
	    window.getWorkbench().getHelpSystem().setHelp(this,
	        "com.delegreg.com.actions.update");
	  }
	  public void run() {
	    BusyIndicator.showWhile(window.getShell().getDisplay(),
	        new Runnable() {
	          public void run() {
	            UpdateJob job = new UpdateJob(
	                "Search for new extensions", getSearchRequest());
	            UpdateManagerUI.openInstaller(window.getShell(), job);
	          }
	        });
	  }

	  private UpdateSearchRequest getSearchRequest() {
	    UpdateSearchRequest result = new UpdateSearchRequest(
	        UpdateSearchRequest.createDefaultSiteSearchCategory(),
	        new UpdateSearchScope());
	    result.addFilter(new BackLevelFilter());
	    result.addFilter(new EnvironmentFilter());
	    UpdateSearchScope scope = new UpdateSearchScope();
	    try {
	      String homeBase = System.getProperty("rpgm.homebase",
	          "http://www.delegreg.com/rpgm/update");
	      URL url = new URL(homeBase);
	      scope.addSearchSite("RPGM site", url, null);
	    } catch (MalformedURLException e) {
	      // skip bad URLs
	    }
	    result.setScope(scope);
	    return result;
	  }
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	}
