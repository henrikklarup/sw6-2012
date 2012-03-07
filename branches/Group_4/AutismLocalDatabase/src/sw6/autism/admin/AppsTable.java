package sw6.autism.admin;

import android.database.sqlite.SQLiteDatabase;

public class AppsTable {

	//Table columns
	public static final String TABLE_NAME = "apps_table";
	public static final String COLUMN_ID = "apps_id";
	public static final String COLUMN_NAME = "apps_name";
	public static final String COLUMN_STATS = "apps_stats";


	//SQL string for creating table
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NAME + " TEXT NOT NULL, "
			+ COLUMN_STATS + " TEXT NOT NULL"
			+ ");";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}