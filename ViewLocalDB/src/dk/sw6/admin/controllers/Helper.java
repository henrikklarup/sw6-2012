package dk.sw6.admin.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.sw6.admin.viewmodels.App;
import dk.sw6.admin.viewmodels.Department;
import dk.sw6.admin.viewmodels.Media;
import dk.sw6.admin.viewmodels.Profile;
import dk.sw6.admin.viewmodels.Setting;
import dk.sw6.admin.viewmodels.Stat;

/**
 * Helper class, containing all functions you are ever gonna need!! xD
 * @author Admin
 *
 */
public class Helper {
	
	private static Context _context;
	/**
	 * Constructor
	 * @param context Current context
	 */
	public Helper(Context context){
		_context = context;
	}
	
	/**
	 * Profile class
	 * @author Admin
	 *
	 */
	public class ProfileHelper {
		
		/**
		 * Insert profile
		 * @param profileName Profile name
		 * @param profileRole Profile role, either autism or guardian
		 */
		public void insertProfile(String profileName, int profileRole) {
			ContentValues cv = new ContentValues();
			cv.put(Profile.ProfilesTable.COLUMN_FIRST_NAME, profileName);
			cv.put(Profile.ProfilesTable.COLUMN_ROLE, profileRole);
			_context.getContentResolver().insert(Profile.CONTENT_URI, cv);
		}
		
		/**
		 * Modify profile
		 * @param id Profile id in Database
		 * @param profileName New profile name for selected item
		 * @param profileRole New profile role for selected item, either autist or guardian
		 */
		public void modifyProfile(long id, String profileName, int profileRole) {
			String _id = String.valueOf(id);
			ContentValues cv = new ContentValues();
			cv.put(Profile.ProfilesTable.COLUMN_FIRST_NAME, profileName);
			cv.put(Profile.ProfilesTable.COLUMN_ROLE, profileRole);
			_context.getContentResolver().update(Uri.withAppendedPath(Profile.CONTENT_URI, _id), cv, null, null);
		}
		
		/**
		 * Clear profiles table
		 */
		public void clearProfilesTable() {
			_context.getContentResolver().delete(Profile.CONTENT_URI, null, null);
		}
		
		/**
		 * Get all profiles
		 * @return Cursor, containing all profiles
		 */
		public Cursor getProfiles() {
			String[] columns = new String[] { Profile.ProfilesTable.COLUMN_ID, 
											  Profile.ProfilesTable.COLUMN_FIRST_NAME,
											  Profile.ProfilesTable.COLUMN_ROLE};
			Cursor c = _context.getContentResolver().query(Profile.CONTENT_URI, columns, null, null, null);

			return c;
		}
	}

	/**
	 * Application class
	 * @author Admin
	 *
	 */
	public class AppHelper {
		
		/**
		 * Insert app
		 * @param appName Application name
		 */
		public void insertApp(String appName) {
			ContentValues cv = new ContentValues();
			cv.put(App.AppsTable.COLUMN_NAME, appName);
			_context.getContentResolver().insert(App.CONTENT_URI, cv);
		}
		
		/**
		 * Modify app method
		 * @param id Application id in Database
		 * @param appName New application name for selected item
		 */
		public void modifyApp(long id, String appName) {
			String _id = String.valueOf(id);
			ContentValues cv = new ContentValues();
			cv.put(App.AppsTable.COLUMN_NAME, appName);
			_context.getContentResolver().update(Uri.withAppendedPath(App.CONTENT_URI, _id), cv, null, null);
		}
		
