package cn.edu.seu.robot.navigator;

import java.util.List;

import cn.edu.seu.robot.models.ITreeEntry;
import cn.edu.seu.robot.utils.TreeContentProviderAdapter;

public class ProjectExplorerContentProvider extends TreeContentProviderAdapter {
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			return ((List<ITreeEntry>) inputElement).toArray();
		} else {
			return new Object[0];
		}
	}

	public boolean hasChildren(Object element) {
		ITreeEntry entry = (ITreeEntry) element;
		return !entry.getChildren().isEmpty();
	}

	public Object[] getChildren(Object parentElement) {
		ITreeEntry entry = (ITreeEntry) parentElement;
		return entry.getChildren().toArray();
	}
}
