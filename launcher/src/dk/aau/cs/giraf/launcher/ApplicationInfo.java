package dk.aau.cs.giraf.launcher;

import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;

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
	int color;
	
	/**
	 * The package name the application belongs to.
	 */
	String packageName;
	
	/**
	 * The name of the main activity in the application.
	 */
	String activityName;
	
	/**
	 * ID of the guardian who is using the launcher.
	 */
	Long guardian;
	
	/**
	 * ID of the application.
	 */
	Long app;
	
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
