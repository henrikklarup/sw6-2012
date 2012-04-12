package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.AuthUsersMetaData;
import dk.aau.cs.giraf.oasis.lib.metadata.HasDepartmentMetaData;
import dk.aau.cs.giraf.oasis.lib.metadata.HasGuardianMetaData;
import dk.aau.cs.giraf.oasis.lib.metadata.ProfilesMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

/**
 * Helper class for Profiles 
 * @author Admin
 *
 */
public class ProfilesHelper {


	private static Context _context;
	/**
	 * Constructor
	 * @param context Current context
	 */
	public ProfilesHelper(Context context){
		_context = context;
	}

	/**
	 * Insert profile
	 * @param profile Profile containing data
	 */
	public int insertProfile(Profile profile) {
		String certificate = getNewCertificate();
		ContentValues authusersContentValues = new ContentValues();
		authusersContentValues.put(AuthUsersMetaData.Table.COLUMN_CERTIFICATE, certificate);
		authusersContentValues.put(AuthUsersMetaData.Table.COLUMN_ROLE, profile.getPRole());
		_context.getContentResolver().insert(AuthUsersMetaData.CONTENT_URI, authusersContentValues);

		String[] authColumns = new String[] { 
				AuthUsersMetaData.Table.COLUMN_ID, 
				AuthUsersMetaData.Table.COLUMN_CERTIFICATE,
				AuthUsersMetaData.Table.COLUMN_ROLE};
		Cursor c = _context.getContentResolver().query(AuthUsersMetaData.CONTENT_URI, authColumns, null, new String[] {certificate}, null);

		if (c != null) {
			if(c.moveToFirst()) {
				long id = c.getLong(c.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ID));
				profile.setId(id);

				ContentValues profileContentValues = getContentValues(profile);
				profileContentValues.put(ProfilesMetaData.Table.COLUMN_ID, id);
				_context.getContentResolver().insert(ProfilesMetaData.CONTENT_URI, profileContentValues);
				c.close();
				return (int)id;
			}
		}
		c.close();

