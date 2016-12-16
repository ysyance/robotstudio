package cn.edu.seu.robot.utils;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import cn.edu.seu.robot.Activator;

public class PluginImage {
	public static final Image NEW_FOLDER; 
	
	public static final Image FUNCTION;
	public static final Image FUNCTIONBLOCK;
	public static final Image PROGRAM;
	public static final Image POU_FOLDER;
	public static final Image PLC;
	public static final Image ROBOT;
	public static final Image SERVO_FOLDER;
	public static final Image PROJECT;
	public static final Image SERVO;
	public static final Image IO_FACILITY;
	public static final Image LIB_ENTITY;
	
	public static final Image ROBOT_MASTER_FORM;
	
	public static final ImageDescriptor OPEN_FILE_DESC;
	public static final ImageDescriptor CLOSE_PROJECT_DESC;
	public static final ImageDescriptor OPEN_PROJECT_DESC;
	public static final ImageDescriptor NEW_PROJECT_DESC;
	public static final ImageDescriptor SHOW_NAVIGATOR_DESC;
	public static final ImageDescriptor EXPAND_DESC;
	public static final ImageDescriptor SHOW_CONSOLE_DESC;
	public static final ImageDescriptor SHOW_LIBRARY_DESC;
	public static final ImageDescriptor SHOW_PROPERTY_DESC;
	
	static{
		String iconPath = "icons/";
		NEW_FOLDER = createImageDescriptor(iconPath + "new_folder.gif").createImage();
		FUNCTION = createImageDescriptor(iconPath + "function_public.png").createImage();
		FUNCTIONBLOCK = createImageDescriptor(iconPath + "fb.png").createImage();
		PROGRAM = createImageDescriptor(iconPath + "program.png").createImage();
		POU_FOLDER = createImageDescriptor(iconPath + "poufolder.png").createImage();
		PLC = createImageDescriptor(iconPath + "plc.png").createImage();
		ROBOT = createImageDescriptor(iconPath + "robot.bmp").createImage();
		SERVO_FOLDER = createImageDescriptor(iconPath + "servofolder.png").createImage();
		PROJECT = createImageDescriptor(iconPath + "project.png").createImage();
		SERVO = createImageDescriptor(iconPath + "servo.png").createImage();
		IO_FACILITY = createImageDescriptor(iconPath + "io.png").createImage();
		LIB_ENTITY = createImageDescriptor(iconPath + "maven_index.gif").createImage();
		
		ROBOT_MASTER_FORM = createImageDescriptor(iconPath + "form_banner.gif").createImage();
		
		OPEN_FILE_DESC = createImageDescriptor(iconPath + "openfile.png");
		CLOSE_PROJECT_DESC = createImageDescriptor(iconPath + "closeproject.png");
		OPEN_PROJECT_DESC = createImageDescriptor(iconPath + "openproject.png");
		NEW_PROJECT_DESC = createImageDescriptor(iconPath + "newproject.png");
		SHOW_NAVIGATOR_DESC = createImageDescriptor(iconPath + "explore.png");
		EXPAND_DESC = createImageDescriptor(iconPath + "expand.png");
		SHOW_CONSOLE_DESC = createImageDescriptor(iconPath + "console_view.gif");
		SHOW_LIBRARY_DESC = createImageDescriptor(iconPath + "res.png");
		SHOW_PROPERTY_DESC = createImageDescriptor(iconPath + "properties_view.gif");
	}
	

	private static ImageDescriptor createImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, path);
	}
}
