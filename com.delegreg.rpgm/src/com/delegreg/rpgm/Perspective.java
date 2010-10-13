package com.delegreg.rpgm;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.delegreg.rpgm.ui.views.CampaignsView;


public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
	    layout.setEditorAreaVisible(true);
	    //layout.addView(CampaignsView.ID, IPageLayout.LEFT, 1.0f, layout.getEditorArea());
	    layout.addStandaloneView(CampaignsView.ID, false, IPageLayout.LEFT, 1.0f, layout.getEditorArea());
	    //layout.addView(PDFDocView.ID, IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView(PDFDocView.ID + ":2", IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView(PDFDocView.ID + ':' + "c:\\workspace\\com.delegreg.rpgm\\pdf_samples\\D&D 3.5 - Players Handbook [OEF].pdf.pdf", IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView("org.jpedal.pdf.plugins.eclipse.views.PDFView", IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView(HttpDocView.ID, IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    //layout.addView("orfeus.views.Orfeus", IPageLayout.RIGHT, 1.0f, layout.getEditorArea());
	    
	}



}
