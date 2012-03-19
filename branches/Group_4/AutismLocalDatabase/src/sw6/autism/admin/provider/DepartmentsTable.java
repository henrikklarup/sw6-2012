package sw6.autism.admin.provider;

import android.database.sqlite.SQLiteDatabase;

public class DepartmentsTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ DepartmentsMetaData.Table.TABLE_NAME
			+ "("
			+ DepartmentsMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ DepartmentsMetaData.Table.COLUMN_NAME + " TEXT NOT NULL, "
			+ DepartmentsMetaData.Table.COLUMN_ADDRESS + " TEXT, "
			+ DepartmentsMetaData.Table.COLUMN_PHONE + " INTEGER"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + DepartmentsMetaData.Table.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
