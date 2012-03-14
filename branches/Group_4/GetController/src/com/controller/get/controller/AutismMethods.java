package com.controller.get.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.controller.get.viewmodels.App;
import com.controller.get.viewmodels.Department;
import com.controller.get.viewmodels.Media;
import com.controller.get.viewmodels.Profile;
import com.controller.get.viewmodels.Setting;
import com.controller.get.viewmodels.Stat;

public class AutismMethods {
	
	private static Context _context;
	
	public AutismMethods(Context context) {
		_context  = context;
	}
	
	//Clear Methods
	public void clearAppsTable() {
		_context.getContentResolver().delete(App.CONTENT_URI, null, null);
	}
	
	public void clearDepartmentsTable() {
		_context.getContentResolver().delete(Department.CONTENT_URI, null, null);
	}
	
	public void clearMediaTable() {
		_context.getContentResolver().delete(Media.CONTENT_URI, null, null);
	}
	
	public void clearProfilesTable() {
		_context.getContentResolver().delete(Profile.CONTENT_URI, null, null);
	}
	
	public void clearSettingsTable() {
		_context.getContentResolver().delete(Setting.CONTENT_URI, null, null);
	}
	
	public void clearStatsTable() {
		_context.getContentResolver().delete(Stat.CONTENT_URI, null, null);
	}

	//Modify Methods
	public void modifyApp(long id, String appName) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(App.AppsTable.COLUMN_NAME, appName);
		_context.getContentResolver().update(Uri.withAppendedPath(App.CONTENT_URI, _id), cv, null, null);
	}
	
	public void modifyDepartment(long id, String departmentName, int departmentPhone) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(Department.DepartmentsTable.COLUMN_NAME, departmentName);
		cv.put(Department.DepartmentsTable.COLUMN_PHONE, departmentPhone);
		_context.getContentResolver().update(Uri.withAppendedPath(Department.CONTENT_URI, _id), cv, null, null);
	}
	
	public void modifyMedia(long id, String mediaName) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(Media.MediaTable.COLUMN_NAME, mediaName);
		_context.getContentResolver().update(Uri.withAppendedPath(Media.CONTENT_URI, _id), cv, null, null);
	}
	
	public void modifyProfile(long id, String profileName, int profileRole) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(Profile.ProfilesTable.COLUMN_FIRST_NAME, profileName);
		cv.put(Profile.ProfilesTable.COLUMN_ROLE, profileRole);
		_context.getContentResolver().update(Uri.withAppendedPath(Profile.CONTENT_URI, _id), cv, null, null);
	}
	
	public void modifySetting(long id, String settingName, String settingOwner) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(Setting.SettingsTable.COLUMN_SETTINGS, settingName);
		cv.put(Setting.SettingsTable.COLUMN_OWNER, settingOwner);
		_context.getContentResolver().update(Uri.withAppendedPath(Setting.CONTENT_URI, _id), cv, null, null);
	}
	
	public void modifyStat(long id, String statName, String statOwner) {
		String _id = String.valueOf(id);
		ContentValues cv = new ContentValues();
		cv.put(Stat.StatsTable.COLUMN_STATS, statName);
		cv.put(Stat.StatsTable.COLUMN_OWNER, statOwner);
		_context.getContentResolver().update(Uri.withAppendedPath(Stat.CONTENT_URI, _id), cv, null, null);
	}
	
	//Insert Methods
	public void insertApp(String appName) {
		ContentValues cv = new ContentValues();
		cv.put(App.AppsTable.COLUMN_NAME, appName);
		_context.getContentResolver().insert(App.CONTENT_URI, cv);
	}
	
	public void insertDepartment(String departmentName, int departmentPhone) {
		ContentValues cv = new ContentValues();
		cv.put(Department.DepartmentsTable.COLUMN_NAME, departmentName);
		cv.put(Department.DepartmentsTable.COLUMN_PHONE, departmentPhone);
		_context.getContentResolver().insert(Department.CONTENT_URI, cv);
	}
	
	public void insertMedia(String mediaName) {
		ContentValues cv = new ContentValues();
		cv.put(Media.MediaTable.COLUMN_NAME, mediaName);
		_context.getContentResolver().insert(Media.CONTENT_URI, cv);
	}
	
	public void insertProfile(String profileName, int profileRole) {
		ContentValues cv = new ContentValues();
		cv.put(Profile.ProfilesTable.COLUMN_FIRST_NAME, profileName);
		cv.put(Profile.ProfilesTable.COLUMN_ROLE, profileRole);
		_context.getContentResolver().insert(Profile.CONTENT_URI, cv);
	}
	
	public void insertSetting(String settingName, String settingOwner) {
		ContentValues cv = new ContentValues();
		cv.put(Setting.SettingsTable.COLUMN_SETTINGS, settingName);
		cv.put(Setting.SettingsTable.COLUMN_OWNER, settingOwner);
		_context.getContentResolver().insert(Setting.CONTENT_URI, cv);
	}
	
	public void insertStat(String statName, String statOwner) {
		ContentValues cv = new ContentValues();
		cv.put(Stat.StatsTable.COLUMN_STATS, statName);
		cv.put(Stat.StatsTable.COLUMN_OWNER, statOwner);
		_context.getContentResolver().insert(Stat.CONTENT_URI, cv);
	}
	
	//Get Methods
	public Cursor getApps() {
		String[] columns = new String[] { App.AppsTable.COLUMN_ID, 
										  App.AppsTable.COLUMN_NAME};
		Cursor c = _context.getContentResolver().query(App.CONTENT_URI, columns, null, null, null);

		return c;
	}
	
	public Cursor getDepartments() {
		String[] columns = new String[] { Department.DepartmentsTable.COLUMN_ID, 
										  Department.DepartmentsTable.COLUMN_NAME,
										  Department.DepartmentsTable.COLUMN_PHONE};
		Cursor c = _context.getContentResolver().query(Department.CONTENT_URI, columns, null, null, null);

		return c;
	}
	
	public Cursor getMedia() {
		String[] columns = new String[] { Media.MediaTable.COLUMN_ID, 
										  Media.MediaTable.COLUMN_NAME};
		Cursor c = _context.getContentResolver().query(Media.CONTENT_URI, columns, null, null, null);

		return c;
	}
	
	public Cursor getProfiles() {
		String[] columns = new String[] { Profile.ProfilesTable.COLUMN_ID, 
										  Profile.ProfilesTable.COLUMN_FIRST_NAME,
										  Profile.ProfilesTable.COLUMN_ROLE};
		Cursor c = _context.getContentResolver().query(Profile.CONTENT_URI, columns, null, null, null);

		return c;
	}
	
	public Cursor getSettings() {
		String[] columns = new String[] { Setting.SettingsTable.COLUMN_ID, 
										  Setting.SettingsTable.COLUMN_SETTINGS,
										  Setting.SettingsTable.COLUMN_OWNER};
		Cursor c = _context.getContentResolver().query(Setting.CONTENT_URI, columns, null, null, null);

		return c;
	}
	
	public Cursor getStats() {
		String[] columns = new String[] { Stat.StatsTable.COLUMN_ID, 
										  Stat.StatsTable.COLUMN_STATS,
										  Stat.StatsTable.COLUMN_OWNER};
		Cursor c = _context.getContentResolver().query(Stat.CONTENT_URI, columns, null, null, null);

		return c;
	}
}
