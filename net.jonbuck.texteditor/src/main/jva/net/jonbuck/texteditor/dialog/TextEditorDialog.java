/*******************************************************************************
 * Copyright(C) 2008 JonBuck.net.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors: Jon Buck - initial API and implementation
 ******************************************************************************/
package net.jonbuck.texteditor.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import net.jonbuck.texteditor.Activator;
import net.jonbuck.texteditor.nl.TextEditorMessages;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


/**
 * 
 * 
 * @since 1.0.0
 */
public class TextEditorDialog extends Dialog {

    /** The editor content. */
    private String content;

    /** The identifier marks the text to pull from the status bar. */
    private static final String identifier = "C#"; //$NON-NLS-1$

    /**
     * @param parentShell
     */
    public TextEditorDialog(Shell parentShell) {
        super(parentShell);
    }

    /**
     * Returns the initial size to use for the shell. The default implementation
     * returns the preferred size of the shell, using
     * <code>Shell.computeSize(SWT.DEFAULT, SWT.DEFAULT, true)</code>.
     * 
     * @return the initial size of the shell
     */
    protected Point getInitialSize() {
        return getShell().computeSize(650, 457, true);
    }

    /**
     * The <code>Dialog</code> implementation of this <code>Window</code> method
     * creates and lays out the top level composite for the dialog, and
     * determines the appropriate horizontal and vertical dialog units based on
     * the font size. It then calls the <code>createDialogArea</code> and
     * <code>createButtonBar</code> methods to create the dialog area and button
     * bar, respectively. Overriding <code>createDialogArea</code> and
     * <code>createButtonBar</code> are recommended rather than overriding this
     * method.
     */
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, 0);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        applyDialogFont(composite);
        initializeDialogUnits(composite);
        dialogArea = createDialogArea(composite);
        return composite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control createDialogArea(Composite parent) {

        getShell().setText(TextEditorMessages.shellTitle);
        
        Composite contents = (Composite) super.createDialogArea(parent);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.verticalSpacing = 0;
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.horizontalSpacing = 0;
        contents.setLayout(gridLayout);

        final Browser browser = new Browser(contents, SWT.NONE);
        browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path("/html/editor.html"),
                null);

        StringBuilder editorContent = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                editorContent.append(inputLine);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            browser.setUrl(FileLocator.resolve(url).toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add text to control if text is being edited
        if (StringUtils.isNotEmpty(this.content)) {
            browser.addProgressListener(new ProgressListener() {

                public void changed(ProgressEvent event) {}

                public void completed(ProgressEvent event) {
                    String htmlcontent = StringUtils.replace(content, "\"", "'") ;
                    htmlcontent=StringUtils.replace(htmlcontent, "\r", "") ;
                    htmlcontent=StringUtils.replace(htmlcontent, "\n", "") ;
                    String method = "document.forms[0].content.value=\"" + htmlcontent + "\"";
                    if(!browser.execute(method)) {
                        System.out.println("Script was not executed");
                    }
                }
            });
        }

        browser.addStatusTextListener(new StatusTextListener() {
            public void changed(StatusTextEvent event) {
                if (event.text.startsWith(identifier)) {
                    content = StringUtils.removeStart(event.text, identifier);
                    content = content.replaceAll("[\\n\\r]", "");
                    browser.removeStatusTextListener(this);
                    setReturnCode(OK);
                    close();
                }
            }
        });

        return contents;
    }

    /**
     * @return the text
     */
    public String getContent() {
        return content;
    }

    /**
     * @param text the text to set
     */
    public void setContent(String Content) {
        this.content = Content;
    }

}
