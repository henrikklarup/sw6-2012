package sw6.autism.admin.provider;

import android.database.sqlite.SQLiteDatabase;

public class ProfilesTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ ProfilesMetaData.ProfilesTable.TABLE_NAME
			+ "("
			+ ProfilesMetaData.ProfilesTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ProfilesMetaData.ProfilesTable.COLUMN_FIRST_NAME + " TEXT NOT NULL, "
			+ ProfilesMetaData.ProfilesTable.COLUMN_ROLE + " INTEGER"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + ProfilesMetaData.ProfilesTable.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
