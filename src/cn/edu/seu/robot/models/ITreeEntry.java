package cn.edu.seu.robot.models;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

public class ITreeEntry {

	private String name;
	private String type;

	private Image image;
	private ITreeEntry parentEntry;

	private List<ITreeEntry> children = new ArrayList<ITreeEntry>();

	public ITreeEntry() {
	}

	public ITreeEntry(String name) {
		this.name = name;
	}
	
	public ITreeEntry(String name, String type) {
		this.name = name;
		this.type = type;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return this.name;
	}

	public void setParentEntry(ITreeEntry parentEntry) {
		this.parentEntry = parentEntry;
	}

	public ITreeEntry getParentEntry() {
		return parentEntry;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setChildren(List<ITreeEntry> children) {
		this.children = children;
	}

	public List<ITreeEntry> getChildren() {
		return this.children;
	}

	public void addChild(ITreeEntry entry) {
		children.add(entry);
	}

	public boolean hasChild() {
		return children.size() > 0;
	}
}
