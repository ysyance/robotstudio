package cn.edu.seu.robot.navigator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import cn.edu.seu.robot.Activator;
import cn.edu.seu.robot.editor.CodeEditor;
import cn.edu.seu.robot.editor.CodeEditorInput;
import cn.edu.seu.robot.models.ITreeEntry;
import cn.edu.seu.robot.models.ProjectEntity;
import cn.edu.seu.robot.utils.PluginImage;

public class ProjectExplorer extends ViewPart {
	public static final String ID = "cn.edu.seu.robot.projectnavigator";
	
	private TreeViewer tv;
	private List<ITreeEntry> list;
	
	private Action expandAction;

	public ProjectExplorer() {
	}

	@Override
	public void createPartControl(Composite parent) {
		expandAction = new ExpandAction();
		
		tv = new TreeViewer(parent);
		tv.setContentProvider(new ProjectExplorerContentProvider());
		tv.setLabelProvider(new ProjectExplorerLabelProvider());
		
		this.getViewSite().setSelectionProvider(tv);
		
		
		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().add(expandAction);
		

		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				IStatusLineManager statusline = getViewSite().getActionBars().getStatusLineManager();
				Object obj = selection.getFirstElement();
				
				if(obj != null && obj instanceof ITreeEntry) {
					ITreeEntry entry = (ITreeEntry)obj;
					statusline.setMessage(entry.getImage(), entry.getName() + " selected");
				}
			}
		});
		
		tv.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IWorkbenchPage page = getViewSite().getWorkbenchWindow().getActivePage();
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				Object obj = selection.getFirstElement();
				
				if(obj != null && obj instanceof ITreeEntry) {
					ITreeEntry entry = (ITreeEntry)obj;
					CodeEditorInput input = new CodeEditorInput(entry.getName());
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
		});
		
	}

	@Override
	public void setFocus() {
		this.tv.getControl().setFocus();
	}
	
	public void update() {
		System.out.println(Activator.getCurrentProject());
		this.tv.refresh();
		System.out.println("update");
	}
	
	public void setInput(ProjectEntity pro) {
		
		list = new ArrayList<ITreeEntry>();
		if(pro != null){
			list.add(pro);
		}
		
		this.tv.setInput(list);
	}
	
	
	private class ExpandAction extends Action{
		private boolean expand;
		public ExpandAction() {
			this.setText("Expand");
			this.setToolTipText("Expand All the Tree Entry");
			this.setImageDescriptor(PluginImage.EXPAND_DESC);
			this.expand = false;
		}
		@Override
		public void run() {
			if(this.expand == false){
				this.expand = true;
				tv.expandToLevel(list, TreeViewer.ALL_LEVELS);
			} else {
				this.expand = false;
				tv.collapseAll();
			}
		}
	}
	

}
