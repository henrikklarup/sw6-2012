package sw6.autism.admin;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private String[] allColumns = { DbHelper.KEY_COLUMNID, DbHelper.KEY_NAME, DbHelper.KEY_PASSWORD};

	public DataSource(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public boolean createUser(String username, String password) {
		boolean result = false;
		User user;
		user = findUserByUsername(username);
		if (user == null) {
			ContentValues values = new ContentValues();
			values.put(DbHelper.KEY_NAME, username);
			values.put(DbHelper.KEY_PASSWORD, password);
			database.insert(DbHelper.DATABASE_TABLE, null, values);
			result = true;
		}
		return result;
	}

	public void deleteUser(User user) {
		long id = user.getId();
		database.delete(DbHelper.DATABASE_TABLE, DbHelper.KEY_COLUMNID + " = " + id, null);
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		Cursor cursor = database.query(DbHelper.DATABASE_TABLE, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = cursorToUser(cursor);
			users.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		return users;
	}

	public User findUserByUsername(String _username) {
		String _query = "SELECT _id, username, password FROM userTable WHERE username = '" + _username + "'";
		User user;
		Cursor cursor = database.rawQuery(_query, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			user = cursorToUser(cursor);
			return user;
		} else {
			user = null;
			return user;
		}
	}

	public User findUserById(long _id) {
		String _query = "SELECT _id, username, password FROM userTable WHERE _id = '" + _id + "'";
		User user;
		Cursor cursor = database.rawQuery(_query, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			user = cursorToUser(cursor);
			return user;
		} else {
			user = null;
			return user;
		}
	}

	public User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(0));
		user.setUsername(cursor.getString(1));
		user.setPassword(cursor.getString(2));
		return user;
	}
}
