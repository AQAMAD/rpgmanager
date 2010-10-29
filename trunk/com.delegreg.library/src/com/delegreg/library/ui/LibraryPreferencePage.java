package com.delegreg.library.ui;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.ScaleFieldEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.delegreg.library.util.LibraryPreferenceStore;

public class LibraryPreferencePage extends FieldEditorPreferencePage implements
IWorkbenchPreferencePage {

	public LibraryPreferencePage() {
		// TODO Auto-generated constructor stub
		super("Library preferences",GRID);
		setDescription("These are the preferences used during the library use.");
	}


	private class SpacerFieldEditor extends FieldEditor {
		private Label label;
		private int vGap;
		
		public SpacerFieldEditor(Composite parent, int pVGap) {
			this.vGap = pVGap;
			if (vGap < 0) { this.vGap = 0; }
			createControl(parent);
		}
		protected void adjustForNumColumns(int numColumns) {
			((GridData) label.getLayoutData()).horizontalSpan = numColumns;
		}
		public int getNumberOfControls() {return 1;}
		

		@Override
		protected void doLoad() {}

		@Override
		protected void doLoadDefault() {}

		@Override
		protected void doStore() {}

		@Override
		protected void doFillIntoGrid(Composite parent,
				int numColumns) {
			label = getLabelControl(parent);
			GridData gridData = new GridData();
			gridData.heightHint = vGap;
			label.setLayoutData(gridData);
		}

	} 	
	
	@Override
	protected void createFieldEditors() {
		int V_GAP=10;

		addField(new SpacerFieldEditor(getFieldEditorParent(), V_GAP));
		
		DirectoryFieldEditor dirEditor = new DirectoryFieldEditor(
				LibraryPreferenceStore.LIBRARY_DIRECTORY,
				"Library base directory",
				getFieldEditorParent()
		);
		addField(dirEditor);

		addField(new SpacerFieldEditor(getFieldEditorParent(), V_GAP));


		BooleanFieldEditor internalBrowsingEditor = new BooleanFieldEditor(
				LibraryPreferenceStore.ALLOW_INTERNAL_BROWSING,
				"Allow internal web browsing (open URLs in internal browser)",
				getFieldEditorParent()
		);
		addField(internalBrowsingEditor);

	 	ScaleFieldEditor hiresPdfEditor = new ScaleFieldEditor(
	 			LibraryPreferenceStore.HIRES_PDF,
				"Define PDF Engine precision (high definition PDFs)",
				getFieldEditorParent(),
				50,
				250,
				5,
				15
		);
		addField(hiresPdfEditor);
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}


}
