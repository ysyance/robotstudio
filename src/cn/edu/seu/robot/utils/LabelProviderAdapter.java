package cn.edu.seu.robot.utils;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class LabelProviderAdapter implements ILabelProvider{
	public Image getImage(Object element) {return null;}
	public void addListener(ILabelProviderListener listener) {}
	public void dispose(){}
	public boolean isLabelProperty(Object element, String property) {return false;}
	public void removeListener(ILabelProviderListener listener){}
	public String getText(Object element) {	return null;}
}
