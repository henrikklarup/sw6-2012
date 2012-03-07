package sw6.autism.admin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public static final String DATABASE_TABLE = "userTable";
	public static final String KEY_COLUMNID = "_id";
	public static final String KEY_NAME = "username";
	public static final String KEY_PASSWORD = "password";

	private static final String DATABASE_NAME = "userDB";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE 
			+ " (" 
			+ KEY_COLUMNID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ KEY_NAME 	+ " TEXT NOT NULL, " 
			+ KEY_PASSWORD 	+ " TEXT NOT NULL" 
			+ ");";
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}
	
	
}
