package com.delegreg.rpgm;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(400, 300));
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(false);
        configurer.setShowMenuBar(true);
        configurer.setShowProgressIndicator(true);  
        configurer.setShowPerspectiveBar(true);
		// Set the preference toolbar to the left place
		// If other menus exists then this will be on the left of them
		IPreferenceStore apiStore = PlatformUI.getPreferenceStore();
		apiStore.setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR,"TOP_LEFT");        
		apiStore.setValue(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR,false);        
        configurer.setTitle(Application.getTitle());
    }



}
