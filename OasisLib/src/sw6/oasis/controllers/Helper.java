package sw6.oasis.controllers;

import sw6.oasis.controllers.metadata.AppsMetaData;
import sw6.oasis.controllers.metadata.CertificatesMetaData;
import sw6.oasis.controllers.metadata.DepartmentsMetaData;
import sw6.oasis.controllers.metadata.ListOfAppsMetaData;
import sw6.oasis.controllers.metadata.MediaMetaData;
import sw6.oasis.controllers.metadata.ProfilesMetaData;
import sw6.oasis.viewmodels.App;
import sw6.oasis.viewmodels.Certificate;
import sw6.oasis.viewmodels.Department;
import sw6.oasis.viewmodels.ListOfApps;
import sw6.oasis.viewmodels.Media;
import sw6.oasis.viewmodels.Profile;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

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
//	public class ProfileHelper {
		
		/**
		 * Insert profile
		 * @param _profile Profile containing data
		 */
		public void insertProfile(Profile _profile) {
			ContentValues cv = new ContentValues();
			cv.put(ProfilesMetaData.Table.COLUMN_FIRST_NAME, _profile.getFirstname());
			cv.put(ProfilesMetaData.Table.COLUMN_SUR_NAME, _profile.getSurname());
			cv.put(ProfilesMetaData.Table.COLUMN_MIDDLE_NAME, _profile.getMiddlename());
			cv.put(ProfilesMetaData.Table.COLUMN_ROLE, _profile.getRole());
			cv.put(ProfilesMetaData.Table.COLUMN_PHONE, _profile.getPhone());
			cv.put(ProfilesMetaData.Table.COLUMN_PICTURE, _profile.getPicture());
			cv.put(ProfilesMetaData.Table.COLUMN_DEPARTMENTID, _profile.getDepartmentId());
			_context.getContentResolver().insert(ProfilesMetaData.CONTENT_URI, cv);
		}
		
		/**
		 * Modify profile
		 * @param _profile Profile containing data to modify
		 */
		public void modifyProfile(Profile _profile) {
			Uri uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, _profile.getId());
			ContentValues cv = new ContentValues();
			cv.put(ProfilesMetaData.Table.COLUMN_FIRST_NAME, _profile.getFirstname());
			cv.put(ProfilesMetaData.Table.COLUMN_SUR_NAME, _profile.getSurname());
			cv.put(ProfilesMetaData.Table.COLUMN_MIDDLE_NAME, _profile.getMiddlename());
			cv.put(ProfilesMetaData.Table.COLUMN_ROLE, _profile.getRole());
			cv.put(ProfilesMetaData.Table.COLUMN_PHONE, _profile.getPhone());
			cv.put(ProfilesMetaData.Table.COLUMN_PICTURE, _profile.getPicture());
			cv.put(ProfilesMetaData.Table.COLUMN_DEPARTMENTID, _profile.getDepartmentId());
			_context.getContentResolver().update(uri, cv, null, null);
		}
		
		/**
		 * Clear profiles table
		 */
		public void clearProfilesTable() {
			_context.getContentResolver().delete(ProfilesMetaData.CONTENT_URI, null, null);
		}
		
		/**
		 * Get all profiles
		 * @return Cursor, containing all profiles
		 */
		public Cursor getProfiles() {
			String[] columns = new String[] { ProfilesMetaData.Table.COLUMN_ID, 
											  ProfilesMetaData.Table.COLUMN_FIRST_NAME,
											  ProfilesMetaData.Table.COLUMN_SUR_NAME,
											  ProfilesMetaData.Table.COLUMN_MIDDLE_NAME,
											  ProfilesMetaData.Table.COLUMN_ROLE,
											  ProfilesMetaData.Table.COLUMN_PHONE,
											  ProfilesMetaData.Table.COLUMN_PICTURE,
											  ProfilesMetaData.Table.COLUMN_DEPARTMENTID};
			Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, null, null, null);

			return c;
		}
//	}

	/**
	 * Application class
	 * @author Admin
	 *
	 */
