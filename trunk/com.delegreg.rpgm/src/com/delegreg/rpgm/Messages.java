package com.delegreg.rpgm;

import java.lang.reflect.Field;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.delegreg.rpgm.messages"; //$NON-NLS-1$
	public static String ApplicationActionBarAdvisor_HelpMenuName;
	public static String ApplicationActionBarAdvisor_RPGMMenuName;
	public static String CampaignEditor_CampaignNameLabel;
	public static String CampaignEditor_GameMasterLabel;
	public static String CampaignEditor_Title;
	public static String Campaigns_Filename;
	public static String CampaignsView_Importing;
	public static String CampaignsView_ProgressImporting;
	public static String ClearAllAction_Cancel;
	public static String ClearAllAction_Confirm;
	public static String ClearAllAction_ConfirmDialogMessage;
	public static String ClearAllAction_ConfirmDialogTitle;
	public static String ClearAllAction_Name;
	public static String ClearAllAction_Tooltip;
	public static String DeleteItemAction_Name;
	public static String DeleteItemAction_ToolTip;
	public static String HttpDocView_Backward;
	public static String HttpDocView_Forward;
	public static String HttpDocView_Home;
	public static String HttpDocView_Index;
	public static String HttpDocView_IndexDialogMessage;
	public static String HttpDocView_IndexDialogTitle;
	public static String HttpDocView_PartName;
	public static String HttpDocView_Refresh;
	public static String HttpDocView_Stop;
	public static String LocationEditor_LocationNameLabel;
	public static String LocationEditor_Title;
	public static String LocationEditorMainPage_Title;
	public static String LocationEditorMapPage_PositionAction;
	public static String LocationEditorMapPage_PositionActionTooltip;
	public static String LocationEditorMapPage_Title;
	public static String EditItemAction_Name;
	public static String NewItemAction_Name;
	public static String NewItemAction_ToolTip;
	public static String NewItemDetailsWizardPage_ChosenItemType;
	public static String NewItemDetailsWizardPage_GameMaster;
	public static String NewItemDetailsWizardPage_Mandatory;
	public static String NewItemDetailsWizardPage_MustExist;
	public static String NewItemDetailsWizardPage_Name;
	public static String NewItemDetailsWizardPage_NameMandatory;
	public static String NewItemDetailsWizardPage_NewActor;
	public static String NewItemDetailsWizardPage_NewCampaign;
	public static String NewItemDetailsWizardPage_NewGroup;
	public static String NewItemDetailsWizardPage_NewLibrary;
	public static String NewItemDetailsWizardPage_NewLocation;
	public static String NewItemDetailsWizardPage_NewPdfDocument;
	public static String NewItemDetailsWizardPage_NewScenario;
	public static String NewItemDetailsWizardPage_NewSequence;
	public static String NewItemDetailsWizardPage_NewWebDocument;
	public static String NewItemDetailsWizardPage_NewWebPage;
	public static String NewItemDetailsWizardPage_PageNumber;
	public static String NewItemDetailsWizardPage_PdfFileName;
	public static String NewItemDetailsWizardPage_URL;
	public static String NewItemDetailsWizardPage_WillBeAddedTo;
	public static String NewItemWizard_Name;
	public static String NewItemWizard_Page1Name;
	public static String NewItemWizard_Page1Text;
	public static String NewItemWizard_Page2Name;
	public static String NewItemWizard_Page2Text;
	public static String OpenCampaignAction_Name;
	public static String OpenCampaignAction_Tooltip;
	public static String PDFDocView_Back10;
	public static String PDFDocView_EnterPassword;
	public static String PDFDocView_FirstPage;
	public static String PDFDocView_Forward10;
	public static String PDFDocView_IndexActionName;
	public static String PDFDocView_IndexActionToltip;
	public static String PDFDocView_IndexDialogError;
	public static String PDFDocView_IndexDialogMessage;
	public static String PDFDocView_IndexDialogTitle;
	public static String PDFDocView_Last;
	public static String PDFDocView_Next;
	public static String PDFDocView_NoValidPassword;
	public static String PDFDocView_Password;
	public static String PDFDocView_Previous;
	public static String PDFDocView_Title;
	public static String PDFDocView_ZoomIn;
	public static String PDFDocView_ZoomOut;
	public static String PreferencesAction_Name;
	public static String PreferencesAction_Tooltip;
	public static String RenameItemAction_DialogMessage;
	public static String RenameItemAction_DialogTitle;
	public static String RenameItemAction_ErrorMessage;
	public static String RenameItemAction_Name;
	public static String RenameItemAction_Tooltip;
	public static String RPGMAdapterFactory_CampaignsName;
	public static String RPGMAdapterFactory_LocationsName;
	public static String SaveAsAction_Cancel;
	public static String SaveAsAction_ErrorMessage;
	public static String SaveAsAction_ErrorTitle;
	public static String SaveAsAction_Name;
	public static String SaveAsAction_Overwrite;
	public static String SaveAsAction_OverwriteDialogMessage;
	public static String SaveAsAction_OverwriteDialogTitle;
	public static String SaveAsAction_ToolTip;
	public static String SaveAction_Name;
	public static String SaveAction_ToolTip;
	public static String ScenarioEditor_ScenarioNameLabel;
	public static String ScenarioEditor_Title;
	public static String SelectItemTypeWizardPage_Actor;
	public static String SelectItemTypeWizardPage_Group;
	public static String SelectItemTypeWizardPage_Campaign;
	public static String SelectItemTypeWizardPage_HttpDocument;
	public static String SelectItemTypeWizardPage_HttpDocRessource;
	public static String SelectItemTypeWizardPage_Library;
	public static String SelectItemTypeWizardPage_Location;
	public static String SelectItemTypeWizardPage_PDFDocument;
	public static String SelectItemTypeWizardPage_PDFDocRessource;
	public static String SelectItemTypeWizardPage_AudioDocument;
	public static String SelectItemTypeWizardPage_AudioRessource;
	public static String SelectItemTypeWizardPage_ImageDocument;
	public static String SelectItemTypeWizardPage_Scenario;
	public static String SelectItemTypeWizardPage_Sequence;
	public static String SelectItemTypeWizardPage_SideQuest;
	public static String SelectItemTypeWizardPage_Text;
	public static String SequenceEditor_SequenceNameLabel;
	public static String SequenceEditor_Title;
	public static String SolidifyAction_ErrorMessage;
	public static String SolidifyAction_ErrorTitle;
	public static String SolidifyAction_Name;
	public static String SolidifyAction_Tooltip;
	public static String SwitchWorkspaceAction_Text;
	public static String SwitchWorkspaceAction_Tooltip;
	public static String SwitchWorkspaceAction_WarningMessage;
	public static String SwitchWorkspaceAction_WarningTitle;
	public static String Widget_SelectDocRessource;
	public static String Widget_SelectDocRessourceMessage;
	public static String Widget_Empty;
	public static String Widget_DocumentRessourceLabel;
	public static String Widget_DescriptionLabel;
	public static String Widget_MapRessource;
	public static String EditItemAction_Tooltip;
	public static String ExportItemAction_Cancel;
	public static String ExportItemAction_DialogMessage;
	public static String ExportItemAction_DialogTitle;
	public static String ExportItemAction_ErrorMessage;
	public static String ExportItemAction_ErrorTitle;
	public static String ExportItemAction_Name;
	public static String ExportItemAction_OverWrite;
	public static String ExportItemAction_Tooltip;
	public static String ActorEditor_ActorNameLabel;
	public static String ActorEditor_Title;
	public static String AudioPlayer_Clear;
	public static String AudioPlayer_ClearTooltip;
	public static String AudioPlayer_CurrentRessource;
	public static String AudioPlayer_Loop;
	public static String AudioPlayer_Looplist;
	public static String AudioPlayer_LooplistTooltip;
	public static String AudioPlayer_LoopTooltip;
	public static String AudioPlayer_Next;
	public static String AudioPlayer_NextTooltip;
	public static String AudioPlayer_Pause;
	public static String AudioPlayer_PauseTooltip;
	public static String AudioPlayer_Play;
	public static String AudioPlayer_PlayTooltip;
	public static String AudioPlayer_Previous;
	public static String AudioPlayer_PreviousTooltip;
	public static String AudioPlayer_Random;
	public static String AudioPlayer_RandomTooltip;
	public static String AudioPlayer_Stop;
	public static String AudioPlayer_StopTooltip;
	public static String AudioPlayer_Title;
	public static String File_LoadErrorMessage;
	public static String File_LoadErrorTitle;
	public static String GeneralPreferencePage_AllowInternalBrowsing;
	public static String GeneralPreferencePage_DefaultGMName;
	public static String GeneralPreferencePage_HiresPDF;
	public static String GeneralPreferencePage_LastSavedFile;
	public static String GeneralPreferencePage_LibraryDirectory;
	public static String GeneralPreferencePage_LoadLastSaved;
	public static String GeneralPreferencePage_PageDescription;
	public static String GeneralPreferencePage_PageName;
	public static String ImportItemAction_Add;
	public static String ImportItemAction_AlternateDialogMessage;
	public static String ImportItemAction_Cancel;
	public static String ImportItemAction_DialogMessage;
	public static String ImportItemAction_DialogTitle;
	public static String ImportItemAction_ErrorMessage;
	public static String ImportItemAction_ErrorTitle;
	public static String ImportItemAction_Import;
	public static String ImportItemAction_Name;
	public static String ImportItemAction_Tooltip;
	public static String Widget_DocumentRessourceTooltip;
	public static String Workspace_CannotRunWithout;
	public static String Workspace_Error;
	public static String ActorEditorStatsPage_Title;
	public static String ActorEditorMainPage_Title;
	public static String NewItemDetailsWizardPage_PDFFiles;
	public static String NewItemDetailsWizardPage_NewAudioDocument;
	public static String NewItemDetailsWizardPage_AudioFolderName;
	public static String NewItemDetailsWizardPage_NewAudioRessource;
	public static String NewItemDetailsWizardPage_AudioFiles;
	public static String NewItemDetailsWizardPage_ImageFiles;
	public static String NewItemDetailsWizardPage_AudioFileName;
	public static String DefaultStatBlock;
	public static String UnsavedChanges_Cancel;
	public static String UnsavedChanges_Ignore;
	public static String UnsavedChanges_Message;
	public static String UnsavedChanges_Save;
	public static String UnsavedChanges_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
	
	public static String getByName(String name){
		Field[] fields=Messages.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals(name)){
				try {
					return (String)fields[i].get(Messages.class.newInstance());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} 
			}
		}
		System.out.println("no field found"); //$NON-NLS-1$
		return null;		
	}
}
