package cn.edu.seu.robot;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import cn.edu.seu.robot.models.LibraryEntity;
import cn.edu.seu.robot.models.ProjectEntity;
import cn.edu.seu.robot.utils.EditorColorProvider;
import cn.edu.seu.robot.xmlparser.XmlLibraryParser;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "cn.edu.seu.robot"; //$NON-NLS-1$
	
	public static final String PREF_COLOR_DEFAULT = "colorDefault";
	public static final String PREF_COLOR_COMMENT = "colorComment";
	public static final String PREF_COLOR_STRING = "colorString";
	public static final String PREF_COLOR_KEYWORD = "colorKeyword";
	
	public static final String PREF_FONT_CODE = "fontCode";
	
	private static ProjectEntity project;
	private static List<LibraryEntity> libList = new ArrayList<>();
	
	private IPreferenceStore store;
	private EditorColorProvider colorProvider;

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		this.store = this.getPreferenceStore();
		this.colorProvider = new EditorColorProvider(this.getPreferenceStore());
		XmlLibraryParser.loadLibrary(libList);
	}
	
	public EditorColorProvider getEditorColorProvider(){
		return this.colorProvider;
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	
	public static ProjectEntity getCurrentProject(){
		return project;
	}
	
	public static void setCurrentProject(ProjectEntity pro){
		project = pro;
	}
	
	public static List<LibraryEntity> getLib(){
		return libList;
	}
	
}
