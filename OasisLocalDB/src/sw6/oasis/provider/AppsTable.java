package sw6.oasis.provider;

import android.database.sqlite.SQLiteDatabase;

public class AppsTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ AppsMetaData.Table.TABLE_NAME
			+ "("
			+ AppsMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ AppsMetaData.Table.COLUMN_NAME + " TEXT NOT NULL, "
			+ AppsMetaData.Table.COLUMN_VERSIONNUMBER + " TEXT NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + AppsMetaData.Table.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
