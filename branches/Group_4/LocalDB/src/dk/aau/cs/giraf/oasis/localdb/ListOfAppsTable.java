package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class ListOfAppsTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ ListOfAppsMetaData.Table.TABLE_NAME
			+ "("
			+ ListOfAppsMetaData.Table.COLUMN_APPID + " INTEGER NOT NULL, "
			+ ListOfAppsMetaData.Table.COLUMN_PROFILEID + " INTEGER NOT NULL, "
			+ ListOfAppsMetaData.Table.COLUMN_SETTINGS + " TEXT, "
			+ ListOfAppsMetaData.Table.COLUMN_STATS + " TEXT, "
			+ "PRIMARY KEY(" + ListOfAppsMetaData.Table.COLUMN_APPID + ", " + ListOfAppsMetaData.Table.COLUMN_PROFILEID + ")"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + ListOfAppsMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes a sql string, which creates a new listofapps table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * Executes a sql string, which drop the current table, then the mehod calls the oncreate, which creates a new table
	 * @param db this is an instance of a sqlite database
	 * @param oldVersion integer refering to the old version number
	 * @param newVersion integer refering to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
