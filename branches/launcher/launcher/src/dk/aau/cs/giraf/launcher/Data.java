package dk.aau.cs.giraf.launcher;

public final class Data {
	
	// SharedPreferences keys for log in data.
	public static final String TIMERKEY = "TIMING";
	public static final String DATEKEY = "DATE";
	
	// Keys for inserting and retrieving data in Intents.
	public static final String GUARDIANID = "currentGuardianID";
	public static final String CHILDID = "currentChildID";
	public static final String APP_PACKAGENAME = "appPackageName";
	public static final String APP_ACTIVITYNAME = "appActivityName";
	public static final String SKIP = "skipActivity";

	// Keys for settings.
	public static final String COLORS = "colorSettings";
	public static final String COLOR_BG = "backgroundColor";

	// Constants denoting user roles.
	public static final long ROLE_GUARDIAN = 1L;
	public static final long ROLE_CHILD = 3L;
	
	// Home activity values.
	public static final int DRAWER_WIDTH = 400;
	public static final int HOMEBAR_WIDTH_LANDSCAPE = 200;

}
