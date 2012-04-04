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
import dk.aau.cs.giraf.oasis.lib.metadata.ProfilesMetaData;
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
		_context.getContentResolver().insert(AuthUsersMetaData.CONTENT_URI, authusersContentValues);
		
		String[] columns = new String[] { 
				AuthUsersMetaData.Table.COLUMN_ID, 
				AuthUsersMetaData.Table.COLUMN_CERTIFICATE};
		Cursor c = _context.getContentResolver().query(AuthUsersMetaData.CONTENT_URI, columns, null, new String[] {certificate}, null);
		
		if(c.moveToFirst()) {
			long id = c.getLong(c.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ID));
			profile.setId(id);
			
			ContentValues profileContentValues = getContentValues(profile);
			profileContentValues.put(ProfilesMetaData.Table.COLUMN_ID, id);
			_context.getContentResolver().insert(ProfilesMetaData.CONTENT_URI, profileContentValues);
			return 0;
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
		
		String[] columns = new String[] { 
				AuthUsersMetaData.Table.COLUMN_ID, 
				AuthUsersMetaData.Table.COLUMN_CERTIFICATE};
		Cursor c = _context.getContentResolver().query(AuthUsersMetaData.CONTENT_URI, columns, null, new String[] {"Certificate"}, null);
		
		if(c.moveToFirst()) {
			profile = getProfileById(c.getLong(c.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ID)));
		}
		
		c.close();
		
//		Profile profile = new Profile("Dummy", "Dummy","Dummy", 0, 12345678, "Dummy", new Setting<String,String,String>());
//		profile.setId(101);
		
		return profile;
	}
	
	/**
	 * Get a profile by its ID
	 * @param id the id of the profile
	 * @return profile
	 */
	public Profile getProfileById(long id) {
		Uri uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, id);
		String[] columns = getTableColumns();
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
		String[] columns = getTableColumns();
		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, null, null, null);

		profiles = cursorToProfiles(c);

		c.close();

		return profiles;
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
		profile.setSetting(Setting.toObject(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_SETTINGS)).getBytes()));
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
		contentValues.put(ProfilesMetaData.Table.COLUMN_SETTINGS, Setting.toByteArray(profile.getSetting()).toString());
		
		return contentValues;
	}
	
	/**
	 * @return the columns of the table
	 */
	private String[] getTableColumns() {
		String[] columns = new String[] { 
				ProfilesMetaData.Table.COLUMN_ID, 
				ProfilesMetaData.Table.COLUMN_FIRST_NAME,
				ProfilesMetaData.Table.COLUMN_SUR_NAME,
				ProfilesMetaData.Table.COLUMN_MIDDLE_NAME,
				ProfilesMetaData.Table.COLUMN_ROLE,
				ProfilesMetaData.Table.COLUMN_PHONE,
				ProfilesMetaData.Table.COLUMN_PICTURE,
				ProfilesMetaData.Table.COLUMN_SETTINGS};
		return columns;
	}
	
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
