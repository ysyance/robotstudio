package cn.edu.seu.robot.models;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

public class ServoFolder extends ITreeEntry {
	private String servoPeriod;

	public ServoFolder() {
		super("ServoFacility", EntityTypeFactory.SERVO_FOLDER);
		this.setImage(PluginImage.SERVO_FOLDER);
		this.servoPeriod = String.valueOf(4000000);
	}

	public String getServoPeriod() {
		return servoPeriod;
	}

	public void setServoPeriod(String servoPeriod) {
		this.servoPeriod = servoPeriod;
	}

}
