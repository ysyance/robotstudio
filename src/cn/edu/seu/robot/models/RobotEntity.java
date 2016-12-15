package cn.edu.seu.robot.models;

import java.util.ArrayList;
import java.util.List;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

public class RobotEntity extends ITreeEntry {

	private String robotType = "";
	private List<DHParam> dhparams = new ArrayList<>();

	public RobotEntity(String name) {
		super(name, EntityTypeFactory.ROBOT_ENTITY);
		this.setImage(PluginImage.ROBOT);
	}

	public List<DHParam> getDhparams() {
		return dhparams;
	}

	public void setDhparams(List<DHParam> dhparams) {
		this.dhparams = dhparams;
	}

	public String getRobotType() {
		return robotType;
	}

	public void setRobotType(String robotType) {
		this.robotType = robotType;
	}

}
