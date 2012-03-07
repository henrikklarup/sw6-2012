package sw6.autism.admin;

import android.database.sqlite.SQLiteDatabase;

public class SettingsTable {

	//Table columns
	public static final String TABLE_NAME = "settings_table";
	public static final String COLUMN_ID = "settings_id";
	public static final String COLUMN_SETTINGS = "settings_settings"; //NICE NAVN
	public static final String COLUMN_OWNER = "apps_owner";


	//SQL string for creating table
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_SETTINGS + " TEXT NOT NULL, "
			+ COLUMN_OWNER + " TEXT NOT NULL"
			+ ");";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}