		/**
		 * Get all applications
		 * @return Cursor, containing all applications
		 */
		public Cursor getApps() {
			String[] columns = new String[] { App.AppsTable.COLUMN_ID, 
											  App.AppsTable.COLUMN_NAME};
			Cursor c = _context.getContentResolver().query(App.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear applications table
		 */
		public void clearAppsTable() {
			_context.getContentResolver().delete(App.CONTENT_URI, null, null);
		}
	}

	/**
	 * Department class
	 * @author Admin
	 *
	 */
	public class DepartmentHelper {
		
		/**
		 * Insert department
		 * @param departmentName Department name
		 * @param departmentPhone Department phone number
		 */
		public void insertDepartment(String departmentName, int departmentPhone) {
			ContentValues cv = new ContentValues();
			cv.put(Department.DepartmentsTable.COLUMN_NAME, departmentName);
			cv.put(Department.DepartmentsTable.COLUMN_PHONE, departmentPhone);
			_context.getContentResolver().insert(Department.CONTENT_URI, cv);
		}
		
		/**
		 * Modify department
		 * @param id Department id in Database
		 * @param departmentName New department name for selected item
		 * @param departmentPhone New department phone number for selected item
		 */
		public void modifyDepartment(long id, String departmentName, int departmentPhone) {
			String _id = String.valueOf(id);
			ContentValues cv = new ContentValues();
			cv.put(Department.DepartmentsTable.COLUMN_NAME, departmentName);
			cv.put(Department.DepartmentsTable.COLUMN_PHONE, departmentPhone);
			_context.getContentResolver().update(Uri.withAppendedPath(Department.CONTENT_URI, _id), cv, null, null);
		}
		
		/**
		 * Get all departments
		 * @return Cursor, containing all departments
		 */
		public Cursor getDepartments() {
			String[] columns = new String[] { Department.DepartmentsTable.COLUMN_ID, 
											  Department.DepartmentsTable.COLUMN_NAME,
											  Department.DepartmentsTable.COLUMN_PHONE};
			Cursor c = _context.getContentResolver().query(Department.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear department table
		 */
		public void clearDepartmentsTable() {
			_context.getContentResolver().delete(Department.CONTENT_URI, null, null);
		}
	}

	/**
	 * Media class
	 * @author Admin
	 *
	 */
	public class MediaHelper {
		
		/**
		 * Insert media
		 * @param mediaName Media name
		 */
		public void insertMedia(String mediaName) {
			ContentValues cv = new ContentValues();
			cv.put(Media.MediaTable.COLUMN_NAME, mediaName);
			_context.getContentResolver().insert(Media.CONTENT_URI, cv);
		}
		
		/**
		 * Modify media
		 * @param id Media id in Database
		 * @param mediaName New media name for selected item
		 */
		public void modifyMedia(long id, String mediaName) {
			String _id = String.valueOf(id);
			ContentValues cv = new ContentValues();
			cv.put(Media.MediaTable.COLUMN_NAME, mediaName);
			_context.getContentResolver().update(Uri.withAppendedPath(Media.CONTENT_URI, _id), cv, null, null);
		}
		
		/**
		 * Get all media
		 * @return Cursor, containing all media
		 */
		public Cursor getMedia() {
			String[] columns = new String[] { Media.MediaTable.COLUMN_ID, 
											  Media.MediaTable.COLUMN_NAME};
			Cursor c = _context.getContentResolver().query(Media.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear media table
		 */
		public void clearMediaTable() {
			_context.getContentResolver().delete(Media.CONTENT_URI, null, null);
		}
	}
	
	
	/**
	 * Setting class
	 * @author Admin
	 *
	 */
	public class SettingHelper {
		
		/**
		 * Insert settings
		 * @param settingName Settings name
		 * @param settingOwner Settings owner
		 */
		public void insertSetting(String settingName, String settingOwner) {
			ContentValues cv = new ContentValues();
			cv.put(Setting.SettingsTable.COLUMN_SETTINGS, settingName);
			cv.put(Setting.SettingsTable.COLUMN_OWNER, settingOwner);
			_context.getContentResolver().insert(Setting.CONTENT_URI, cv);
		}
		
		/**
		 * Modify settings
		 * @param id Setting id in Database
		 * @param settingName New setting name for selected item
		 * @param settingOwner New setting owner for selected item
		 */
		public void modifySetting(long id, String settingName, String settingOwner) {
			String _id = String.valueOf(id);
			ContentValues cv = new ContentValues();
			cv.put(Setting.SettingsTable.COLUMN_SETTINGS, settingName);
			cv.put(Setting.SettingsTable.COLUMN_OWNER, settingOwner);
			_context.getContentResolver().update(Uri.withAppendedPath(Setting.CONTENT_URI, _id), cv, null, null);
		}
		
		/**
		 * Get all settings
		 * @return Cursor, containing all settings
		 */
		public Cursor getSettings() {
			String[] columns = new String[] { Setting.SettingsTable.COLUMN_ID, 
											  Setting.SettingsTable.COLUMN_SETTINGS,
											  Setting.SettingsTable.COLUMN_OWNER};
			Cursor c = _context.getContentResolver().query(Setting.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear settings table
		 */
		public void clearSettingsTable() {
			_context.getContentResolver().delete(Setting.CONTENT_URI, null, null);
		}
	}
	
	
	/**
	 * Stat class
	 * @author Admin
	 *
	 */
	public class StatHelper {
		
		/**
		 * Insert stat
		 * @param statName Stat name
		 * @param statOwner Stat owner
		 */
		public void insertStat(String statName, String statOwner) {
			ContentValues cv = new ContentValues();
			cv.put(Stat.StatsTable.COLUMN_STATS, statName);
			cv.put(Stat.StatsTable.COLUMN_OWNER, statOwner);
			_context.getContentResolver().insert(Stat.CONTENT_URI, cv);
		}
		
		/**
		 * Modify stat
		 * @param id Stat id in Database
		 * @param statName New stat name for selected item
		 * @param statOwner New stat owner for selected item
		 */
		public void modifyStat(long id, String statName, String statOwner) {
			String _id = String.valueOf(id);
			ContentValues cv = new ContentValues();
			cv.put(Stat.StatsTable.COLUMN_STATS, statName);
			cv.put(Stat.StatsTable.COLUMN_OWNER, statOwner);
			_context.getContentResolver().update(Uri.withAppendedPath(Stat.CONTENT_URI, _id), cv, null, null);
		}
		
		/**
		 * Get all stats
		 * @return Cursor, containing all stats
		 */
		public Cursor getStats() {
			String[] columns = new String[] { Stat.StatsTable.COLUMN_ID, 
											  Stat.StatsTable.COLUMN_STATS,
											  Stat.StatsTable.COLUMN_OWNER};
			Cursor c = _context.getContentResolver().query(Stat.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear stats table
		 */
		public void clearStatsTable() {
			_context.getContentResolver().delete(Stat.CONTENT_URI, null, null);
		}
	}
	
	
	
}
