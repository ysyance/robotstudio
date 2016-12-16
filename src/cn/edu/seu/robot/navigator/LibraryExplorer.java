package cn.edu.seu.robot.navigator;

import java.util.List;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import cn.edu.seu.robot.Activator;
import cn.edu.seu.robot.models.IPOUEntity;
import cn.edu.seu.robot.models.ITreeEntry;
import cn.edu.seu.robot.models.LibraryEntity;

public class LibraryExplorer extends ViewPart {
	public static final String ID = "cn.edu.seu.robot.libnavigatorview";
	
	private TreeViewer tv;
	private Text text;
	private List<ITreeEntry> list;

	public LibraryExplorer() {
	}

	@Override
	public void createPartControl(Composite parent) {
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
		tv = new TreeViewer(sashForm);
		tv.setContentProvider(new ExplorerContentProvider());
		tv.setLabelProvider(new ExplorerLabelProvider());
		tv.setInput(Activator.getLib());
		
		getViewSite().setSelectionProvider(tv);
		
		text = new Text(sashForm, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY );
		text.setBackground(new Color(Display.getCurrent(), new RGB(255, 255, 255)));
		
		sashForm.setWeights(new int[] {2, 1});
		
		
		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				Object obj = selection.getFirstElement();
				IStatusLineManager statusline = getViewSite().getActionBars().getStatusLineManager();
				
				if(obj != null && obj instanceof ITreeEntry) {
					ITreeEntry entry = (ITreeEntry)obj;
					
					statusline.setMessage(entry.getImage(), entry.getName() + " selected");
					if(obj instanceof IPOUEntity){
						IPOUEntity pouEntry = (IPOUEntity)obj;
						text.setText(pouEntry.getPouInfo());
					}
				}
			}
		});
		
		tv.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				Object obj = selection.getFirstElement();
				if(obj != null && obj instanceof LibraryEntity) {
					LibraryEntity entity = (LibraryEntity)obj;
					boolean isExpand = tv.getExpandedState(entity);
					if(isExpand == false) {
						tv.expandToLevel(entity, 1);
					} else {
						tv.collapseToLevel(entity, 1);
					}
				}
			}
		});
		
		
		
	}

	@Override
	public void setFocus() {
		this.tv.getControl().setFocus();
	}

}
