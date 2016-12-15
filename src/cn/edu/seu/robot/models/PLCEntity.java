package cn.edu.seu.robot.models;

import cn.edu.seu.robot.utils.EntityTypeFactory;
import cn.edu.seu.robot.utils.PluginImage;

class FileHeader{
	public String comcompanyName = "";
	public String companyURL = "";
	public String productName = "";
	public String productVersion = "";
	public String productRelease = "";
	public String creationDateTime = "";
	public String contentDescription ="";
}

class ContentHeader {
	public String name = "";
	public String version = "";
	public String modificationDateTime = "";
	public String organization = "";
	public String author = "";
	public String language = "";
	
	public String pageSize_x = "";
	public String pageSize_y = "";
	public String fbd_x = "";
	public String fbd_y = "";
	public String ld_x = "";
	public String ld_y = "";
	public String sfc_x = "";
	public String sfc_y = "";
	
}

public class PLCEntity extends ITreeEntry {
	
	private FileHeader fileHeader;
	private ContentHeader contentHeader;
	
	
	public PLCEntity(String name) {
		super(name, EntityTypeFactory.PLC_ENTITY);
		this.setImage(PluginImage.PLC);
		
	}
	public FileHeader getFileHeader() {
		return fileHeader;
	}
	public void setFileHeader(FileHeader fileHeader) {
		this.fileHeader = fileHeader;
	}
	public ContentHeader getContentHeader() {
		return contentHeader;
	}
	public void setContentHeader(ContentHeader contentHeader) {
		this.contentHeader = contentHeader;
	}
	
}
