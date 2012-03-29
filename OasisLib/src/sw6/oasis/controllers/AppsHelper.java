package sw6.oasis.controllers;

import java.util.ArrayList;
import java.util.List;

import sw6.oasis.controllers.metadata.AppsMetaData;
import sw6.oasis.models.App;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Helper class for Apps
 * @author Admin
 *
 */
public class AppsHelper {

	private static Context _context;
	/**
	 * Constructor
	 * @param context Current context
	 */
	public AppsHelper(Context context){
		_context = context;
	}

	/**
	 * Insert app
	 * @param _app Application containing data
	 */
	public void insertApp(App _app) {
		ContentValues cv = new ContentValues();
		cv.put(AppsMetaData.Table.COLUMN_NAME, _app.getName());
		cv.put(AppsMetaData.Table.COLUMN_VERSIONNUMBER, _app.getVersionNumber());
		_context.getContentResolver().insert(AppsMetaData.CONTENT_URI, cv);
	}

	/**
	 * Modify app method
	 * @param _app Application containing data to modify
	 */
	public void modifyApp(App _app) {
		Uri uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, _app.getId());
		ContentValues cv = new ContentValues();
		cv.put(AppsMetaData.Table.COLUMN_NAME, _app.getName());
		cv.put(AppsMetaData.Table.COLUMN_VERSIONNUMBER, _app.getVersionNumber());
		_context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Get one application
	 * @param _id the id of the desired application
	 * @return null or the application
	 */
	public App getAppById(long _id) {
		Uri uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, _id);
		String[] columns = new String[] { AppsMetaData.Table.COLUMN_ID, 
				AppsMetaData.Table.COLUMN_NAME,
				AppsMetaData.Table.COLUMN_VERSIONNUMBER};
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);
		
		if(c.moveToFirst()) {
			return cursorToApp(c);
		}
		
		return null;
	}
	
	
	/**
	 * Gets all application with the specified name
	 * @param _name the name of the application
	 * @return a list of applications
	 */
	public List<App> getAppsByName(String _name) {
		List<App> apps = new ArrayList<App>();
		String[] columns = new String[] { AppsMetaData.Table.COLUMN_ID, 
				AppsMetaData.Table.COLUMN_NAME,
				AppsMetaData.Table.COLUMN_VERSIONNUMBER};
		Cursor c = _context.getContentResolver().query(Uri.withAppendedPath(AppsMetaData.CONTENT_URI, _name) , columns, null, null, null);
		
		apps = cursorToAppList(c);
		c.close();
		
		return apps; 
	}
	
	/**
	 * Get all applications
	 * @return List<App>, containing all applications
	 */
	public List<App> getApps() {
		List<App> apps = new ArrayList<App>();
		String[] columns = new String[] { AppsMetaData.Table.COLUMN_ID, 
				AppsMetaData.Table.COLUMN_NAME,
				AppsMetaData.Table.COLUMN_VERSIONNUMBER};
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, null, null, null);

		apps = cursorToAppList(c);
		c.close();

		return apps;
	}

	/**
	 * Clear applications table
	 */
	public void clearAppsTable() {
		_context.getContentResolver().delete(AppsMetaData.CONTENT_URI, null, null);
	}

	/**
	 * Cursor to app
	 * @param cursor the cursor to convert
	 * @return the app
	 */
	private App cursorToApp(Cursor cursor) {
		App app = new App();
		app.setId(cursor.getLong(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_ID)));
		app.setName(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_NAME)));
		app.setVersionNumber(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_VERSIONNUMBER)));
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
}
