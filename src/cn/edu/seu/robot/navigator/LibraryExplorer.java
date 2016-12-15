package cn.edu.seu.robot.navigator;

import java.util.List;

import org.eclipse.jface.action.IStatusLineManager;
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

import cn.edu.seu.robot.models.ITreeEntry;
import cn.edu.seu.robot.utils.EntryFactory;

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
		tv.setContentProvider(new ProjectExplorerContentProvider());
		tv.setLabelProvider(new ProjectExplorerLabelProvider());
		tv.setInput(EntryFactory.createNavigatorEntryTree());
		
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
					text.setText(entry.getName());
				}
			}
		});
		
		
		
	}

	@Override
	public void setFocus() {
		this.tv.getControl().setFocus();
	}

}
