package sw6.autism.admin.provider;

import android.database.sqlite.SQLiteDatabase;

public class AppsTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ AppsMetaData.AppsTable.TABLE_NAME
			+ "("
			+ AppsMetaData.AppsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ AppsMetaData.AppsTable.COLUMN_NAME + " TEXT NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + AppsMetaData.AppsTable.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
