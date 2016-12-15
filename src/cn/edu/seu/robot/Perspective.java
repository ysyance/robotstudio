package cn.edu.seu.robot;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import cn.edu.seu.robot.navigator.LibraryExplorer;
import cn.edu.seu.robot.navigator.ProjectExplorer;

public class Perspective implements IPerspectiveFactory {
	
	
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		layout.addView(ProjectExplorer.ID, IPageLayout.LEFT, 0.20f, layout.getEditorArea());
		layout.addView(IConsoleConstants.ID_CONSOLE_VIEW, IPageLayout.BOTTOM, 0.80f, layout.getEditorArea());
		layout.addView(LibraryExplorer.ID, IPageLayout.RIGHT, 0.75f, layout.getEditorArea());
		layout.addView(IPageLayout.ID_PROP_SHEET, IPageLayout.BOTTOM, 0.65f, LibraryExplorer.ID);
		
	}
}
