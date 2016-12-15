package cn.edu.seu.robot.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import cn.edu.seu.robot.Activator;
import cn.edu.seu.robot.models.ProjectEntity;
import cn.edu.seu.robot.navigator.ProjectExplorer;
import cn.edu.seu.robot.utils.PluginImage;

public class CloseProjectAction extends Action {
	public static String ID = "cn.edu.seu.robot.actions.closeprojectaction";
	IWorkbenchWindow window;

	public CloseProjectAction(IWorkbenchWindow window) {
		this.window = window;
		this.setId(ID);
		this.setText("Close Project");
		this.setImageDescriptor(PluginImage.CLOSE_PROJECT_DESC);
	}

	@Override
	public void run() {
		ProjectEntity pro = Activator.getCurrentProject();
		if (pro != null) {
			boolean isClose = MessageDialog.openConfirm(this.window.getShell(), "Close Project",
					"Are you sure to close current project ?");
			if (isClose) {
				Activator.setCurrentProject(null);
				IWorkbenchPage page = window.getActivePage();
				ProjectExplorer explorer = (ProjectExplorer) page.findView(ProjectExplorer.ID);
				explorer.setInput(null);
			}
		} else {
			MessageDialog.openInformation(this.window.getShell(), "Close Project", "Current project is Empty !");
		}

	}
}
