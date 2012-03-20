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

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
