package cn.edu.seu.robot.navigator;

import org.eclipse.swt.graphics.Image;

import cn.edu.seu.robot.models.ITreeEntry;
import cn.edu.seu.robot.utils.LabelProviderAdapter;

public class ProjectExplorerLabelProvider extends LabelProviderAdapter {
	public String getText(Object element) {
		return ((ITreeEntry) element).getName();
	}

	public Image getImage(Object element) {
		return ((ITreeEntry) element).getImage();
	}
}
