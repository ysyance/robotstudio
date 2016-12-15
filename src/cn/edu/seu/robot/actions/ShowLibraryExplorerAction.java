package cn.edu.seu.robot.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import cn.edu.seu.robot.navigator.LibraryExplorer;
import cn.edu.seu.robot.utils.PluginImage;

public class ShowLibraryExplorerAction extends Action {
	public static final String ID = "cn.edu.seu.robot.actions.showlibraryexplorer";
	IWorkbenchWindow window;

	public ShowLibraryExplorerAction(IWorkbenchWindow window) {
		this.window = window;
		this.setId(ID);
		this.setText("Show Library");
		this.setImageDescriptor(PluginImage.SHOW_LIBRARY_DESC);
	}
	
	@Override
	public void run() {
		IWorkbenchPage page = this.window.getActivePage();
		try {
			page.showView(LibraryExplorer.ID);
//			LibraryExplorer explorer = (LibraryExplorer) page.findView(LibraryExplorer.ID);
//			explorer.setInput(Activator.getCurrentProject());
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

}
