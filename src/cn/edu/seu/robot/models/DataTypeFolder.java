package cn.edu.seu.robot.models;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

public class DataTypeFolder extends ITreeEntry {
	public DataTypeFolder() {
		super("DataTypes", EntityTypeFactory.POU_FLODER);
		this.setImage(PluginImage.POU_FOLDER);
	}
}
