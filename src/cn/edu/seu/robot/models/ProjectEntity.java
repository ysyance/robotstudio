package cn.edu.seu.robot.models;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

public class ProjectEntity extends ITreeEntry {

	private String description;

	private String headerType;
	private String headerOrder;
	private String version;
	private String targetMachine;

	public ProjectEntity(String name) {
		super(name, EntityTypeFactory.PROJECT_ENTITY);
		this.setImage(PluginImage.PROJECT);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeaderType() {
		return headerType;
	}

	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}

	public String getHeaderOrder() {
		return headerOrder;
	}

	public void setHeaderOrder(String headerOrder) {
		this.headerOrder = headerOrder;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTargetMachine() {
		return targetMachine;
	}

	public void setTargetMachine(String targetMachine) {
		this.targetMachine = targetMachine;
	}

	@Override
	public String toString() {
		return "Project: " + this.getName() + "\n" 
				+ this.headerType + "\n"
				+ this.headerOrder + "\n"
				+ this.version + "\n"
				+ this.targetMachine;
	}

}
