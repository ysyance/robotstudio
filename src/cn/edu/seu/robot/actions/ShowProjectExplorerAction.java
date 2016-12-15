package cn.edu.seu.robot.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import cn.edu.seu.robot.Activator;
import cn.edu.seu.robot.navigator.ProjectExplorer;
import cn.edu.seu.robot.utils.PluginImage;

public class ShowProjectExplorerAction extends Action {
	public static final String ID = "cn.edu.seu.robot.actions.showprojectexplorer";
	IWorkbenchWindow window;

	public ShowProjectExplorerAction(IWorkbenchWindow window) {
		this.window = window;
		this.setId(ID);
		this.setText("Show Navigator");
		this.setImageDescriptor(PluginImage.SHOW_NAVIGATOR_DESC);
	}

	@Override
	public void run() {
		IWorkbenchPage page = this.window.getActivePage();
		try {
			page.showView(ProjectExplorer.ID);
			ProjectExplorer explorer = (ProjectExplorer) page.findView(ProjectExplorer.ID);
			explorer.setInput(Activator.getCurrentProject());
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}
