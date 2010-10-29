package com.delegreg.rpgm;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.delegreg.library.ui.views.LibraryView;
import com.delegreg.rpgm.ui.views.CampaignsView;

public class CompoundPerspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
	    layout.setEditorAreaVisible(true);
	    //layout.addView(LibraryView.ID, IPageLayout.LEFT, 1.0f, layout.getEditorArea());
//	    layout.addStandaloneView(CampaignsView.ID, true, IPageLayout.LEFT, 1.0f, layout.getEditorArea());
//	    layout.addStandaloneView(LibraryView.ID, true, IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    IFolderLayout fold=layout.createFolder("com.delegreg.views.models", IPageLayout.LEFT, 1.0f, layout.getEditorArea());
	    fold.addView(CampaignsView.ID);
	    fold.addView(LibraryView.ID);
	    //layout.addView(PDFDocView.ID, IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView(PDFDocView.ID + ":2", IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView(PDFDocView.ID + ':' + "c:\\workspace\\com.delegreg.rpgm\\pdf_samples\\D&D 3.5 - Players Handbook [OEF].pdf.pdf", IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView("org.jpedal.pdf.plugins.eclipse.views.PDFView", IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView(HttpDocView.ID, IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView("orfeus.views.Orfeus", IPageLayout.RIGHT, 1.0f, layout.getEditorArea());

	}

	
	
	
}
