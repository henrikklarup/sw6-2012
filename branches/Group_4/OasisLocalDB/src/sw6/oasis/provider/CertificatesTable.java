package sw6.oasis.provider;

import android.database.sqlite.SQLiteDatabase;

public class CertificatesTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ CertificatesMetaData.Table.TABLE_NAME
			+ "("
			+ CertificatesMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CertificatesMetaData.Table.COLUMN_AUTHKEY + " TEXT NOT NULL"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + CertificatesMetaData.Table.TABLE_NAME + ";";

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
