package sw6.autism.admin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

	private SQLiteDatabase database;
	private DbHelper dbHelper;

	public DataSource(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	//ANDRE METODER SOM DE ANDRE SKAL BRUGE

}
