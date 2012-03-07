package sw6.autism.admin;

import android.database.sqlite.SQLiteDatabase;

public class DepartmentTable {

	//Table columns
	public static final String TABLE_NAME = "department_table";
	public static final String COLUMN_ID = "department_id";
	public static final String COLUMN_NAME = "department_name";
	public static final String COLUMN_ADDRESS = "department_address";
	public static final String COLUMN_PHONE = "department_phone";
	public static final String COLUMN_EMAIL = "department_email";


	//SQL string for creating table
	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NAME + " TEXT NOT NULL, "
			+ COLUMN_ADDRESS + " TEXT NOT NULL, "
			+ COLUMN_PHONE + " INTEGER NOT NULL, "
			+ COLUMN_EMAIL + " INTEGER NOT NULL"
			+ ");";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}