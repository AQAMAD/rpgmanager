package com.delegreg.rpgm.ui;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.ScaleFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.delegreg.rpgm.Messages;
import com.delegreg.rpgm.core.RpgmPreferenceStore;

public class GeneralPreferencePage extends FieldEditorPreferencePage implements
IWorkbenchPreferencePage {

	public GeneralPreferencePage() {
		// TODO Auto-generated constructor stub
		super(Messages.GeneralPreferencePage_PageName,GRID);
		setDescription(Messages.GeneralPreferencePage_PageDescription);
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
				RpgmPreferenceStore.LIBRARY_DIRECTORY,
				Messages.GeneralPreferencePage_LibraryDirectory,
				getFieldEditorParent()
		);
		addField(dirEditor);

		addField(new SpacerFieldEditor(getFieldEditorParent(), V_GAP));

		DirectoryFieldEditor cmpEditor = new DirectoryFieldEditor(
				RpgmPreferenceStore.CAMPAIGNS_DIRECTORY,
				"Campaigns file directory",
				getFieldEditorParent()
		);
		addField(cmpEditor);

		BooleanFieldEditor saveLastCampaignsDirectoryEditor = new BooleanFieldEditor(
				RpgmPreferenceStore.SAVE_CAMPAIGNS_DIRECTORY,
				"Update Campaigns file directory on Save",
				getFieldEditorParent()
		);
		addField(saveLastCampaignsDirectoryEditor);

		addField(new SpacerFieldEditor(getFieldEditorParent(), V_GAP));

		StringFieldEditor gmNameEditor = new StringFieldEditor(
				RpgmPreferenceStore.DEFAULT_GM_NAME,
				Messages.GeneralPreferencePage_DefaultGMName,
				getFieldEditorParent()
		);
		addField(gmNameEditor);

		addField(new SpacerFieldEditor(getFieldEditorParent(), V_GAP));

		BooleanFieldEditor internalBrowsingEditor = new BooleanFieldEditor(
				RpgmPreferenceStore.ALLOW_INTERNAL_BROWSING,
				Messages.GeneralPreferencePage_AllowInternalBrowsing,
				getFieldEditorParent()
		);
		addField(internalBrowsingEditor);

		addField(new SpacerFieldEditor(getFieldEditorParent(), V_GAP));

		BooleanFieldEditor loadLastSavedCampaignsEditor = new BooleanFieldEditor(
				RpgmPreferenceStore.LOAD_LAST_SAVED_CAMPAIGNS,
				Messages.GeneralPreferencePage_LoadLastSaved,
				getFieldEditorParent()
		);
		addField(loadLastSavedCampaignsEditor);

		FileFieldEditor lastSavedEditor = new FileFieldEditor(
				RpgmPreferenceStore.LAST_SAVED_CAMPAIGNS,
				Messages.GeneralPreferencePage_LastSavedFile,
				getFieldEditorParent()
		);
		addField(lastSavedEditor);

		addField(new SpacerFieldEditor(getFieldEditorParent(), V_GAP));

	 	ScaleFieldEditor hiresPdfEditor = new ScaleFieldEditor(
				RpgmPreferenceStore.HIRES_PDF,
				Messages.GeneralPreferencePage_HiresPDF,
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
