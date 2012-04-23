package dk.aau.cs.giraf.launcher;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Class for holding static methods and field, to minimize code duplication.
 */
public class Tools {
	
	// SharedPreferences keys for log in data.
	public static final String TIMERKEY = "TIMING";
	public static final String DATEKEY = "DATE";
	
	// Keys for inserting and retrieving data in Intents.
	public static final String GUARDIANID = "currentGuardianID";
	
	// Hash keys for settings.
	public static final String BACKGROUNDCOLOR = "backgroundColor";
	public static final String APPBACKGROUNDCOLOR = "appBackgroundColor";
	
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
	 * Logs the current guardian out and launches the authentication activity.
	 * @param context Context of the current activity.
	 */
	public static void logOut(Context context) {
		clearLogOutData(context);
		
		Intent i = new Intent(context, AuthenticationActivity.class);
		context.startActivity(i);
	}
	
	/**
	 * Clears the current data on who is logged in and when they logged in.
	 * @param context Context of the current activity.
	 */
	public static void clearLogOutData(Context context) {
		SharedPreferences sp = context.getSharedPreferences(TIMERKEY, 0);
		SharedPreferences.Editor editor = sp.edit();
		
		editor.putLong(DATEKEY, 1);
		editor.putLong(GUARDIANID, -1);
		
		editor.commit();
	}
}
