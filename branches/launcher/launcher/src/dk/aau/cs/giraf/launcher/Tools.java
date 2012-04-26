package dk.aau.cs.giraf.launcher;

import java.util.Date;
import java.util.List;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Class for holding static methods and fields, to minimize code duplication.
 */
public class Tools {

	// SharedPreferences keys for log in data.
	public static final String TIMERKEY = "TIMING";
	public static final String DATEKEY = "DATE";

	// Keys for inserting and retrieving data in Intents.
	public static final String GUARDIANID = "currentGuardianID";
	public static final String CHILDID = "currentChildID";
	public static final String APP_PACKAGENAME = "appPackageName";
	public static final String APP_ACTIVITYNAME = "appActivityName";

	// Keys for settings
	public static final String COLORS = "colorSettings";
	public static final String COLOR_BG = "backgroundColor";
	
	// Constants denoting user roles:
	public static final Long ROLE_GUARDIAN = 1L;
	public static final Long ROLE_CHILD = 3L;

	// 24 hours in milliseconds = 86400000;
	// 4 hours in milliseconds:
	private static final long mAuthSpan = 14400000;

	/**
	 * Saves data for the currently authorized log in.
	 * @param context Context of the current activity.
	 * @param id ID of the guardian logging in.
	 */
	public static void saveLogInData(Context context, Long id) {
		SharedPreferences sp = context.getSharedPreferences(TIMERKEY, 0);
		SharedPreferences.Editor editor = sp.edit();

		Date d = new Date();
		editor.putLong(DATEKEY, d.getTime());

		editor.putLong(GUARDIANID, id);

		editor.commit();
	}

	/**
	 Logs the current guardian out and launches the authentication activity.
	 * @param context Context of the current activity.
	 * @return The intent required to launch authentication.
	 */
	public static Intent logOutIntent(Context context) {
		clearAuthData(context);

		return new Intent(context, AuthenticationActivity.class);
	}

	/**
	 * Clears the current data on who is logged in and when they logged in.
	 * @param context Context of the current activity.
	 */
	public static void clearAuthData(Context context) {
		SharedPreferences sp = context.getSharedPreferences(TIMERKEY, 0);
		SharedPreferences.Editor editor = sp.edit();

		editor.putLong(DATEKEY, 1);
		editor.putLong(GUARDIANID, -1);

		editor.commit();
	}

	/**
	 * Checks whether the current user session has expired.
	 * @param context Context of the current activity.
	 * @return True if a log in is required; otherwise false.
	 */
	public static boolean AuthRequired(Context context) {
		SharedPreferences sp = context.getSharedPreferences(TIMERKEY, 0);
		Long lastAuthTime = sp.getLong(DATEKEY, 1);
		Date d = new Date();

		return d.getTime() > lastAuthTime + mAuthSpan;
	}

	/**
	 * Converts integer to density pixels (dp)
	 * @param context Context of the current activity
	 * @param i The integer which should be used for convertion
	 * @return i converted to density pixels (dp)
	 */
	public static int intToDP(Context context, int i) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
	}

	/**
	 * Returns true if the device currently is held in landscape orientation by the user.
	 * @param context Context of the current activity.
	 * @return true if the device currently is held in landscape orientation by the user.
	 */
	public static boolean isLandscape(Context context) {
		int rotation = ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
		if ((rotation % 2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the apps that are usable by the given user, relative to their settings and the system they're logged in on.
	 * @param context Context of the current activity.
	 * @param currentUser The user to find apps for.
	 * @return List of apps that are usable by this user on this device.
	 */
	public static List<App> getUsableApps(Context context, Profile currentUser) {
		Helper helper = new Helper(context);

		List<App> userApps = helper.appsHelper.getAppsByProfile(currentUser);

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		List<ResolveInfo> systemApps = context.getPackageManager().queryIntentActivities(mainIntent, 0);

		// Remove all non-GIRAF apps from the list of apps in the system.
		for (int i = 0; i < systemApps.size(); i++) {
			if (!(systemApps.get(i).toString().toLowerCase().contains("dk.aau.cs.giraf") && 
					!systemApps.get(i).toString().toLowerCase().contains("launcher"))) {
				systemApps.remove(i); 
				i--;
			}
		}

		// Remove all apps from user's list of apps that are not installed on the current device.
		for (int i = 0; i < userApps.size(); i++) {
			if (!containsPackage(systemApps, userApps.get(i).getaPackage())) {
				userApps.remove(i);
				i--;
			}
		}

		return userApps;
	}

	/**
	 * Checks whether a list of GIRAF apps installed on the system contains a specified app.
	 * @param systemApps List of apps to check.
	 * @param packageName Package name of the app to check for.
	 * @return True if the app is contained in the list; otherwise false.
	 */
	public static boolean containsPackage(List<ResolveInfo> systemApps, String packageName) {
		for (ResolveInfo app : systemApps) {
			if (app.activityInfo.packageName == packageName) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Attaches all GIRAF apps currently installed on a device to a given user.
	 * @param context Context of the current activity.
	 * @param currentUser The user to attach apps to.
	 */
	public static void attachAllSystemAppsToUser(Context context, Profile currentUser) {
		Helper helper = new Helper(context);

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		List<ResolveInfo> systemApps = context.getPackageManager().queryIntentActivities(mainIntent, 0);

		// Remove all non-GIRAF apps from the list of apps in the system.
		for (int i = 0; i < systemApps.size(); i++) {
			if (!(systemApps.get(i).toString().toLowerCase().contains("dk.aau.cs.giraf") && 
					!systemApps.get(i).toString().toLowerCase().contains("launcher"))) {
				systemApps.remove(i); 
				i--;
			}
		}
		
		Log.i("GIRAF_APP", "GIRAF apps currently installed on device: " + systemApps.size());

		int i = 0;
		
		for (ResolveInfo app : systemApps) {
			App userApp = new App(app.loadLabel(context.getPackageManager()).toString(), app.activityInfo.packageName, app.activityInfo.name);
			helper.appsHelper.attachAppToProfile(userApp, currentUser);
			//Log.i("GIRAF_APP", "App " + i + " attach result: " + result);
			i++;
			Log.i("GIRAF_APP", "Attachment run.");
		}
	}
}
