package sw6.autism.admin.provider;

import android.database.sqlite.SQLiteDatabase;

public class ListOfAppsTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ ListOfAppsMetaData.Table.TABLE_NAME
			+ "("
			+ ListOfAppsMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ListOfAppsMetaData.Table.COLUMN_APPSID + " INTEGER NOT NULL, "
			+ ListOfAppsMetaData.Table.COLUMN_PROFILESID + " INTEGER NOT NULL, "
			+ ListOfAppsMetaData.Table.COLUMN_SETTINGS + " TEXT NOT NULL, "
			+ ListOfAppsMetaData.Table.COLUMN_STATS + " TEXT NOT NULL"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + ListOfAppsMetaData.Table.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
