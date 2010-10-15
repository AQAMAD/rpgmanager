/**
 * 
 */
package com.delegreg.rpgm.util;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

/**
 * @author Delegreg
 *
 */
public class DebugTools {
	
	public static void DebugMessage(String message){
        MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Debug", message);
		
	}

}
