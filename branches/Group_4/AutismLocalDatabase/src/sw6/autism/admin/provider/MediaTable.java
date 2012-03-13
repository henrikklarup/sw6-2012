package sw6.autism.admin.provider;

import android.database.sqlite.SQLiteDatabase;

public class MediaTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ MediaMetaData.MediaTable.TABLE_NAME
			+ "("
			+ MediaMetaData.MediaTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MediaMetaData.MediaTable.COLUMN_NAME + " TEXT NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + MediaMetaData.MediaTable.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
