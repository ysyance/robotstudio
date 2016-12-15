package cn.edu.seu.robot.actions;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

import cn.edu.seu.robot.Activator;
import cn.edu.seu.robot.models.ProjectEntity;
import cn.edu.seu.robot.navigator.ProjectExplorer;
import cn.edu.seu.robot.utils.PluginImage;
import cn.edu.seu.robot.xmlparser.XmlProjectParser;

public class LoadProjectAction extends Action {
	public static final String ID = "cn.edu.seu.robot.actions.loadprojectaction";
	IWorkbenchWindow window;

	public LoadProjectAction(IWorkbenchWindow window) {
		this.window = window;
		this.setId(ID);
		this.setText("Open Project");
		this.setImageDescriptor(PluginImage.OPEN_PROJECT_DESC);
	}

	@Override
	public void run() {

		ProjectEntity project = Activator.getCurrentProject();
		if (project != null) {
			MessageDialog.openInformation(window.getShell(), "warning",
					"Please close current project first before loading new project !");
		} else {
			FileDialog dialog = new FileDialog(window.getShell(), SWT.OPEN);
			dialog.setFilterExtensions(new String[] { "*.project" });
			String path = dialog.open();
			if (path != null) {
				File file = new File(path);
				XmlProjectParser proLoader = new XmlProjectParser(file);
				try {
					Activator.setCurrentProject(proLoader.load());
				} catch (Exception e) {
					e.printStackTrace();
				}

				IWorkbenchPage page = window.getActivePage();
				ProjectExplorer explorer = (ProjectExplorer) page.findView(ProjectExplorer.ID);
				explorer.setInput(Activator.getCurrentProject());
			}
		}
	}
}
