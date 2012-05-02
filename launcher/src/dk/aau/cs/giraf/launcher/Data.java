package dk.aau.cs.giraf.launcher;

public final class Data {
	
	// SharedPreferences keys for log in data
	public static final String TIMERKEY = "TIMING";
	public static final String DATEKEY = "DATE";
	
	// Keys for inserting and retrieving data in Intents
	public static final String GUARDIANID = "currentGuardianID";
	public static final String CHILDID = "currentChildID";
	public static final String APP_PACKAGENAME = "appPackageName";
	public static final String APP_ACTIVITYNAME = "appActivityName";
	public static final String SKIP = "skipActivity";

	// Keys for settings
	public static final String COLORS = "colorSettings";
	public static final String COLOR_BG = "backgroundColor";

	// Constants denoting user roles
	public static final long ROLE_GUARDIAN = 1L;
	public static final long ROLE_CHILD = 3L;

	//Authentication activity
	public static final long TIME_TO_STAY_LOGGED_IN = 14400000;
	
	// Home activity values.
	public static final int DRAWER_WIDTH = 400;
	public static final int HOMEBAR_WIDTH_LANDSCAPE = 200;
	public static final int GRID_CELL_WIDTH = 290;
	public static final int APPS_PER_PAGE = 9;
	public static final int MAX_ROWS_PER_PAGE = 3;
	public static final int MAX_COLUMNS_PER_PAGE = 4;
	
	// Home activity graphics values
	public static final int PROFILEPIC_WIDTH_LANDSCAPE = 70;
	public static final int PROFILEPIC_HEIGHT_LANDSCAPE = 91;
	public static final int HOMEBAR_PADDING_LANDSCAPE = 15;

	public static final int WIDGET_CONNECTIVITY_MARGIN_LEFT = 0;
	public static final int WIDGET_CONNECTIVITY_MARGIN_TOP = 106;
	public static final int WIDGET_CONNECTIVITY_MARGIN_RIGHT = 0;
	public static final int WIDGET_CONNECTIVITY_MARGIN_BOTTOM = 0;
	
	public static final int WIDGET_CALENDAR_MARGIN_LEFT = 0;
	public static final int WIDGET_CALENDAR_MARGIN_TOP = 15;
	public static final int WIDGET_CALENDAR_MARGIN_RIGHT = 0;
	public static final int WIDGET_CALENDAR_MARGIN_BOTTOM = 0;
	
	// Error logging
	public static final String ERRORTAG = "launcher";

}
