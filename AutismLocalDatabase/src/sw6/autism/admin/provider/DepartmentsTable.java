package sw6.autism.admin.provider;

import android.database.sqlite.SQLiteDatabase;

public class DepartmentsTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ DepartmentsMetaData.DepartmentsTable.TABLE_NAME
			+ "("
			+ DepartmentsMetaData.DepartmentsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ DepartmentsMetaData.DepartmentsTable.COLUMN_NAME + " TEXT NOT NULL, "
			+ DepartmentsMetaData.DepartmentsTable.COLUMN_PHONE + " INTEGER NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + DepartmentsMetaData.DepartmentsTable.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