		return -1;
	}

	/**
	 * Modify profile
	 * @param profile Profile containing data to modify
	 */
	public void modifyProfile(Profile profile) {
		Uri uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, profile.getId());
		ContentValues cv = getContentValues(profile);
		_context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Clear profiles table
	 */
	public void clearProfilesTable() {
		_context.getContentResolver().delete(ProfilesMetaData.CONTENT_URI, null, null);
	}

	/**
	 * Authenticates the profile
	 * @param certificate the certificate that authenticates the profile
	 * @return the authenticated profile or null
	 */
	public Profile authenticateProfile(String certificate) {
		Profile profile = null;

		String[] authColumns = new String[] { 
				AuthUsersMetaData.Table.COLUMN_ID, 
				AuthUsersMetaData.Table.COLUMN_CERTIFICATE,
				AuthUsersMetaData.Table.COLUMN_ROLE};
		Cursor c = _context.getContentResolver().query(AuthUsersMetaData.CONTENT_URI, authColumns, null, new String[] {certificate}, null);
		
		if(c != null) {
			if(c.moveToFirst()) {
				profile = getProfileById(c.getLong(c.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ID)));
			}
		}
		
		c.close();

		return profile;
	}
	
	/**
	 * Set a new certificate
	 * @param certificate the certificate to set
	 * @param profile the profile whom the certificate is updated for
	 * @return the number of row affected
	 */
	public int setCertificate(String certificate, Profile profile) {
		Uri uri = ContentUris.withAppendedId(AuthUsersMetaData.CONTENT_URI, profile.getId());
		
		ContentValues cv = new ContentValues();
		cv.put(AuthUsersMetaData.Table.COLUMN_CERTIFICATE, certificate);
		return _context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Retrieve the certificates for a profile
	 * @param profile
	 * @return List<String> or null
	 */
	public List<String> getCertificatesByProfile(Profile profile) {
		List<String> certificate = null;
		Uri uri = ContentUris.withAppendedId(AuthUsersMetaData.CONTENT_URI, profile.getId());

		String[] authColumns = new String[] {
				AuthUsersMetaData.Table.COLUMN_ID,
				AuthUsersMetaData.Table.COLUMN_CERTIFICATE,
				AuthUsersMetaData.Table.COLUMN_ROLE};
		Cursor c = _context.getContentResolver().query(uri, authColumns, null, null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				certificate = new ArrayList<String>();
				while (!c.isAfterLast()) {
					certificate.add(c.getString(c.getColumnIndex(AuthUsersMetaData.Table.COLUMN_CERTIFICATE)));
					c.moveToNext();
				}
			}
		}

		c.close();
		
		return certificate;
	}
	
	public List<Profile> getChildrenByDepartment(Department department) {
		List<Profile> profiles = new ArrayList<Profile>();
		String[] hasDepartmentColumns = {HasDepartmentMetaData.Table.COLUMN_IDPROFILE, HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT}; 
		Cursor c = _context.getContentResolver().query(HasDepartmentMetaData.CONTENT_URI, hasDepartmentColumns,	hasDepartmentColumns[1] + " = '" + department.getId() + "'", null, null);
		
		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					Profile profile = getProfileById(c.getLong(c.getColumnIndex(hasDepartmentColumns[0])));
					if (profile.getPRole() == 3) {
						profiles.add(profile);
					}
					c.moveToNext();
				}
			}
		}
		
		c.close();
		
		return profiles;
	}
	
	public List<Profile> getChildrenByGuardian(Profile guardian) {
		List<Profile> profiles = new ArrayList<Profile>();
		String[] hasGuardianColumns = {HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, HasGuardianMetaData.Table.COLUMN_IDCHILD}; 
		Cursor c = _context.getContentResolver().query(HasGuardianMetaData.CONTENT_URI, hasGuardianColumns, hasGuardianColumns[0] + " = '" + guardian.getId() + "'", null, null);
		
		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					Profile profile = getProfileById(c.getLong(c.getColumnIndex(hasGuardianColumns[1])));
					profiles.add(profile);
					c.moveToNext();
				}
			}
		}
		
		c.close();
		
		return profiles;
	}
	
	public List<Profile> getGuardiansByDepartment(Department department) {
		List<Profile> profiles = new ArrayList<Profile>();
		String[] hasDepartmentColumns = {HasDepartmentMetaData.Table.COLUMN_IDPROFILE, HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT}; 
		Cursor c = _context.getContentResolver().query(HasGuardianMetaData.CONTENT_URI, hasDepartmentColumns, hasDepartmentColumns[1] + " = '" + department.getId() + "'", null, null);
		
		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					Profile profile = getProfileById(c.getLong(c.getColumnIndex(hasDepartmentColumns[0])));
					if (profile.getPRole() == 1) {
						profiles.add(profile);
					}
					c.moveToNext();
				}
			}
		}
		
		c.close();
		
		return profiles;
	}

	/**
	 * Get a profile by its ID
	 * @param id the id of the profile
	 * @return profile
	 */
	public Profile getProfileById(long id) {
		Uri uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, id);
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if(c.moveToFirst()) {
				return cursorToProfile(c);
			}
		}
		
		c.close();

		return null;
	}

	/**
	 * Get all profiles
	 * @return List<Profile>, containing all profiles
	 */
	public List<Profile> getProfiles() {
		List<Profile> profiles = new ArrayList<Profile>();
		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
		profiles = cursorToProfiles(c);
		}

		c.close();

		return profiles;
	}
	
	public int attachChildToGuardian(Profile child, Profile guardian) {
		if (child.getPRole() != 3 || guardian.getPRole() != 1 || guardian.getPRole() != 2) {
			return -1;
		} else {
			ContentValues values = new ContentValues();
			values.put(HasGuardianMetaData.Table.COLUMN_IDCHILD, child.getId());
			values.put(HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, guardian.getId());
			_context.getContentResolver().insert(HasGuardianMetaData.CONTENT_URI, values);
			return 0;
		}
	}
	
	public int removeChildAttachmentToGuardian(Profile child, Profile guardian) {
		if (child.getPRole() != 3 || guardian.getPRole() != 1 || guardian.getPRole() != 2) {
			return -1;
		} else {
			_context.getContentResolver().delete(HasGuardianMetaData.CONTENT_URI, 
					HasGuardianMetaData.Table.COLUMN_IDCHILD + " = '" + child.getId() + "'" +
					HasGuardianMetaData.Table.COLUMN_IDGUARDIAN + " = '" + guardian.getId() + "'", null);
			return 0;
		}
	}

	/**
	 * Cursor to profiles
	 * @param cursor Input cursor
	 * @return Output Profiles
	 */
	private List<Profile> cursorToProfiles(Cursor cursor) {
		List<Profile> profiles = new ArrayList<Profile>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				profiles.add(cursorToProfile(cursor));
				cursor.moveToNext();
			}
		}
		

		return profiles;
	}

	/**
	 * Cursor to profile
	 * @param cursor Input cursor
	 * @return Output Profile
	 */
	private Profile cursorToProfile(Cursor cursor) {
		Profile profile = new Profile();
		profile.setId(cursor.getLong(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_ID)));
		profile.setFirstname(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_FIRST_NAME)));
		profile.setMiddlename(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_MIDDLE_NAME)));
		profile.setSurname(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_SUR_NAME)));
		profile.setPicture(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_PICTURE)));
		profile.setPhone(cursor.getLong(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_PHONE)));
		profile.setPRole(cursor.getLong(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_ROLE)));
		profile.setSetting(Setting.toObject(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_SETTINGS))));
		return profile;
	}

	/**
	 * @param profile the profile to put in the database
	 * @return the contentValues
	 */
	private ContentValues getContentValues(Profile profile) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(ProfilesMetaData.Table.COLUMN_FIRST_NAME, profile.getFirstname());
		contentValues.put(ProfilesMetaData.Table.COLUMN_SUR_NAME, profile.getSurname());
		contentValues.put(ProfilesMetaData.Table.COLUMN_MIDDLE_NAME, profile.getMiddlename());
		contentValues.put(ProfilesMetaData.Table.COLUMN_ROLE, profile.getPRole());
		contentValues.put(ProfilesMetaData.Table.COLUMN_PHONE, profile.getPhone());
		contentValues.put(ProfilesMetaData.Table.COLUMN_PICTURE, profile.getPicture());
		contentValues.put(ProfilesMetaData.Table.COLUMN_SETTINGS, Setting.toStringSetting(profile.getSetting()));

		return contentValues;
	}

	/**
	 * @return the columns of the table
	 */
	private String[] columns = new String[] { 
				ProfilesMetaData.Table.COLUMN_ID, 
				ProfilesMetaData.Table.COLUMN_FIRST_NAME,
				ProfilesMetaData.Table.COLUMN_SUR_NAME,
				ProfilesMetaData.Table.COLUMN_MIDDLE_NAME,
				ProfilesMetaData.Table.COLUMN_ROLE,
				ProfilesMetaData.Table.COLUMN_PHONE,
				ProfilesMetaData.Table.COLUMN_PICTURE,
				ProfilesMetaData.Table.COLUMN_SETTINGS};

	/**
	 * @return the certificate
	 */
	private String getNewCertificate() {
		Random rnd = new Random();
		String certificate = "";
		for (int i = 0; i < 256 + 1; i++)
		{
			certificate += (char)((rnd.nextInt() % 26) + 97);
		}
		return certificate;
	}
}
