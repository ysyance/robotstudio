package cn.edu.seu.robot.editor;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class CodeEditorInput implements IEditorInput {
	
	String name;
	
	public CodeEditorInput(String name) {
		this.name = name;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		return new File(name).exists();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		return this.name;
	}
	
	  @Override  
	    public boolean equals(Object obj) {  
	        if(obj instanceof CodeEditorInput){  
	        	CodeEditorInput newDbEditorInput = (CodeEditorInput)obj;  
	            if(this.name.equals(newDbEditorInput.getName())) {  
	                return true;  
	            }  
	        }  
	        return false;  
	    }

}
