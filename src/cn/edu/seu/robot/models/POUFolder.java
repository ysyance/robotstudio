package cn.edu.seu.robot.models;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

public class POUFolder extends ITreeEntry {

	public POUFolder() {
		super("POUS", EntityTypeFactory.POU_FLODER);
		this.setImage(PluginImage.POU_FOLDER);
	}
	
}
