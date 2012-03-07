package sw6.autism.admin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "autismDatabase.db";
	private static final int DATABASE_VERSION = 1;

	public DbHelper(Context c) {
		super(c, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		ProfileTable.onCreate(db);
		StatsTable.onCreate(db);
		AppsTable.onCreate(db);
		SettingsTable.onCreate(db);
		MediaTable.onCreate(db);
		DepartmentTable.onCreate(db);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		ProfileTable.onUpgrade(db, oldVersion, newVersion);
		StatsTable.onUpgrade(db, oldVersion, newVersion);
		AppsTable.onUpgrade(db, oldVersion, newVersion);
		SettingsTable.onUpgrade(db, oldVersion, newVersion);
		MediaTable.onUpgrade(db, oldVersion, newVersion);
		DepartmentTable.onUpgrade(db, oldVersion, newVersion);
	}
}
