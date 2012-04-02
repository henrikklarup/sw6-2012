package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class MediaTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ MediaMetaData.Table.TABLE_NAME
			+ "("
			+ MediaMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MediaMetaData.Table.COLUMN_NAME + " TEXT NOT NULL, "
			+ MediaMetaData.Table.COLUMN_PATH + " TEXT NOT NULL, "
			+ MediaMetaData.Table.COLUMN_PUBLIC + " INTEGER NOT NULL, "
			+ MediaMetaData.Table.COLUMN_TYPE + " TEXT NOT NULL, "
			+ MediaMetaData.Table.COLUMN_TAGS + " TEXT NOT NULL, "
			+ MediaMetaData.Table.COLUMN_OWNERID + " INTEGER NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + MediaMetaData.Table.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
