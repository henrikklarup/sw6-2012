/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package giraffe.launcher;

import android.content.ComponentName;
import android.content.Intent;
// Maybe color isnt the right type //check
import android.graphics.Color;
import android.graphics.drawable.Drawable;

class ApplicationInfo {
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
