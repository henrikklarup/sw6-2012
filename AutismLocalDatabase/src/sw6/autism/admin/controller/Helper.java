package sw6.autism.admin.controller;

import sw6.autism.admin.provider.AppsMetaData;
import sw6.autism.admin.provider.DepartmentsMetaData;
import sw6.autism.admin.provider.MediaMetaData;
import sw6.autism.admin.provider.ProfilesMetaData;
import sw6.autism.admin.provider.SettingsMetaData;
import sw6.autism.admin.provider.StatsMetaData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class Helper {

	private static Context _context;

	public Helper(Context context) {
		_context  = context;
	}

	//Clear Methods
	public void clearAppsTable() {
		_context.getContentResolver().delete(AppsMetaData.CONTENT_URI, null, null);
	}

	public void clearDepartmentsTable() {
		_context.getContentResolver().delete(DepartmentsMetaData.CONTENT_URI, null, null);
	}

	public void clearMediaTable() {
		_context.getContentResolver().delete(MediaMetaData.CONTENT_URI, null, null);
	}

	public void clearProfilesTable() {
		_context.getContentResolver().delete(ProfilesMetaData.CONTENT_URI, null, null);
	}

	public void clearSettingsTable() {
		_context.getContentResolver().delete(SettingsMetaData.CONTENT_URI, null, null);
	}

	public void clearStatsTable() {
		_context.getContentResolver().delete(StatsMetaData.CONTENT_URI, null, null);
	}

	//Modify Methods
	public void modifyApp(long id, String appName) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(AppsMetaData.AppsTable.COLUMN_NAME, appName);
		_context.getContentResolver().update(Uri.withAppendedPath(AppsMetaData.CONTENT_URI, _id), cv, null, null);
	}

	public void modifyDepartment(long id, String departmentName, int departmentPhone) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(DepartmentsMetaData.DepartmentsTable.COLUMN_NAME, departmentName);
		cv.put(DepartmentsMetaData.DepartmentsTable.COLUMN_PHONE, departmentPhone);
		_context.getContentResolver().update(Uri.withAppendedPath(DepartmentsMetaData.CONTENT_URI, _id), cv, null, null);
	}

	public void modifyMedia(long id, String mediaName) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(MediaMetaData.MediaTable.COLUMN_NAME, mediaName);
		_context.getContentResolver().update(Uri.withAppendedPath(MediaMetaData.CONTENT_URI, _id), cv, null, null);
	}

	public void modifyProfile(long id, String profileName, int profileRole) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(ProfilesMetaData.ProfilesTable.COLUMN_FIRST_NAME, profileName);
		cv.put(ProfilesMetaData.ProfilesTable.COLUMN_ROLE, profileRole);
		_context.getContentResolver().update(Uri.withAppendedPath(ProfilesMetaData.CONTENT_URI, _id), cv, null, null);
	}

	public void modifySetting(long id, String settingName, String settingOwner) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(SettingsMetaData.SettingsTable.COLUMN_SETTINGS, settingName);
		cv.put(SettingsMetaData.SettingsTable.COLUMN_OWNER, settingOwner);
		_context.getContentResolver().update(Uri.withAppendedPath(SettingsMetaData.CONTENT_URI, _id), cv, null, null);
	}

	public void modifyStat(long id, String statName, String statOwner) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(StatsMetaData.StatsTable.COLUMN_STATS, statName);
		cv.put(StatsMetaData.StatsTable.COLUMN_OWNER, statOwner);
		_context.getContentResolver().update(Uri.withAppendedPath(StatsMetaData.CONTENT_URI, _id), cv, null, null);
	}

	//Insert Methods
	public void insertApp(String appName) {
		ContentValues cv = new ContentValues();
		cv.put(AppsMetaData.AppsTable.COLUMN_NAME, appName);
		_context.getContentResolver().insert(AppsMetaData.CONTENT_URI, cv);
	}

	public void insertDepartment(String departmentName, int departmentPhone) {
		ContentValues cv = new ContentValues();
		cv.put(DepartmentsMetaData.DepartmentsTable.COLUMN_NAME, departmentName);
		cv.put(DepartmentsMetaData.DepartmentsTable.COLUMN_PHONE, departmentPhone);
		_context.getContentResolver().insert(DepartmentsMetaData.CONTENT_URI, cv);
	}

	public void insertMedia(String mediaName) {
		ContentValues cv = new ContentValues();
		cv.put(MediaMetaData.MediaTable.COLUMN_NAME, mediaName);
		_context.getContentResolver().insert(MediaMetaData.CONTENT_URI, cv);
	}

	public void insertProfile(String profileName, int profileRole) {
		ContentValues cv = new ContentValues();
		cv.put(ProfilesMetaData.ProfilesTable.COLUMN_FIRST_NAME, profileName);
		cv.put(ProfilesMetaData.ProfilesTable.COLUMN_ROLE, profileRole);
		_context.getContentResolver().insert(ProfilesMetaData.CONTENT_URI, cv);
	}

	public void insertSetting(String settingName, String settingOwner) {
		ContentValues cv = new ContentValues();
		cv.put(SettingsMetaData.SettingsTable.COLUMN_SETTINGS, settingName);
		cv.put(SettingsMetaData.SettingsTable.COLUMN_OWNER, settingOwner);
		_context.getContentResolver().insert(SettingsMetaData.CONTENT_URI, cv);
	}

	public void insertStat(String statName, String statOwner) {
		ContentValues cv = new ContentValues();
		cv.put(StatsMetaData.StatsTable.COLUMN_STATS, statName);
		cv.put(StatsMetaData.StatsTable.COLUMN_OWNER, statOwner);
		_context.getContentResolver().insert(StatsMetaData.CONTENT_URI, cv);
	}

	//Get Methods
	public Cursor getApps() {
		String[] columns = new String[] { AppsMetaData.AppsTable.COLUMN_ID, 
										  AppsMetaData.AppsTable.COLUMN_NAME};
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, null, null, null);
		return c;
	}

	public Cursor getDepartments() {
		String[] columns = new String[] { DepartmentsMetaData.DepartmentsTable.COLUMN_ID, 
										  DepartmentsMetaData.DepartmentsTable.COLUMN_NAME,
										  DepartmentsMetaData.DepartmentsTable.COLUMN_PHONE};
		Cursor c = _context.getContentResolver().query(DepartmentsMetaData.CONTENT_URI, columns, null, null, null);
		return c;
	}

	public Cursor getMedia() {
		String[] columns = new String[] { MediaMetaData.MediaTable.COLUMN_ID, 
										  MediaMetaData.MediaTable.COLUMN_NAME};
		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, null, null, null);
		return c;
	}

	public Cursor getProfiles() {
		String[] columns = new String[] { ProfilesMetaData.ProfilesTable.COLUMN_ID, 
										  ProfilesMetaData.ProfilesTable.COLUMN_FIRST_NAME,
										  ProfilesMetaData.ProfilesTable.COLUMN_ROLE};
		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, null, null, null);
		return c;
	}

	public Cursor getSettings() {
		String[] columns = new String[] { SettingsMetaData.SettingsTable.COLUMN_ID, 
										  SettingsMetaData.SettingsTable.COLUMN_SETTINGS,
										  SettingsMetaData.SettingsTable.COLUMN_OWNER};
		Cursor c = _context.getContentResolver().query(SettingsMetaData.CONTENT_URI, columns, null, null, null);
		return c;
	}

	public Cursor getStats() {
		String[] columns = new String[] { StatsMetaData.StatsTable.COLUMN_ID, 
										  StatsMetaData.StatsTable.COLUMN_STATS,
										  StatsMetaData.StatsTable.COLUMN_OWNER};
		Cursor c = _context.getContentResolver().query(StatsMetaData.CONTENT_URI, columns, null, null, null);
		return c;
	}
}
