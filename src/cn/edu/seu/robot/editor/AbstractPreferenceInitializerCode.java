package cn.edu.seu.robot.editor;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

import cn.edu.seu.robot.Activator;


public class AbstractPreferenceInitializerCode extends
		AbstractPreferenceInitializer {

	public AbstractPreferenceInitializerCode() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		store.setDefault(Activator.PREF_COLOR_DEFAULT, StringConverter.asString(new RGB(0,0,0)));
		store.setDefault(Activator.PREF_COLOR_COMMENT, StringConverter.asString(new RGB(0,128,0)));
		store.setDefault(Activator.PREF_COLOR_STRING, StringConverter.asString(new RGB(0,0,255)));
		store.setDefault(Activator.PREF_COLOR_KEYWORD, StringConverter.asString(new RGB(255,0,0)));
		
		store.setDefault(Activator.PREF_FONT_CODE, StringConverter.asString(new FontData("Courier New", 9, SWT.NORMAL)));
	}
}
