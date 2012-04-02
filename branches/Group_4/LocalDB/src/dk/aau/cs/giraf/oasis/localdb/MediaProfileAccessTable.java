package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class MediaProfileAccessTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ MediaProfileAccessMetaData.Table.TABLE_NAME
			+ "("
			+ MediaProfileAccessMetaData.Table.COLUMN_MEDIAID + " INTEGER, "
			+ MediaProfileAccessMetaData.Table.COLUMN_PROFILEID + " INTEGER, "
			+ "PRIMARY KEY(" + MediaProfileAccessMetaData.Table.COLUMN_MEDIAID + ", " + MediaProfileAccessMetaData.Table.COLUMN_PROFILEID + ")"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + MediaProfileAccessMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes sql string for creating table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * executes a sql string which drops the old table and then the method call the oncreate, which creates a new table
	 * @param db this is an instance of a sqlite database
	 * @param oldVersion integer refering to the old version number
	 * @param newVersion integer refering to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
