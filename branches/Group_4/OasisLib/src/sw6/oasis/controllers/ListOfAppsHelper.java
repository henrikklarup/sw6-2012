package sw6.oasis.controllers;

import java.util.ArrayList;
import java.util.List;

import sw6.oasis.controllers.metadata.ListOfAppsMetaData;
import sw6.oasis.viewmodels.ListOfApps;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Helper class for List of Apps
 * @author Admin
 *
 */
public class ListOfAppsHelper {

	private static Context _context;
	/**
	 * Constructor
	 * @param context Current context
	 */
	public ListOfAppsHelper(Context context){
		_context = context;
	}

	/**
	 * Insert list of apps
	 * @param _listOfApps List of apps containing data
	 */
	public void insertListOfApps(ListOfApps _listOfApps) {
		ContentValues cv = new ContentValues();
		cv.put(ListOfAppsMetaData.Table.COLUMN_APPSID, _listOfApps.getAppId());
		cv.put(ListOfAppsMetaData.Table.COLUMN_PROFILESID, _listOfApps.getProfileId());
		cv.put(ListOfAppsMetaData.Table.COLUMN_SETTINGS, _listOfApps.getSettings());
		cv.put(ListOfAppsMetaData.Table.COLUMN_STATS, _listOfApps.getStats());
		_context.getContentResolver().insert(ListOfAppsMetaData.CONTENT_URI, cv);
	}

	/**
	 * Modify list of apps
	 * @param _listOfApps List of apps containing data to modify
	 */
	public void modifyListOfApps(ListOfApps _listOfApps) {
		Uri uri = ContentUris.withAppendedId(ListOfAppsMetaData.CONTENT_URI, _listOfApps.getId());
		ContentValues cv = new ContentValues();
		cv.put(ListOfAppsMetaData.Table.COLUMN_APPSID, _listOfApps.getAppId());
		cv.put(ListOfAppsMetaData.Table.COLUMN_PROFILESID, _listOfApps.getProfileId());
		cv.put(ListOfAppsMetaData.Table.COLUMN_SETTINGS, _listOfApps.getSettings());
		cv.put(ListOfAppsMetaData.Table.COLUMN_STATS, _listOfApps.getStats());
		_context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Get all list of apps
	 * @return List<ListOfApps>, containing all media
	 */
	public List<ListOfApps> getListOfApps() {
		List<ListOfApps> ListOfListOfApps =  new ArrayList<ListOfApps>();
		String[] columns = new String[] { ListOfAppsMetaData.Table.COLUMN_ID, 
				ListOfAppsMetaData.Table.COLUMN_APPSID,
				ListOfAppsMetaData.Table.COLUMN_PROFILESID,
				ListOfAppsMetaData.Table.COLUMN_SETTINGS,
				ListOfAppsMetaData.Table.COLUMN_STATS};
		Cursor c = _context.getContentResolver().query(ListOfAppsMetaData.CONTENT_URI, columns, null, null, null);

		if(c.moveToFirst()) {
			while(!c.isAfterLast()) {
				ListOfListOfApps.add(cursorToListOfApps(c));
				c.moveToNext();
			}
		}

		c.close();

		return ListOfListOfApps;
	}

	/**
	 * Clear list of apps table
	 */
	public void clearListOfAppsTable() {
		_context.getContentResolver().delete(ListOfAppsMetaData.CONTENT_URI, null, null);
	}

	/**
	 * Cursor to List of apps
	 * @param cursor Input cursor
	 * @return Output ListOfApps
	 */
	private ListOfApps cursorToListOfApps(Cursor cursor) {
		ListOfApps loa = new ListOfApps();
		loa.setId(cursor.getLong(cursor.getColumnIndex(ListOfAppsMetaData.Table.COLUMN_ID)));
		loa.setAppId(cursor.getLong(cursor.getColumnIndex(ListOfAppsMetaData.Table.COLUMN_APPSID)));
		loa.setProfileId(cursor.getLong(cursor.getColumnIndex(ListOfAppsMetaData.Table.COLUMN_PROFILESID)));
		loa.setSettings(cursor.getString(cursor.getColumnIndex(ListOfAppsMetaData.Table.COLUMN_SETTINGS)));
		loa.setStats(cursor.getString(cursor.getColumnIndex(ListOfAppsMetaData.Table.COLUMN_STATS)));
		return loa;
	}
}
