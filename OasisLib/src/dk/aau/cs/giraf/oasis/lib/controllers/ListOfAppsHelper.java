package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.ListOfAppsMetaData;
import dk.aau.cs.giraf.oasis.lib.models.ListOfApps;
import dk.aau.cs.giraf.oasis.lib.models.Setting;
import dk.aau.cs.giraf.oasis.lib.models.Stat;

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
	 * @param listOfApps List of apps containing data
	 */
	public void insertListOfApps(ListOfApps listOfApps) {
		ContentValues cv = getContentValues(listOfApps);
		_context.getContentResolver().insert(ListOfAppsMetaData.CONTENT_URI, cv);
	}

	/**
	 * Modify list of apps
	 * @param listOfApps List of apps containing data to modify
	 */
	public void modifyListOfApps(ListOfApps listOfApps) {
		ContentValues cv = getContentValues(listOfApps);
		_context.getContentResolver().update(ListOfAppsMetaData.CONTENT_URI, 
											 cv, null, 
											 new String[] {Long.toString(listOfApps.getIdApp()), 
														   Long.toString(listOfApps.getIdProfile())}
											);
	}

	/**
	 * Get all list of apps
	 * @return List<ListOfApps>, containing all media
	 */
	public List<ListOfApps> getListOfApps() {
		List<ListOfApps> listOfListOfApps =  new ArrayList<ListOfApps>();
		String[] columns = getTableColumns();
		Cursor c = _context.getContentResolver().query(ListOfAppsMetaData.CONTENT_URI, columns, null, null, null);

		listOfListOfApps = cursorToListOfListOfApps(c);

		c.close();

		return listOfListOfApps;
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
		loa.setIdApp(cursor.getLong(cursor.getColumnIndex(ListOfAppsMetaData.Table.COLUMN_IDAPP)));
		loa.setIdProfile(cursor.getLong(cursor.getColumnIndex(ListOfAppsMetaData.Table.COLUMN_IDPROFILE)));
		loa.setSettings(Setting.toObject(cursor.getString(cursor.getColumnIndex(ListOfAppsMetaData.Table.COLUMN_SETTINGS))));
		loa.setStats(Stat.toObject(cursor.getString(cursor.getColumnIndex(ListOfAppsMetaData.Table.COLUMN_STATS)).getBytes()));
		return loa;
	}
	
	private List<ListOfApps> cursorToListOfListOfApps(Cursor cursor) {
		List<ListOfApps> listOfListOfApps = new ArrayList<ListOfApps>();
		
		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				listOfListOfApps.add(cursorToListOfApps(cursor));
				cursor.moveToNext();
			}
		}
		
		return listOfListOfApps;
	}
	
	private ContentValues getContentValues(ListOfApps listOfApps) {
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(ListOfAppsMetaData.Table.COLUMN_IDAPP, listOfApps.getIdApp());
		contentValues.put(ListOfAppsMetaData.Table.COLUMN_IDPROFILE, listOfApps.getIdProfile());
		contentValues.put(ListOfAppsMetaData.Table.COLUMN_SETTINGS, Setting.toStringSetting(listOfApps.getSettings()));
		contentValues.put(ListOfAppsMetaData.Table.COLUMN_STATS, Stat.toByteArray(listOfApps.getStats()).toString());
		
		return contentValues;
	}
	
	private String[] getTableColumns() {
		String[] columns = new String[] { 
				ListOfAppsMetaData.Table.COLUMN_IDAPP,
				ListOfAppsMetaData.Table.COLUMN_IDPROFILE,
				ListOfAppsMetaData.Table.COLUMN_SETTINGS,
				ListOfAppsMetaData.Table.COLUMN_STATS};
		
		return columns;
	}
}
