package sw6.autism.admin;

import java.util.ArrayList;
import java.util.List;

import sw6.autism.admin.provider.AppsMetaData;
import sw6.autism.admin.provider.DepartmentsMetaData;
import sw6.autism.admin.provider.MediaMetaData;
import sw6.autism.admin.provider.ProfilesMetaData;
import sw6.autism.admin.provider.SettingsMetaData;
import sw6.autism.admin.provider.StatsMetaData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DataSource {

	private static Context _context;

	public DataSource(Context c) {
		_context = c;
	}

	public void clearDB() {
		_context.getContentResolver().delete(AppsMetaData.CONTENT_URI, null, null);
		_context.getContentResolver().delete(DepartmentsMetaData.CONTENT_URI, null, null);
		_context.getContentResolver().delete(MediaMetaData.CONTENT_URI, null, null);
		_context.getContentResolver().delete(ProfilesMetaData.CONTENT_URI, null, null);
		_context.getContentResolver().delete(SettingsMetaData.CONTENT_URI, null, null);
		_context.getContentResolver().delete(StatsMetaData.CONTENT_URI, null, null);
	}
	
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

	public void insertApp(String appName) {
		ContentValues cv = new ContentValues();
		cv.put(AppsMetaData.AppsTable.COLUMN_NAME, appName);
		_context.getContentResolver().insert(AppsMetaData.CONTENT_URI, cv);
	}

	public List<String> getAllApps() {
		List<String> apps = new ArrayList<String>();
		Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, new String[] {
				AppsMetaData.AppsTable.COLUMN_ID,
				AppsMetaData.AppsTable.COLUMN_NAME}, null, null, null);
		if (c.moveToFirst()) {
			while(!c.isAfterLast()) {
				apps.add("ID: " + c.getString(c.getColumnIndexOrThrow(AppsMetaData.AppsTable.COLUMN_ID))
						+ " Name: " + c.getString(c.getColumnIndexOrThrow(AppsMetaData.AppsTable.COLUMN_NAME)));
				c.moveToNext();
			}
		}
		return apps;
	}

	public void insertDepartment(String depName, int depPhone) {
		ContentValues cv = new ContentValues();
		cv.put(DepartmentsMetaData.DepartmentsTable.COLUMN_NAME, depName);
		cv.put(DepartmentsMetaData.DepartmentsTable.COLUMN_PHONE, depPhone);
		_context.getContentResolver().insert(DepartmentsMetaData.CONTENT_URI, cv);
	}

	public List<String> getAllDepartments() {
		List<String> deps = new ArrayList<String>();
		Cursor c = _context.getContentResolver().query(DepartmentsMetaData.CONTENT_URI, new String[] {
				DepartmentsMetaData.DepartmentsTable.COLUMN_ID,
				DepartmentsMetaData.DepartmentsTable.COLUMN_NAME,
				DepartmentsMetaData.DepartmentsTable.COLUMN_PHONE}, null, null, null);
		if (c.moveToFirst()) {
			while(!c.isAfterLast()) {
				deps.add("ID: " + c.getString(c.getColumnIndexOrThrow(DepartmentsMetaData.DepartmentsTable.COLUMN_ID))
						+ " Name: " + c.getString(c.getColumnIndexOrThrow(DepartmentsMetaData.DepartmentsTable.COLUMN_NAME))
						+ " Phone: " + c.getString(c.getColumnIndexOrThrow(DepartmentsMetaData.DepartmentsTable.COLUMN_PHONE)));
				c.moveToNext();
			}
		}
		return deps;
	}

	public void insertMedia(String mediaName) {
		ContentValues cv = new ContentValues();
		cv.put(MediaMetaData.MediaTable.COLUMN_NAME, mediaName);
		_context.getContentResolver().insert(MediaMetaData.CONTENT_URI, cv);
	}

	public List<String> getAllMedia() {
		List<String> media = new ArrayList<String>();
		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, new String[] {
				MediaMetaData.MediaTable.COLUMN_ID,
				MediaMetaData.MediaTable.COLUMN_NAME}, null, null, null);
		if (c.moveToFirst()) {
			while(!c.isAfterLast()) {
				media.add("ID: " + c.getString(c.getColumnIndexOrThrow(MediaMetaData.MediaTable.COLUMN_ID))
						+ " Name: " + c.getString(c.getColumnIndexOrThrow(MediaMetaData.MediaTable.COLUMN_NAME)));
				c.moveToNext();
			}
		}
		return media;
	}

	public void insertProfile(String name, int role) {
		ContentValues cv = new ContentValues();
		cv.put(ProfilesMetaData.ProfilesTable.COLUMN_FIRST_NAME, name);
		cv.put(ProfilesMetaData.ProfilesTable.COLUMN_ROLE, role);
		_context.getContentResolver().insert(ProfilesMetaData.CONTENT_URI, cv);
	}

	public List<String> getAllProfiles() {
		List<String> profiles = new ArrayList<String>();
		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, new String[] {
				ProfilesMetaData.ProfilesTable.COLUMN_ID,
				ProfilesMetaData.ProfilesTable.COLUMN_FIRST_NAME,
				ProfilesMetaData.ProfilesTable.COLUMN_ROLE}, null, null, null);
		if (c.moveToFirst()) {
			while(!c.isAfterLast()) {
				profiles.add("ID: " + c.getString(c.getColumnIndexOrThrow(ProfilesMetaData.ProfilesTable.COLUMN_ID))
						+ " Name: " + c.getString(c.getColumnIndexOrThrow(ProfilesMetaData.ProfilesTable.COLUMN_FIRST_NAME))
						+ " Role: " + c.getString(c.getColumnIndexOrThrow(ProfilesMetaData.ProfilesTable.COLUMN_ROLE)));
				c.moveToNext();
			}
		}
		return profiles;
	}

	public void insertSetting(String setting, String owner) {
		ContentValues cv = new ContentValues();
		cv.put(SettingsMetaData.SettingsTable.COLUMN_SETTINGS, setting);
		cv.put(SettingsMetaData.SettingsTable.COLUMN_OWNER, owner);
		_context.getContentResolver().insert(SettingsMetaData.CONTENT_URI, cv);
	}

	public List<String> getAllSettings() {
		List<String> settings = new ArrayList<String>();
		Cursor c = _context.getContentResolver().query(SettingsMetaData.CONTENT_URI, new String[] {
				SettingsMetaData.SettingsTable.COLUMN_ID,
				SettingsMetaData.SettingsTable.COLUMN_SETTINGS,
				SettingsMetaData.SettingsTable.COLUMN_OWNER}, null, null, null);
		if (c.moveToFirst()) {
			while(!c.isAfterLast()) {
				settings.add("ID: " + c.getString(c.getColumnIndexOrThrow(SettingsMetaData.SettingsTable.COLUMN_ID))
						+ " Setting: " + c.getString(c.getColumnIndexOrThrow(SettingsMetaData.SettingsTable.COLUMN_SETTINGS))
						+ " Owner: " + c.getString(c.getColumnIndexOrThrow(SettingsMetaData.SettingsTable.COLUMN_OWNER)));
				c.moveToNext();
			}
		}
		return settings;
	}

	public void insertStat(String stat, String owner) {
		ContentValues cv = new ContentValues();
		cv.put(StatsMetaData.StatsTable.COLUMN_STATS, stat);
		cv.put(StatsMetaData.StatsTable.COLUMN_OWNER, owner);
		_context.getContentResolver().insert(StatsMetaData.CONTENT_URI, cv);
	}

	public List<String> getAllStats() {
		List<String> stats = new ArrayList<String>();
		Cursor c = _context.getContentResolver().query(StatsMetaData.CONTENT_URI, new String[] {
				StatsMetaData.StatsTable.COLUMN_ID,
				StatsMetaData.StatsTable.COLUMN_STATS,
				StatsMetaData.StatsTable.COLUMN_OWNER}, null, null, null);
		if (c.moveToFirst()) {
			while(!c.isAfterLast()) {
				stats.add("ID: " + c.getString(c.getColumnIndexOrThrow(StatsMetaData.StatsTable.COLUMN_ID))
						+ " Stat: " + c.getString(c.getColumnIndexOrThrow(StatsMetaData.StatsTable.COLUMN_STATS))
						+ " Owner: " + c.getString(c.getColumnIndexOrThrow(StatsMetaData.StatsTable.COLUMN_ID)));
				c.moveToNext();
			}
		}
		return stats;
	}
}
