package sw6.autism.admin.provider;

import android.database.sqlite.SQLiteDatabase;

public class ProfilesTable {
	
	private static final String TABLE_CREATE = "CREATE TABLE "
			+ ProfilesMetaData.Table.TABLE_NAME
			+ "("
			+ ProfilesMetaData.Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ProfilesMetaData.Table.COLUMN_FIRST_NAME + " TEXT NOT NULL, "
			+ ProfilesMetaData.Table.COLUMN_SUR_NAME + " TEXT NOT NULL, "
			+ ProfilesMetaData.Table.COLUMN_MIDDLE_NAME + " TEXT, "
			+ ProfilesMetaData.Table.COLUMN_ROLE + " INTEGER NOT NULL, "
			+ ProfilesMetaData.Table.COLUMN_PHONE + " INTEGER, "
			+ ProfilesMetaData.Table.COLUMN_PICTURE + " TEXT, "
			+ ProfilesMetaData.Table.COLUMN_DEPARTMENTID + " INTEGER NOT NULL"
			+ ");";
	
	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + ProfilesMetaData.Table.TABLE_NAME + ";";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
