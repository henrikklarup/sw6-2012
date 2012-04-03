package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class AuthUsersTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ AuthUsersMetaData.Table.TABLE_NAME
			+ "("
			+ AuthUsersMetaData.Table.COLUMN_ID + " INTEGER UNIQUE AUTOINCREMENT, "
			+ AuthUsersMetaData.Table.COLUMN_CERTIFICATE + " TEXT PRIMARY KEY"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + AuthUsersMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes sql string for creating certificate table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * executes a sql string which drops the old table and then the method calls oncreate, which create a new certificate table
	 * @param db this is a instance of a sqlite database
	 * @param oldVersion integer refering to the old version number
	 * @param newVersion integer refering to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
