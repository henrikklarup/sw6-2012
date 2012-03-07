package sw6.autism.admin;

import android.database.sqlite.SQLiteDatabase;

public class ProfileTable {

	//Table columns
	public static final String TABLE_NAME = "profile_table";
	public static final String COLUMN_ID = "profile_id";
	public static final String COLUMN_FIRST_NAME = "profile_first_name";
	public static final String COLUMN_LAST_NAME = "profile_last_name";
	public static final String COLUMN_MIDDLE_NAME = "profile_middle_name";
	public static final String COLUMN_ROLE = "profile_role";
	public static final String COLUMN_PHONE = "profile_phone_number";
	public static final String COLUMN_PICTURE = "profile_picture";
	public static final String COLUMN_DEPARTMENT = "profile_department";


	//SQL string for creating table
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_FIRST_NAME + " TEXT NOT NULL, "
			+ COLUMN_LAST_NAME + " TEXT NOT NULL, "
			+ COLUMN_MIDDLE_NAME + " TEXT, "
			+ COLUMN_ROLE + " INTEGER, "
			+ COLUMN_PHONE + " INTEGER NOT NULL, "
			+ COLUMN_PICTURE + " TEXT NOT NULL, "
			+ COLUMN_DEPARTMENT + " TEXT NOT NULL"
			+ ");";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
