package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.AppsMetaData;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

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
	 * @param app Application containing data
	 */
	public void insertApp(App app) {
		ContentValues cv = getContentValues(app);
		getContext().getContentResolver().insert(AppsMetaData.CONTENT_URI, cv);
//		_context.getContentResolver().insert(AppsMetaData.CONTENT_URI, cv);
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

	public List<App> getAppsByProfile(Profile profile) {
		List<App> apps = new ArrayList<App>();
		
		
		return apps;
	}
	
	/**
	 * Get application by id
	 * @param id the id of the application
	 * @return the application or null
	 */
	public App getAppById(long id) {
		Uri uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, id);
		String[] columns = getTableColumns();
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
		String[] columns = getTableColumns();
		Cursor c = _context.getContentResolver().query(Uri.withAppendedPath(AppsMetaData.CONTENT_URI, name) , columns, null, null, null);

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
		String[] columns = getTableColumns();
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
	
	/**
	 * Getter for the content values
	 * @param app the app which values should be used
	 * @return the contentValues
	 */
	private ContentValues getContentValues(App app) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(AppsMetaData.Table.COLUMN_NAME, app.getName());
		contentValues.put(AppsMetaData.Table.COLUMN_VERSIONNUMBER, app.getVersion());
		
		return contentValues;
	}
	
	/**
	 * Getter for table columns
	 * @return the columns
	 */
	private String[] getTableColumns() {
		String[] columns = new String[] { 
				AppsMetaData.Table.COLUMN_ID, 
				AppsMetaData.Table.COLUMN_NAME,
				AppsMetaData.Table.COLUMN_VERSIONNUMBER};
		
		return columns;
	}
	
	/**
	 * Getter for the private context of this helper
	 * @return the context
	 */
	private Context getContext() {
		return _context;
	}
}
