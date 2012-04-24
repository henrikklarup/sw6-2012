package dk.aau.cs.giraf.launcher;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	public static final String COLORSETTINGS = "colorSettings";
	public static final String COLOR_BG = "backgroundColor";
	
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
	private int intToDP(Context context, int i) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
	}
	
	/**
	 * Returns true if the device currently is held in landscape orientation by the user.
	 * @param context Context of the current activity
	 * @return true if the device currently is held in landscape orientation by the user.
	 */
	private boolean isLandscape(Context context) {
		int rotation = ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
		if ((rotation % 2) == 0) {
			return true;
		} else {
			return false;
		}
	}
}
