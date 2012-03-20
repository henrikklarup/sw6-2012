package sw6.oasis.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "autismDatabase.db";
	private static final int DATABASE_VERSION = 1;
	public static final String AUTHORITY = "sw6.oasis.provider.AutismProvider";

	public DbHelper(Context c) {
		super(c, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		ProfilesTable.onCreate(db);
		CertificatesTable.onCreate(db);
		AppsTable.onCreate(db);
		ListOfAppsTable.onCreate(db);
		MediaTable.onCreate(db);
		DepartmentsTable.onCreate(db);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		ProfilesTable.onUpgrade(db, oldVersion, newVersion);
		CertificatesTable.onUpgrade(db, oldVersion, newVersion);
		AppsTable.onUpgrade(db, oldVersion, newVersion);
		ListOfAppsTable.onUpgrade(db, oldVersion, newVersion);
		MediaTable.onUpgrade(db, oldVersion, newVersion);
		DepartmentsTable.onUpgrade(db, oldVersion, newVersion);
	}
}
