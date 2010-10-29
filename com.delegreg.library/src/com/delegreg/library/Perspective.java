package com.delegreg.library;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.delegreg.library.ui.views.LibraryView;

public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
	    layout.setEditorAreaVisible(true);
	    //layout.addView(LibraryView.ID, IPageLayout.LEFT, 1.0f, layout.getEditorArea());
	    layout.addStandaloneView(LibraryView.ID, true, IPageLayout.LEFT, 1.0f, layout.getEditorArea());

	}

}
