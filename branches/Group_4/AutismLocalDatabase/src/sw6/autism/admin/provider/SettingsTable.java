package sw6.autism.admin.provider;

import android.database.sqlite.SQLiteDatabase;

public class SettingsTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ SettingsMetaData.SettingsTable.TABLE_NAME
			+ "("
			+ SettingsMetaData.SettingsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ SettingsMetaData.SettingsTable.COLUMN_SETTINGS + " TEXT NOT NULL, "
			+ SettingsMetaData.SettingsTable.COLUMN_OWNER + " TEXT NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + SettingsMetaData.SettingsTable.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
