package com.delegreg.rpgm.ui.dialogs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;

import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.core.RpgmPreferenceStore;

/**
 * Dialog that lets/forces a user to enter/select a workspace that will be used when saving all configuration files and
 * settings. This dialog is shown at startup of the GUI just after the splash screen has shown.
 * 
 * @author Emil Crumhorn
 */
public class PickWorkspaceDialog extends TitleAreaDialog {

    // the name of the file that tells us that the workspace directory belongs to our application
    public static final String  WS_IDENTIFIER          = ".rpgm_workspace";

    // this are our preferences 
    private static PreferenceStore  _preferences           = RpgmPreferenceStore.getInstance();
    //which we need to load
    static {
    	try {
			_preferences.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // various dialog messages
    private static final String _StrMsg                = "Your workspace is where settings and various important files will be stored.";
    private static final String _StrInfo               = "Please select a directory that will be the workspace root";
    private static final String _StrError              = "You must set a directory";

    // our controls
    private Combo               _workspacePathCombo;
    private List<String>        _lastUsedWorkspaces;
    private Button              _RememberWorkspaceButton;

    // used as separator when we save the last used workspace locations
    private static final String _SplitChar             = "#";
    // max number of entries in the history box
    private static final int    _MaxHistory            = 20;

    private boolean             _switchWorkspace;

    // whatever the user picks ends up on this variable
    private String              _selectedWorkspaceRootLocation;

    /**
     * Creates a new workspace dialog with a specific image as title-area image.
     * 
     * @param switchWorkspace true if we're using this dialog as a switch workspace dialog
     * @param wizardImage Image to show
     */
    public PickWorkspaceDialog(boolean switchWorkspace, Image wizardImage) {
        super(Display.getDefault().getActiveShell());
        this._switchWorkspace = switchWorkspace;
        if (wizardImage != null) {
            setTitleImage(wizardImage);
        }
    }

    @Override
    protected void configureShell(Shell newShell) {
    	newShell.setMinimumSize(new Point(110, 28));
        super.configureShell(newShell);
        if (_switchWorkspace) {
            newShell.setText("Switch Workspace");
        } else {
            newShell.setText("Workspace Selection");
        }
    }
    
    /**
     * Returns whether the user selected "remember workspace" in the preferences
     * 
     * @return
     */
    public static boolean isRememberWorkspace() {
        return _preferences.getBoolean(RpgmPreferenceStore.REMEMBER_WORKSPACE);
    }
    
    /**
     * Returns the last set workspace directory from the preferences
     * 
     * @return null if none
     */
    public static String getLastSetWorkspaceDirectory() {
        return _preferences.getString(RpgmPreferenceStore.WORKSPACE_ROOT_DIRECTORY);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        setTitle("Pick Workspace");
        setMessage(_StrMsg);

        try {
            Composite outer = new Composite(parent, SWT.NONE);
            outer.setLayout(null);
            Composite inner = new Composite(outer, SWT.NONE);
            inner.setBounds(5, 5, 430, 145);
            inner.setLayout(null);

            // label on left
            CLabel label = new CLabel(inner, SWT.NONE);
            label.setBounds(5, 5, 119, 21);
            label.setText("Workspace Root Path");

            // combo in middle
            _workspacePathCombo = new Combo(inner, SWT.BORDER);
            _workspacePathCombo.setBounds(5, 31, 336, 23);
            String wsRoot = _preferences.getString(RpgmPreferenceStore.WORKSPACE_ROOT_DIRECTORY);
            if (wsRoot == null || wsRoot.length() == 0) {
                wsRoot = getWorkspacePathSuggestion();
            }
            _workspacePathCombo.setText(wsRoot == null ? "" : wsRoot);

            // checkbox below
            _RememberWorkspaceButton = new Button(inner, SWT.CHECK);
            _RememberWorkspaceButton.setBounds(5, 59, 138, 16);
            _RememberWorkspaceButton.setText("Remember workspace");
            _RememberWorkspaceButton.setSelection(_preferences.getBoolean(RpgmPreferenceStore.REMEMBER_WORKSPACE));

            String lastUsed = _preferences.getString(RpgmPreferenceStore.WORKSPACES_MRU);
            _lastUsedWorkspaces = new ArrayList<String>();
            if (lastUsed != null) {
                String[] all = lastUsed.split(_SplitChar);
                for (String str : all)
                    _lastUsedWorkspaces.add(str);
            }
            for (String last : _lastUsedWorkspaces)
                _workspacePathCombo.add(last);

            // browse button on right
            Button browse = new Button(inner, SWT.PUSH);
            browse.setBounds(347, 29, 59, 25);
            browse.setText("Browse...");
            browse.addListener(SWT.Selection, new Listener() {

                @Override
                public void handleEvent(Event event) {
                    DirectoryDialog dd = new DirectoryDialog(getParentShell());
                    dd.setText("Select Workspace Root");
                    dd.setMessage(_StrInfo);
                    dd.setFilterPath(_workspacePathCombo.getText());
                    String pick = dd.open();
                    if (pick == null && _workspacePathCombo.getText().length() == 0) {
                        setMessage(_StrError, IMessageProvider.ERROR);
                    } else {
                        setMessage(_StrMsg);
                        _workspacePathCombo.setText(pick);
                    }
                }

            });

            return inner;
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    /**
     * Returns whatever path the user selected in the dialog.
     * 
     * @return Path
     */
    public String getSelectedWorkspaceLocation() {
        return _selectedWorkspaceRootLocation;
    }

    // suggests a path based on the user.home/temp directory location
    private String getWorkspacePathSuggestion() {
        StringBuffer buf = new StringBuffer();

        String uHome = System.getProperty("user.home");
        if (uHome == null) {
            uHome = "c:" + File.separator + "temp";
        }

        buf.append(uHome);
        buf.append(File.separator);
        buf.append("New RPGM Workspace");

        return buf.toString();
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {

        // clone workspace needs a lot of checks
        Button clone = createButton(parent, IDialogConstants.IGNORE_ID, "Clone", false);
        clone.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                try {
                    String txt = _workspacePathCombo.getText();
                    File workspaceDirectory = new File(txt);
                    if (!workspaceDirectory.exists()) {
                        MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
                                "The currently entered workspace path does not exist. Please enter a valid path.");
                        return;
                    }

                    if (!workspaceDirectory.canRead()) {
                        MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
                                "The currently entered workspace path is not readable. Please check file system permissions.");
                        return;
                    }

                    // check for workspace file (empty indicator that it's a workspace)
                    File wsFile = new File(txt + File.separator + WS_IDENTIFIER);
                    if (!wsFile.exists()) {
                        MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
                                "The currently entered workspace path does not contain a valid workspace.");
                        return;
                    }

                    DirectoryDialog dd = new DirectoryDialog(Display.getDefault().getActiveShell());
                    dd.setFilterPath(txt);
                    String directory = dd.open();
                    if (directory == null) { return; }

                    File targetDirectory = new File(directory);
                    if (targetDirectory.getAbsolutePath().equals(workspaceDirectory.getAbsolutePath())) {
                        MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Source and target workspaces are the same");
                        return;
                    }

                    // recursive check, if new directory is a subdirectory of our workspace, that's a big no-no or we'll
                    // create directories forever
                    if (isTargetSubdirOfDir(workspaceDirectory, targetDirectory)) {
                        MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Target folder is a subdirectory of the current workspace");
                        return;
                    }

                    try {
                        copyFiles(workspaceDirectory, targetDirectory);
                    } catch (Exception err) {
                        MessageDialog
                                .openError(Display.getDefault().getActiveShell(), "Error", "There was an error cloning the workspace: " + err.getMessage());
                        return;
                    }

                    boolean setActive = MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "Workspace Cloned",
                            "Would you like to set the newly cloned workspace to be the active one?");
                    if (setActive) {
                        _workspacePathCombo.setText(directory);
                    }
                } catch (Exception err) {
                    MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "There was an internal error, please check the logs");
                    err.printStackTrace();
                }
            }
        });
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    }

    // checks whether a target directory is a subdirectory of ourselves
    private boolean isTargetSubdirOfDir(File source, File target) {
        List<File> subdirs = new ArrayList<File>();
        getAllSubdirectoriesOf(source, subdirs);
        return subdirs.contains(target);
    }

    // helper for above
    private void getAllSubdirectoriesOf(File target, List<File> buffer) {
        File[] files = target.listFiles();
        if (files == null || files.length == 0) return;

        for (File f : files) {
            if (f.isDirectory()) {
                buffer.add(f);
                getAllSubdirectoriesOf(f, buffer);
            }
        }
    }

    /**
     * This function will copy files or directories from one location to another. note that the source and the
     * destination must be mutually exclusive. This function can not be used to copy a directory to a sub directory of
     * itself. The function will also have problems if the destination files already exist.
     * 
     * @param src -- A File object that represents the source for the copy
     * @param dest -- A File object that represents the destination for the copy.
     * @throws IOException if unable to copy.
     */
    public static void copyFiles(File src, File dest) throws IOException {
        // Check to ensure that the source is valid...
        if (!src.exists()) {
            throw new IOException("Can not find source: " + src.getAbsolutePath());
        } else if (!src.canRead()) { // check to ensure we have rights to the source...
            throw new IOException("Cannot read: " + src.getAbsolutePath() + ". Check file permissions.");
        }
        // is this a directory copy?
        if (src.isDirectory()) {
            if (!dest.exists()) { // does the destination already exist?
                // if not we need to make it exist if possible (note this is mkdirs not mkdir)
                if (!dest.mkdirs()) { throw new IOException("Could not create direcotry: " + dest.getAbsolutePath()); }
            }
            // get a listing of files...
            String list[] = src.list();
            // copy all the files in the list.
            for (int i = 0; i < list.length; i++) {
                File dest1 = new File(dest, list[i]);
                File src1 = new File(src, list[i]);
                copyFiles(src1, dest1);
            }
        } else {
            // This was not a directory, so lets just copy the file
            FileInputStream fin = null;
            FileOutputStream fout = null;
            byte[] buffer = new byte[4096]; // Buffer 4K at a time (you can change this).
            int bytesRead;
            try {
                // open the files for input and output
                fin = new FileInputStream(src);
                fout = new FileOutputStream(dest);
                // while bytesRead indicates a successful read, lets write...
                while ((bytesRead = fin.read(buffer)) >= 0) {
                    fout.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) { // Error copying file...
                IOException wrapper = new IOException("Unable to copy file: " + src.getAbsolutePath() + "to" + dest.getAbsolutePath());
                wrapper.initCause(e);
                wrapper.setStackTrace(e.getStackTrace());
                throw wrapper;
            } finally { // Ensure that the files are closed (if they were open).
                if (fin != null) {
                    fin.close();
                }
                if (fout != null) {
                    fin.close();
                }
            }
        }
    }

    @Override
    protected void okPressed() {
        String str = _workspacePathCombo.getText();

        if (str.length() == 0) {
            setMessage(_StrError, IMessageProvider.ERROR);
            return;
        }

        String ret = checkWorkspaceDirectory(getParentShell(), str, true, true);
        if (ret != null) {
            setMessage(ret, IMessageProvider.ERROR);
            return;
        }

        // save it so we can show it in combo later
        _lastUsedWorkspaces.remove(str);

        if (!_lastUsedWorkspaces.contains(str)) {
            _lastUsedWorkspaces.add(0, str);
        }

        // deal with the max history
        if (_lastUsedWorkspaces.size() > _MaxHistory) {
            List<String> remove = new ArrayList<String>();
            for (int i = _MaxHistory; i < _lastUsedWorkspaces.size(); i++) {
                remove.add(_lastUsedWorkspaces.get(i));
            }

            _lastUsedWorkspaces.removeAll(remove);
        }

        // create a string concatenation of all our last used workspaces
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < _lastUsedWorkspaces.size(); i++) {
            buf.append(_lastUsedWorkspaces.get(i));
            if (i != _lastUsedWorkspaces.size() - 1) {
                buf.append(_SplitChar);
            }
        }

        // save them onto our preferences
        _preferences.setValue(RpgmPreferenceStore.REMEMBER_WORKSPACE, _RememberWorkspaceButton.getSelection());
        _preferences.setValue(RpgmPreferenceStore.WORKSPACES_MRU, buf.toString());

        // now create it 
        boolean ok = checkAndCreateWorkspaceRoot(str);
        if (!ok) {
            setMessage("The workspace could not be created, please check the error log");
            return;
        }

        // here we set the location so that we can later fetch it again        
        _selectedWorkspaceRootLocation = str;

        // and on our preferences as well
        _preferences.setValue(RpgmPreferenceStore.WORKSPACE_ROOT_DIRECTORY,str);
        try {
			_preferences.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        super.okPressed();
    }

    /**
     * Ensures a workspace directory is OK in regards of reading/writing, etc. This method will get called externally as well.
     * 
     * @param parentShell Shell parent shell
     * @param workspaceLocation Directory the user wants to use
     * @param askCreate Whether to ask if to create the workspace or not in this location if it does not exist already
     * @param fromDialog Whether this method was called from our dialog or from somewhere else just to check a location
     * @return null if everything is ok, or an error message if not
     */
    public static String checkWorkspaceDirectory(Shell parentShell, String workspaceLocation, boolean askCreate, boolean fromDialog) {
        File f = new File(workspaceLocation);
        if (!f.exists()) {
            if (askCreate) {
                boolean create = MessageDialog.openConfirm(parentShell, "New Directory", "The directory does not exist. Would you like to create it?");
                if (create) {
                    try {
                        f.mkdirs();
                        File wsDot = new File(workspaceLocation + File.separator + WS_IDENTIFIER);
                        wsDot.createNewFile();
                    } catch (Exception err) {
                        return "Error creating directories, please check folder permissions";
                    }
                }

                if (!f.exists()) { return "The selected directory does not exist"; }
            }
        }

        if (!f.canRead()) { return "The selected directory is not readable"; }

        if (!f.isDirectory()) { return "The selected path is not a directory"; }

        File wsTest = new File(workspaceLocation + File.separator + WS_IDENTIFIER);
        if (fromDialog) {
            if (!wsTest.exists()) {
                boolean create = MessageDialog
                        .openConfirm(
                                parentShell,
                                "New Workspace",
                                "The directory '"
                                        + wsTest.getAbsolutePath()
                                        + "' is not set to be a workspace. Do note that files will be created directly under the specified directory and it is suggested you create a directory that has a name that represents your workspace. \n\nWould you like to create a workspace in the selected location?");
                if (create) {
                    try {
                        f.mkdirs();
                        File wsDot = new File(workspaceLocation + File.separator + WS_IDENTIFIER);
                        wsDot.createNewFile();
                    } catch (Exception err) {
                        return "Error creating directories, please check folder permissions";
                    }
                } else {
                    return "Please select a directory for your workspace";
                }

                if (!wsTest.exists()) { return "The selected directory does not exist"; }

                return null;
            }
        } else {
            if (!wsTest.exists()) { return "The selected directory is not a workspace directory"; }
        }

        return null;
    }

    /**
     * Checks to see if a workspace exists at a given directory string, and if not, creates it. Also puts our
     * identifying file inside that workspace.
     * 
     * @param wsRoot Workspace root directory as string
     * @return true if all checks and creations succeeded, false if there was a problem
     */
    public static boolean checkAndCreateWorkspaceRoot(String wsRoot) {
        try {
            File fRoot = new File(wsRoot);
            if (!fRoot.exists()) return false;

            File dotFile = new File(wsRoot + File.separator + PickWorkspaceDialog.WS_IDENTIFIER);
            if (!dotFile.exists() && !dotFile.createNewFile()) return false;

            return true;
        } catch (Exception err) {
            // as it might need to go to some other error log too
            err.printStackTrace();
            return false;
        }
    }

}
