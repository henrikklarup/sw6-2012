package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.AppsMetaData;
import dk.aau.cs.giraf.oasis.lib.metadata.ListOfAppsMetaData;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Helper class for Apps
 * @author Admin
 *
 */
public class AppsHelper {

	private static Context _context;
	private String[] columns = new String[] { 
			AppsMetaData.Table.COLUMN_ID, 
			AppsMetaData.Table.COLUMN_NAME,
			AppsMetaData.Table.COLUMN_VERSION};

	/**
	 * Constructor
	 * @param context Current context
	 */
	public AppsHelper(Context context){
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Clear applications table
	 */
	public void clearAppsTable() {
		_context.getContentResolver().delete(AppsMetaData.CONTENT_URI, null, null);
	}

	/**
	 * Insert app
	 * @param app Application containing data
	 */
	public void insertApp(App app) {
		ContentValues cv = getContentValues(app);
		_context.getContentResolver().insert(AppsMetaData.CONTENT_URI, cv);
	}

	public int attachAppToProfile(App app, Profile profile) {
		ContentValues values = new ContentValues();
		values.put(ListOfAppsMetaData.Table.COLUMN_IDAPP, app.getId());
		values.put(ListOfAppsMetaData.Table.COLUMN_IDPROFILE, profile.getId());
		_context.getContentResolver().insert(ListOfAppsMetaData.CONTENT_URI, values);
		return 0;
	}

	/**
	 * Modify app method
	 * @param app Application containing data to modify
	 */
	public void modifyApp(App app) {
		Uri uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, app.getId());
		ContentValues cv = getContentValues(app);
		_context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Get all applications
	 * @return List<App>, containing all applications
	 */
	public List<App> getApps() {
		List<App> apps = new ArrayList<App>();
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, null, null, null);

		apps = cursorToAppList(c);
		c.close();

		return apps;
	}

	public List<App> getAppsByProfile(Profile profile) {
		List<App> apps = new ArrayList<App>();
		String[] ListOfAppsColums = {ListOfAppsMetaData.Table.COLUMN_IDAPP, ListOfAppsMetaData.Table.COLUMN_IDPROFILE}; 
		Cursor c = _context.getContentResolver().query(ListOfAppsMetaData.CONTENT_URI, ListOfAppsColums, ListOfAppsColums[0] + " = '" + profile.getId() + "'", null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					App app = getAppById(c.getLong(c.getColumnIndex(ListOfAppsColums[1])));
					apps.add(app);
					c.moveToNext();
				}
			}
		}

		c.close();

		return apps;
	}

	/**
	 * Get application by id
	 * @param id the id of the application
	 * @return the application or null
	 */
	public App getAppById(long id) {
		Uri uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, id);
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);
		App app = null;

		if(c.moveToFirst()) {
			app = cursorToApp(c); 
		}

		c.close();
		return app;
	}

	/**
	 * Gets all application with the specified name
	 * @param name the name of the application
	 * @return List<App>, containing all apps with the specific name
	 */
	public List<App> getAppsByName(String name) {
		List<App> apps = new ArrayList<App>();
		Cursor c = _context.getContentResolver().query(Uri.withAppendedPath(AppsMetaData.CONTENT_URI, name) , columns, null, null, null);

		apps = cursorToAppList(c);
		c.close();

		return apps; 
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - Keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to app
	 * @param cursor the cursor to convert
	 * @return the app
	 */
	private App cursorToApp(Cursor cursor) {
		App app = new App();
		app.setId(cursor.getLong(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_ID)));
		app.setName(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_NAME)));
		app.setVersion(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_VERSION)));
		return app;
	}

	/**
	 * Cursor to List<App>
	 * @param cursor the cursor to convert
	 * @return the List<App>
	 */
	private List<App> cursorToAppList(Cursor cursor) {
		List<App> apps = new ArrayList<App>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				apps.add(cursorToApp(cursor));
				cursor.moveToNext();
			}
		}

		return apps;
	}

	/**
	 * @param app the app to put in the database
	 * @return the contentValues
	 */
	private ContentValues getContentValues(App app) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(AppsMetaData.Table.COLUMN_NAME, app.getName());
		contentValues.put(AppsMetaData.Table.COLUMN_VERSION, app.getVersion());

		return contentValues;
	}
}
