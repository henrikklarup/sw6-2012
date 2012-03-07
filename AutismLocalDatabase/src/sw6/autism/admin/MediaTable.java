package sw6.autism.admin;

import android.database.sqlite.SQLiteDatabase;

public class MediaTable {

	//Table columns
	public static final String TABLE_NAME = "media_table";
	public static final String COLUMN_ID = "media_id";
	public static final String COLUMN_PATH = "media_path";
	public static final String COLUMN_NAME = "media_name";
	public static final String COLUMN_PUBLIC = "media_public";
	public static final String COLUMN_TYPE = "media_type";
	public static final String COLUMN_TAG = "media_tag";


	//SQL string for creating table
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_PATH + " TEXT NOT NULL, "
			+ COLUMN_NAME + " TEXT NOT NULL, "
			+ COLUMN_PUBLIC + " INTEGER NOT NULL, "
			+ COLUMN_TYPE + " TEXT NOT NULL, "
			+ COLUMN_TAG + " TEXT NOT NULL"
			+ ");";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}