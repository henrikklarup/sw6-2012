package dk.aau.cs.giraf.launcher;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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
}
