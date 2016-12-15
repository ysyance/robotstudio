package cn.edu.seu.robot.navigator;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import cn.edu.seu.robot.models.ITreeEntry;

public class LibraryExplorer extends ViewPart {
	public static final String ID = "cn.edu.seu.robot.libnavigatorview";
	
	private TreeViewer tv;
	private List<ITreeEntry> list;

	public LibraryExplorer() {
	}

	@Override
	public void createPartControl(Composite parent) {
		tv = new TreeViewer(parent);
		tv.setContentProvider(new ProjectExplorerContentProvider());
		tv.setLabelProvider(new ProjectExplorerLabelProvider());
		
		getViewSite().setSelectionProvider(tv);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
