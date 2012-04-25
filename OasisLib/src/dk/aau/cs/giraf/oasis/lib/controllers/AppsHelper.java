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
import dk.aau.cs.giraf.oasis.lib.models.ListOfApps;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Helper class for Apps
 * @author Admin
 *
 */
public class AppsHelper {

	private static Context _context;
	private ListOfAppsController loa;
	private String[] columns = new String[] { 
			AppsMetaData.Table.COLUMN_ID, 
			AppsMetaData.Table.COLUMN_NAME,
			AppsMetaData.Table.COLUMN_VERSION,
			AppsMetaData.Table.COLUMN_ICON,
			AppsMetaData.Table.COLUMN_PACKAGE,
			AppsMetaData.Table.COLUMN_ACTIVITY};

	/**
	 * Constructor
	 * @param context Current context
	 */
	public AppsHelper(Context context){
		_context = context;
		loa = new ListOfAppsController(context);
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
	
	public int removeAppAttachmentToProfile(App app, Profile profile) {
		return loa.removeListOfApps(app.getId(), profile.getId());
	}
	
	/**
	 * Insert app
	 * @param app Application containing data
	 * @return the app id
	 */
	public long insertApp(App app) {
		int result = 0;
		Uri uri;
		ContentValues cv = getContentValues(app);
		uri = _context.getContentResolver().insert(AppsMetaData.CONTENT_URI, cv);
		result = Integer.parseInt(uri.getPathSegments().get(1));
		
		return result;
	}

	public long attachAppToProfile(App app, Profile profile) {
		ListOfApps loaModel = new ListOfApps();
		loaModel.setIdApp(app.getId());
		loaModel.setIdProfile(profile.getId());
		loaModel.setSetting(app.getSettings());
		loaModel.setStat(app.getStats());
		return loa.insertListOfApps(loaModel);
	}

	/**
	 * Modify app
	 * @param app Application containing data to modify
	 */
	public int modifyApp(App app) {
		Uri uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, app.getId());
		ContentValues cv = getContentValues(app);
		return _context.getContentResolver().update(uri, cv, null, null);
	}
	
	/**
	 * Modify app by profile
	 * @param app the application containing the data to modify
	 * @param profile the profile the settings belong to
	 */
	public int modifyAppByProfile(App app, Profile profile) {
		modifyApp(app);
		
		ListOfApps listOfApps = new ListOfApps(app.getId(), profile.getId(), null, null);
		try {
			listOfApps.setSetting(app.getSettings());
		} catch (NullPointerException e) { }
		
		try {
			listOfApps.setStat(app.getStats());
		} catch (NullPointerException e) { }
		
		return loa.modifyListOfApps(listOfApps);
	}

	/**
	 * Get all applications
	 * @return List<App>, containing all applications
	 */
	public List<App> getApps() {
		List<App> apps = new ArrayList<App>();
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, null, null, null);
		
		if (c != null) {
			apps = cursorToAppList(c);
			c.close();
		}

		return apps;
	}

	/**
	 * Get application by id
	 * @param id the id of the application
	 * @return the application or null
	 */
	public App getAppById(long id) {
		App app = null;
		Uri uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, id);
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if(c.moveToFirst()) {
				app = cursorToApp(c); 
			}
			c.close();
		}
		return app;
	}
	
	public App getAppByIds(long appId, long profileId) {
		App app;
		
		ListOfApps listOfApps = loa.getListOfAppByIds(appId, profileId);
		app = getAppById(appId);
		app.setSettings(listOfApps.getSetting());
		app.setStats(listOfApps.getStat());
		
		return app;
	}

	/**
	 * Gets all application with the specified name
	 * @param name the name of the application
	 * @return List<App>, containing all apps with the specific name
	 */
	public List<App> getAppsByName(String name) {
		List<App> apps = new ArrayList<App>();
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, AppsMetaData.Table.COLUMN_NAME + " LIKE '%" + name + "%'", null, null);
		
		if (c != null) {
			apps = cursorToAppList(c);
			c.close();
		}

		return apps; 
	}
	
	public App getAppByPackageName(String packageName) {
		App app = null;
		
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, AppsMetaData.Table.COLUMN_PACKAGE + " = '" + packageName + "'", null, null);
		
		if (c != null) {
			if (c.moveToFirst()) {
				app = cursorToApp(c);
			}
			c.close();
		}
		
		return app;
	}
	
	public List<App> getAppsByProfile(Profile profile) {
		List<App> apps = new ArrayList<App>();
		List<ListOfApps> listOfApps = new ArrayList<ListOfApps>();

		listOfApps = loa.getListOfAppsByProfileId(profile.getId());

		for (ListOfApps item : listOfApps) {
			App app = getAppById(item.getIdApp());
			if (app != null) {
				app.setSettings(item.getSetting());
				app.setStats(item.getStat());
				apps.add(app);
			}
		}

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
		app.setIcon(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_ICON)));
		app.setaPackage(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_PACKAGE)));
		app.setActivity(cursor.getString(cursor.getColumnIndex(AppsMetaData.Table.COLUMN_ACTIVITY)));
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
		contentValues.put(AppsMetaData.Table.COLUMN_ICON, app.getIcon());
		contentValues.put(AppsMetaData.Table.COLUMN_PACKAGE, app.getaPackage());
		contentValues.put(AppsMetaData.Table.COLUMN_ACTIVITY, app.getActivity());

		return contentValues;
	}
}