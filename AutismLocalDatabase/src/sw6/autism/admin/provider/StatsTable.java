package sw6.autism.admin.provider;

import android.database.sqlite.SQLiteDatabase;

public class StatsTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ StatsMetaData.StatsTable.TABLE_NAME
			+ "("
			+ StatsMetaData.StatsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ StatsMetaData.StatsTable.COLUMN_STATS + " TEXT NOT NULL, "
			+ StatsMetaData.StatsTable.COLUMN_OWNER + " TEXT NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + StatsMetaData.StatsTable.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