//	public class AppHelper {
		
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
		 * Get all applications
		 * @return Cursor, containing all applications
		 */
		public Cursor getApps() {
			String[] columns = new String[] { AppsMetaData.Table.COLUMN_ID, 
											  AppsMetaData.Table.COLUMN_NAME,
											  AppsMetaData.Table.COLUMN_VERSIONNUMBER};
			Cursor c = _context.getContentResolver().query(AppsMetaData.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear applications table
		 */
		public void clearAppsTable() {
			_context.getContentResolver().delete(AppsMetaData.CONTENT_URI, null, null);
		}
//	}

	/**
	 * Department class
	 * @author Admin
	 *
	 */
//	public class DepartmentHelper {
		
		/**
		 * Insert department
		 * @param _department Department containg data
		 */
		public void insertDepartment(Department _department) {
			ContentValues cv = new ContentValues();
			cv.put(DepartmentsMetaData.Table.COLUMN_NAME, _department.getName());
			cv.put(DepartmentsMetaData.Table.COLUMN_ADDRESS, _department.getAddress());
			cv.put(DepartmentsMetaData.Table.COLUMN_PHONE, _department.getPhone());
			_context.getContentResolver().insert(DepartmentsMetaData.CONTENT_URI, cv);
		}
		
		/**
		 * Modify department
		 * @param _department Department containing data to modify
		 */
		public void modifyDepartment(Department _department) {
			Uri uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, _department.getId());
			ContentValues cv = new ContentValues();
			cv.put(DepartmentsMetaData.Table.COLUMN_NAME, _department.getName());
			cv.put(DepartmentsMetaData.Table.COLUMN_ADDRESS, _department.getAddress());
			cv.put(DepartmentsMetaData.Table.COLUMN_PHONE, _department.getPhone());
			_context.getContentResolver().update(uri, cv, null, null);
		}
		
		/**
		 * Get all departments
		 * @return Cursor, containing all departments
		 */
		public Cursor getDepartments() {
			String[] columns = new String[] { DepartmentsMetaData.Table.COLUMN_ID, 
											  DepartmentsMetaData.Table.COLUMN_NAME,
											  DepartmentsMetaData.Table.COLUMN_PHONE,
											  DepartmentsMetaData.Table.COLUMN_ADDRESS};
			Cursor c = _context.getContentResolver().query(DepartmentsMetaData.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear department table
		 */
		public void clearDepartmentsTable() {
			_context.getContentResolver().delete(DepartmentsMetaData.CONTENT_URI, null, null);
		}
//	}

	/**
	 * Media class
	 * @author Admin
	 *
	 */
//	public class MediaHelper {
		
		/**
		 * Insert media
		 * @param _media Media containing data
		 */
		public void insertMedia(Media _media) {
			ContentValues cv = new ContentValues();
			cv.put(MediaMetaData.Table.COLUMN_PATH, _media.getPath());
			cv.put(MediaMetaData.Table.COLUMN_NAME, _media.getName());
			cv.put(MediaMetaData.Table.COLUMN_PUBLIC, _media.is_public());
			cv.put(MediaMetaData.Table.COLUMN_TYPE, _media.getType());
			cv.put(MediaMetaData.Table.COLUMN_TAGS, _media.getTags());
			cv.put(MediaMetaData.Table.COLUMN_OWNERID, _media.getOwnerId());
			_context.getContentResolver().insert(MediaMetaData.CONTENT_URI, cv);
		}
		
		/**
		 * Modify media
		 * @param _media Media containing data to modify
		 */
		public void modifyMedia(Media _media) {
			Uri uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, _media.getId());
			ContentValues cv = new ContentValues();
			cv.put(MediaMetaData.Table.COLUMN_PATH, _media.getPath());
			cv.put(MediaMetaData.Table.COLUMN_NAME, _media.getName());
			cv.put(MediaMetaData.Table.COLUMN_PUBLIC, _media.is_public());
			cv.put(MediaMetaData.Table.COLUMN_TYPE, _media.getType());
			cv.put(MediaMetaData.Table.COLUMN_TAGS, _media.getTags());
			cv.put(MediaMetaData.Table.COLUMN_OWNERID, _media.getOwnerId());
			_context.getContentResolver().update(uri, cv, null, null);
		}
		
		/**
		 * Get all media
		 * @return Cursor, containing all media
		 */
		public Cursor getMedia() {
			String[] columns = new String[] { MediaMetaData.Table.COLUMN_ID, 
											  MediaMetaData.Table.COLUMN_PATH,
											  MediaMetaData.Table.COLUMN_NAME,
											  MediaMetaData.Table.COLUMN_PUBLIC,
											  MediaMetaData.Table.COLUMN_TYPE,
											  MediaMetaData.Table.COLUMN_TAGS,
											  MediaMetaData.Table.COLUMN_OWNERID};
			Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear media table
		 */
		public void clearMediaTable() {
			_context.getContentResolver().delete(MediaMetaData.CONTENT_URI, null, null);
		}
//	}
	
	/**
	 * List of apps class
	 * @author Admin
	 *
	 */
//	public class ListOfAppsHelper {
		
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
		 * @return Cursor, containing all media
		 */
		public Cursor getListOfApps() {
			String[] columns = new String[] { ListOfAppsMetaData.Table.COLUMN_ID, 
											  ListOfAppsMetaData.Table.COLUMN_APPSID,
											  ListOfAppsMetaData.Table.COLUMN_PROFILESID,
											  ListOfAppsMetaData.Table.COLUMN_SETTINGS,
											  ListOfAppsMetaData.Table.COLUMN_STATS};
			Cursor c = _context.getContentResolver().query(ListOfAppsMetaData.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear list of apps table
		 */
		public void clearListOfAppsTable() {
			_context.getContentResolver().delete(ListOfAppsMetaData.CONTENT_URI, null, null);
		}
//	}
	
	
	/**
	 * List of apps class
	 * @author Admin
	 *
	 */
//	public class CertificatesHelper {
		
		/**
		 * Insert certificate
		 * @param _certificates Certificate containing data
		 */
		public void insertCertificate(Certificate _certificates) {
			ContentValues cv = new ContentValues();
			cv.put(CertificatesMetaData.Table.COLUMN_AUTHKEY, _certificates.getAuthkey());
			_context.getContentResolver().insert(CertificatesMetaData.CONTENT_URI, cv);
		}
		
		/**
		 * Modify Certificate
		 * @param _certificates Certificate containing data to modify
		 */
		public void modifyCertificate(Certificate _certificates) {
			Uri uri = ContentUris.withAppendedId(CertificatesMetaData.CONTENT_URI, _certificates.getId());
			ContentValues cv = new ContentValues();
			cv.put(CertificatesMetaData.Table.COLUMN_AUTHKEY, _certificates.getAuthkey());
			_context.getContentResolver().update(uri, cv, null, null);
		}
		
		/**
		 * Get all certificates
		 * @return Cursor, containing all certificates
		 */
		public Cursor getCertificates() {
			String[] columns = new String[] { CertificatesMetaData.Table.COLUMN_ID, 
											  CertificatesMetaData.Table.COLUMN_AUTHKEY};
			Cursor c = _context.getContentResolver().query(CertificatesMetaData.CONTENT_URI, columns, null, null, null);

			return c;
		}
		
		/**
		 * Clear certificates table
		 */
		public void clearCertificateTable() {
			_context.getContentResolver().delete(CertificatesMetaData.CONTENT_URI, null, null);
		}
//	}
	
}
