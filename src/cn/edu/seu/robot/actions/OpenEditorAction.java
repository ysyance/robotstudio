package cn.edu.seu.robot.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import cn.edu.seu.robot.editor.CodeEditor;
import cn.edu.seu.robot.editor.CodeEditorInput;
import cn.edu.seu.robot.utils.PluginImage;

public class OpenEditorAction extends Action{
	public static final String ID = "cn.edu.seu.robot.actions.openeditoraction";
	IWorkbenchWindow window;
	
	public OpenEditorAction(IWorkbenchWindow window) {
		this.window = window;
		this.setId(ID);
		this.setText("Open File");
		this.setImageDescriptor(PluginImage.OPEN_FILE_DESC);
	}
	
	@Override
	public void run(){
		FileDialog dialog = new FileDialog(window.getShell(), SWT.OPEN);
		String path = dialog.open();
		if(path != null) {
			
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			CodeEditorInput input = new CodeEditorInput(path);
			IEditorPart editor = page.findEditor(input);
			if(editor != null) {
				page.bringToTop(editor);
			} else {
				try {
					page.openEditor(input, CodeEditor.ID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
