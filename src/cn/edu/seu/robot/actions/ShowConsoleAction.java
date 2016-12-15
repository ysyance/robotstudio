package cn.edu.seu.robot.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.IConsoleConstants;

import cn.edu.seu.robot.utils.PluginImage;

public class ShowConsoleAction extends Action {
	public static final String ID = "cn.edu.seu.robot.actions.showconsoleaction";
	IWorkbenchWindow window;
	
	public ShowConsoleAction(IWorkbenchWindow window){
		this.window = window;
		this.setId(ID);
		this.setText("Show Console");
		this.setImageDescriptor(PluginImage.SHOW_CONSOLE_DESC);
	}
	
	@Override
	public void run() {
		IWorkbenchPage page = this.window.getActivePage();
		try {
			page.showView(IConsoleConstants.ID_CONSOLE_VIEW);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}
