package cn.edu.seu.robot.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import cn.edu.seu.robot.utils.PluginImage;

public class ShowPropertySheetAction extends Action {
	public static final String ID = "cn.edu.seu.robot.actions.showpropertysheetaction";
	IWorkbenchWindow window;

	public ShowPropertySheetAction(IWorkbenchWindow window) {
		this.window = window;
		this.setId(ID);
		this.setText("Show PropertSheet");
		this.setImageDescriptor(PluginImage.SHOW_PROPERTY_DESC);
	}
	
	@Override
	public void run() {
		IWorkbenchPage page = this.window.getActivePage();
		try {
			page.showView(IPageLayout.ID_PROP_SHEET);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

}
