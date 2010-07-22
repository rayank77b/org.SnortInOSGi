package org.snortinosgi.frontend.snortinosgigui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.snortinosgi.frontend.snortinosgigui.views.View;

public class Perspective implements IPerspectiveFactory {

	IFolderLayout folder;
	
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		layout.addStandaloneView(View.ID,  false, IPageLayout.LEFT, 0.15f, editorArea);
		// layout.addView(viewId, relationship, ratio, refId);
		
		folder = layout.createFolder("informations", IPageLayout.TOP, 0.5f, editorArea);
		//folder.addView(AlertsView.ID);
		//folder.addView(ProtocolStatisticView.ID);
		//layout.addView(AlertsView.ID,  IPageLayout.RIGHT, 0.90f, editorArea);
		//layout.addView(ProtocolStatisticView.ID,  IPageLayout.BOTTOM, 0.50f, editorArea);
		
	}

}
