package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.ProfilesMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

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
		cv.put(ProfilesMetaData.Table.COLUMN_DEPARTMENTID, _profile.getIdCertificate());
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
		cv.put(ProfilesMetaData.Table.COLUMN_DEPARTMENTID, _profile.getIdCertificate());
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
	 * @param QRCode the QRCode that autenticates the profile
	 * @return the authenticated profile
	 */
	public Profile authenticateProfile(String QRCode) {
		Profile profile = new Profile("Dummy","Dummy","Dummy", 1, 0, "Dummy", 0);
		profile.setId(101);
		return profile;
	}
	
	public Profile getProfileById(long _id) {
		Uri uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, _id);
		String[] columns = new String[] { ProfilesMetaData.Table.COLUMN_ID, 
				ProfilesMetaData.Table.COLUMN_FIRST_NAME,
				ProfilesMetaData.Table.COLUMN_SUR_NAME,
				ProfilesMetaData.Table.COLUMN_MIDDLE_NAME,
				ProfilesMetaData.Table.COLUMN_ROLE,
				ProfilesMetaData.Table.COLUMN_PHONE,
				ProfilesMetaData.Table.COLUMN_PICTURE,
				ProfilesMetaData.Table.COLUMN_DEPARTMENTID};
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if(c.moveToFirst()) {
			return cursorToProfile(c);
		}

		return null;
	}

	/**
	 * Get all profiles
	 * @return List<Profile>, containing all profiles
	 */
	public List<Profile> getProfiles() {
		List<Profile> profiles = new ArrayList<Profile>();
		String[] columns = new String[] { ProfilesMetaData.Table.COLUMN_ID, 
				ProfilesMetaData.Table.COLUMN_FIRST_NAME,
				ProfilesMetaData.Table.COLUMN_SUR_NAME,
				ProfilesMetaData.Table.COLUMN_MIDDLE_NAME,
				ProfilesMetaData.Table.COLUMN_ROLE,
				ProfilesMetaData.Table.COLUMN_PHONE,
				ProfilesMetaData.Table.COLUMN_PICTURE,
				ProfilesMetaData.Table.COLUMN_DEPARTMENTID};
		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, null, null, null);

		if(c.moveToFirst()) {
			while(!c.isAfterLast()) {
				profiles.add(cursorToProfile(c));
				c.moveToNext();
			}
		}

		c.close();

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
		profile.setIdCertificate(cursor.getLong(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_DEPARTMENTID)));
		profile.setPhone(cursor.getLong(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_PHONE)));
		profile.setRole(cursor.getLong(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_ROLE)));
		return profile;
	}

}
