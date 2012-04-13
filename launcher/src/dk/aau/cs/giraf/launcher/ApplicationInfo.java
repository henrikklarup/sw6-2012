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
	
	String packageName;
	
	String activityName;

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
