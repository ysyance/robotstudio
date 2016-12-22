package cn.edu.seu.robot.editors;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import cn.edu.seu.robot.models.ITreeEntry;

public class CodeEditorInput implements IEditorInput {

	private String name;
	private ITreeEntry entry;

	public CodeEditorInput(String name, ITreeEntry entry) {
		this.name = name;
		this.entry = entry;
	}
	public ITreeEntry getEntry(){
		return this.entry;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CodeEditorInput) {
			CodeEditorInput newDbEditorInput = (CodeEditorInput) obj;
			if (this.name.equals(newDbEditorInput.getName())) {
				return true;
			}
		}
		return false;
	}

}
