package com.delegreg.rpgm;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.delegreg.rpgm.ui.actions.ClearAllAction;
import com.delegreg.rpgm.ui.actions.DeleteItemAction;
import com.delegreg.rpgm.ui.actions.EditItemAction;
import com.delegreg.rpgm.ui.actions.ExportItemAction;
import com.delegreg.rpgm.ui.actions.ImportItemAction;
import com.delegreg.rpgm.ui.actions.NewItemAction;
import com.delegreg.rpgm.ui.actions.OpenAllAction;
import com.delegreg.rpgm.ui.actions.PreferencesAction;
import com.delegreg.rpgm.ui.actions.RenameItemAction;
import com.delegreg.rpgm.ui.actions.SaveAction;
import com.delegreg.rpgm.ui.actions.SaveAsAction;
import com.delegreg.rpgm.ui.actions.SolidifyAction;
import com.delegreg.rpgm.ui.actions.SwitchWorkspaceAction;
import com.delegreg.rpgm.ui.actions.update.AddExtensionsAction;
import com.delegreg.rpgm.ui.actions.update.ManageExtensionsAction;
import com.delegreg.rpgm.ui.actions.update.UpdateAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	//standard eclipse actions
	private IWorkbenchAction clearAction;
	private IWorkbenchAction newAction;
	private IWorkbenchAction deleteAction;
	private IWorkbenchAction importAction;
	private IWorkbenchAction exportAction;
	private IWorkbenchAction solidifyAction;
	private IWorkbenchAction saveAction;
	private IWorkbenchAction saveAsAction;
	private IWorkbenchAction switchWorkspaceAction;
	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction prefsAction;
	private IWorkbenchAction showHelpAction; // NEW 
    private IWorkbenchAction searchHelpAction; // NEW 
    private IWorkbenchAction dynamicHelpAction; // NEW
	private IWorkbenchAction updateAction; // NEW 
    private IWorkbenchAction searchExtensionsAction; // NEW 
    private IWorkbenchAction manageExtensionsAction; // NEW
    
    //campaign management
	private IWorkbenchAction openAllAction;
	//private IWorkbenchAction saveCampaignAction;
	private IWorkbenchAction editAction;
	private IWorkbenchAction renameAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	//standard RCP actions
	    clearAction = new ClearAllAction(window);
	    register(clearAction);
	    newAction = new NewItemAction(window);
	    register(newAction);
	    deleteAction = new DeleteItemAction(window);
	    register(deleteAction);
	    openAllAction = new OpenAllAction(window);
	    register(openAllAction);
	    importAction = new ImportItemAction(window);
	    register(importAction);
	    exportAction = new ExportItemAction(window);
	    register(exportAction);
	    solidifyAction = new SolidifyAction(window);
	    register(solidifyAction);
	    saveAction = new SaveAction(window);
	    register(saveAction);
	    saveAsAction = new SaveAsAction(window);
	    register(saveAsAction);
	    exitAction = ActionFactory.QUIT.create(window);
	    register(exitAction);
	    aboutAction = ActionFactory.ABOUT.create(window);
	    register(aboutAction);
	    prefsAction = new PreferencesAction(window);
	    register(prefsAction);
	    renameAction = new RenameItemAction(window);
	    register(renameAction);
	    //test
	    showHelpAction = ActionFactory.HELP_CONTENTS.create(window); // NEW 
	    register(showHelpAction); // NEW 
	    searchHelpAction = ActionFactory.HELP_SEARCH.create(window); // NEW 
	    register(searchHelpAction); // NEW 
	    dynamicHelpAction = ActionFactory.DYNAMIC_HELP.create(window); // NEW 
	    register(dynamicHelpAction); // NEW 
	    //custom actions
	    //test
	    updateAction = new UpdateAction(window); // NEW 
	    register(updateAction); // NEW 
	    searchExtensionsAction = new AddExtensionsAction(window); // NEW 
	    register(searchExtensionsAction); // NEW 
	    manageExtensionsAction = new ManageExtensionsAction(window); // NEW 
	    register(manageExtensionsAction); // NEW 
	    //openCampaignAction = new OpenAllAction(window);
	    //register(openCampaignAction);
	    editAction = new EditItemAction(window);
	    register(editAction);
	    //openCampaignAction = new SaveCampaignAction(window);
	    //register(saveCampaignAction);
	    switchWorkspaceAction= new SwitchWorkspaceAction(window);
	    register(editAction);
	    
    }

    protected void fillMenuBar(IMenuManager menuBar) {
	    MenuManager rpgmMenu = new MenuManager(
    	        Messages.ApplicationActionBarAdvisor_RPGMMenuName, 
    	        "rpgm"); //$NON-NLS-1$
	    rpgmMenu.add(clearAction);
	    rpgmMenu.add(newAction);
	    rpgmMenu.add(editAction);
	    rpgmMenu.add(renameAction);
	    rpgmMenu.add(deleteAction);
	    rpgmMenu.add(new Separator());
	    rpgmMenu.add(importAction);
	    rpgmMenu.add(exportAction);
	    rpgmMenu.add(solidifyAction);
	    //rpgmMenu.add(openCampaignAction);
	    rpgmMenu.add(new Separator());
	    rpgmMenu.add(openAllAction);
	    rpgmMenu.add(saveAction);
	    rpgmMenu.add(saveAsAction);
	    rpgmMenu.add(new Separator());
	    rpgmMenu.add(prefsAction);
	    rpgmMenu.add(switchWorkspaceAction);
	    rpgmMenu.add(new Separator());
	    rpgmMenu.add(exitAction);
	    //Window menu
	    //MenuManager windowMenu = new MenuManager(Messages.ApplicationActionBarAdvisor_WindowMenuName, 
	    		//"window");//$NON-NLS-1$
	    //MenuManager viewsMenu = new MenuManager(Messages.ApplicationActionBarAdvisor_OpenViewMenuName, 
	    		//"views");//$NON-NLS-1$
	    //windowMenu.add(viewsMenu);
	    //help menu
	    MenuManager helpMenu = new MenuManager(Messages.ApplicationActionBarAdvisor_HelpMenuName, 
	    		"help");//$NON-NLS-1$
	    helpMenu.add(showHelpAction); // NEW 
	    helpMenu.add(searchHelpAction); // NEW 
	    helpMenu.add(dynamicHelpAction); // NEW 	    
	    helpMenu.add(new Separator());
	    helpMenu.add(updateAction);
	    helpMenu.add(searchExtensionsAction);
	    helpMenu.add(manageExtensionsAction);
	    helpMenu.add(new Separator());
	    helpMenu.add(aboutAction);
	    //add all menus
	    //add all menus
	    //add all menus
	    menuBar.add(rpgmMenu);
	    //menuBar.add(windowMenu);
	    menuBar.add(helpMenu);
    }

    protected void fillCoolBar(ICoolBarManager coolBar) {
    	  IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle());
    	  coolBar.add(toolbar);
    	  toolbar.add(clearAction);
    	  toolbar.add(newAction);
    	  toolbar.add(editAction);
    	  toolbar.add(renameAction);
    	  toolbar.add(deleteAction);
    	  toolbar.add(new Separator());
		  toolbar.add(openAllAction);
    	  toolbar.add(saveAction);
		  toolbar.add(saveAsAction);
    	  toolbar.add(new Separator());
    	  toolbar.add(prefsAction);
    	  toolbar.add(switchWorkspaceAction);
    	  toolbar.add(new Separator());
    	  toolbar.add(showHelpAction);
    	  //toolbar.add(aboutAction);
    }    

}
