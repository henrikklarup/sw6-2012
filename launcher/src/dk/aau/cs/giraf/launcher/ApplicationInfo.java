package dk.aau.cs.giraf.launcher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ServiceInfo;
// Maybe color isnt the right type //check
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

class ApplicationInfo{
	ServiceInfo category;
	
	/**
	 * The application name.
	 */
	CharSequence title;

	/**
	 * The intent used to start the application.
	 */
	Intent intent;

	/**
	 * The application icon.
	 */
	Drawable icon;

	/**
	 * The application icon background color.
	 */
	Color bgcolor;
	
	/**
	 * The application icon color.
	 */
	Color iconcolor;
	
	ComponentName className;

	/**
	 * Creates the application intent based on a component name and various launch flags.
	 *
	 * @param className the class name of the component representing the intent
	 * @param launchFlags the launch flags
	 */
	final void setActivity(ComponentName className, int launchFlags) {
		intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(className);
		this.className = className;
		intent.setFlags(launchFlags);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ApplicationInfo)) {
			return false;
		}

		ApplicationInfo that = (ApplicationInfo) o;
		return title.equals(that.title) &&
				intent.getComponent().getClassName().equals(
						that.intent.getComponent().getClassName());
	}

}
