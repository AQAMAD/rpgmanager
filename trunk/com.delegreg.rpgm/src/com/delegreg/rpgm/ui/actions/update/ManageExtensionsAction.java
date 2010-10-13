package com.delegreg.rpgm.ui.actions.update;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.update.ui.UpdateManagerUI;

import com.delegreg.rpgm.Application;

public class ManageExtensionsAction extends Action implements IWorkbenchAction {
	  private IWorkbenchWindow window;

	  public ManageExtensionsAction(IWorkbenchWindow window) {
	    this.window = window;
	    setId("com.delegreg.com.actions.manageExtensions");
	    setText("&Extensions...");
	    setToolTipText("Manage extensions for RPGM");
	    setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
	        Application.PLUGIN_ID, "icons/usearch_obj.gif"));
	    window.getWorkbench().getHelpSystem().setHelp(this,
	        "com.delegreg.com.actions.update");
	  }
	  public void run() {
		  BusyIndicator.showWhile(window.getShell().getDisplay(),
			      new Runnable() {
			        public void run() {
			          UpdateManagerUI
			              .openConfigurationManager(window.getShell());
			        }
			      });
	  }
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	}