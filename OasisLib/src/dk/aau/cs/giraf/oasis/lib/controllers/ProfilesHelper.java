package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.ProfilesMetaData;
import dk.aau.cs.giraf.oasis.lib.models.AuthUser;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.HasDepartment;
import dk.aau.cs.giraf.oasis.lib.models.HasGuardian;
import dk.aau.cs.giraf.oasis.lib.models.HasSubDepartment;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Profile.pRoles;
import dk.aau.cs.giraf.oasis.lib.models.Setting;



/**
 * Helper class for Profiles 
 * @author Admin
 *
 */
public class ProfilesHelper {


	private static Context _context;
	private AuthUsersController au;
	private HasGuardianController hg;
	private HasDepartmentController hd;
	private HasSubDepartmentController hsd;
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
	 * Constructor
	 * @param context Current context
	 */
	public ProfilesHelper(Context context){
		_context = context;
		au = new AuthUsersController(_context);
		hg = new HasGuardianController(_context);
		hd = new HasDepartmentController(_context);
		hsd = new HasSubDepartmentController(_context);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear profiles table
//	 */
//	public void clearProfilesTable() {
//		_context.getContentResolver().delete(ProfilesMetaData.CONTENT_URI, null, null);
//	}

	/**
	 * Remove child attachment to guardian
	 * @param child Child profile
	 * @param guardian Guardian profile
	 * @return Rows
	 */
	public int removeChildAttachmentToGuardian(Profile child, Profile guardian) {
		if (child.getPRole() == pRoles.CHILD.ordinal() && 
				(guardian.getPRole() == pRoles.GUARDIAN.ordinal() || 
				 guardian.getPRole() == pRoles.PARENT.ordinal())) {
			return hg.removeHasGuardian(child, guardian);
		} else {
			return -1;
		}
	}

	/**
	 * Insert profile
	 * @param profile Profile containing data
	 */
	public long insertProfile(Profile profile) {		
		long id = au.insertAuthUser(AuthUser.aRole.PROFILE.ordinal());

		ContentValues profileContentValues = getContentValues(profile);
		profileContentValues.put(ProfilesMetaData.Table.COLUMN_ID, id);
		_context.getContentResolver().insert(ProfilesMetaData.CONTENT_URI, profileContentValues);
		return id;
	}

	/**
	 * Attach child to guardian
	 * @param child Child profile
	 * @param guardian Guardian profile
	 * @return
	 */
	public long attachChildToGuardian(Profile child, Profile guardian) {
		if (child.getPRole() == pRoles.CHILD.ordinal() && 
				(guardian.getPRole() == pRoles.GUARDIAN.ordinal() || 
				 guardian.getPRole() == pRoles.PARENT.ordinal())) {
			HasGuardian hgModel = new HasGuardian();
			hgModel.setIdChild(child.getId());
			hgModel.setIdGuardian(guardian.getId());
			return hg.insertHasGuardian(hgModel);
		} else {
			return -1;
		}	
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
	 * Authenticates the profile
	 * @param certificate the certificate that authenticates the profile
	 * @return the authenticated profile or null
	 */
	public Profile authenticateProfile(String certificate) {
		Profile profile = null;
		long id;

		id = au.getIdByCertificate(certificate);

		if (id != -1) {
			profile = getProfileById(id);
		}
		
		if (profile == null) {
			
		}

		return profile;
	}

	/**
	 * Set a new certificate
	 * @param certificate the certificate to set
	 * @param profile the profile whom the certificate is updated for
	 * @return the number of row affected
	 */
	public int setCertificate(String certificate, Profile profile) {
		int result;
		result = au.setCertificate(certificate, profile.getId());

		return result;
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
			c.close();
		}

		return profiles;
	}
	
	/**
	 * Get all profiles by a role
	 * @return List<Profile>, containing all profiles
	 */	
	public List<Profile> getProfilesByRole(Profile.pRoles role) {
		List<Profile> profiles = new ArrayList<Profile>();
		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, ProfilesMetaData.Table.COLUMN_ROLE + " = '" + role.ordinal() + "'", null, null);

		if (c != null) {
			profiles = cursorToProfiles(c);
			c.close();
		}
		
		return profiles;
	}

	/**
	 * Retrieve the certificates for a profile
	 * @param profile
	 * @return List<String> or null
	 */
	public List<String> getCertificatesByProfile(Profile profile) {
		List<String> certificate;
		certificate = au.getCertificatesById(profile.getId());

		return certificate;
	}

	/**
	 * Get children by department
	 * @param department Department
	 * @return List of profiles
	 */
	public List<Profile> getChildrenByDepartment(Department department) {
		List<Profile> profiles = new ArrayList<Profile>();
		
		List<HasDepartment> list = hd.getProfilesByDepartment(department);

		for (HasDepartment hdModel : list) {
			Profile profile = getProfileById(hdModel.getIdProfile());
			if (profile.getPRole() == pRoles.CHILD.ordinal()) {
				profiles.add(profile);
			}
		}
		
		return profiles;
	}
	
	/**
	 * Get children by department and sub departments
	 * @param department Department
	 * @return List of profiles
	 */
	public List<Profile> getChildrenByDepartmentAndSubDepartments(Department department) {
		List<Profile> profiles = new ArrayList<Profile>();
		
		profiles.addAll(getChildrenByDepartment(department));
		
		List<HasSubDepartment> list = hsd.getSubDepartmentsByDepartment(department);

		for (HasSubDepartment hsdModel : list) {
			Department _department = new Department();
			department.setId(hsdModel.getIdSubDepartment());
			profiles.addAll(getChildrenByDepartmentAndSubDepartments(_department));
		}
		
		return profiles;
	}

	/**
	 * Get children by guardian
	 * @param guardian Guardian profile
	 * @return List of profiles
	 */
	public List<Profile> getChildrenByGuardian(Profile guardian) {
		List<Profile> profiles = new ArrayList<Profile>();
		
		if (guardian.getPRole() == pRoles.GUARDIAN.ordinal() || 
				guardian.getPRole() == pRoles.PARENT.ordinal()) {
			List<HasGuardian> list = hg.getChildrenByGuardian(guardian);
			
			for (HasGuardian hgModel : list) {
				Profile child = getProfileById(hgModel.getIdChild());
				if (child.getPRole() == pRoles.CHILD.ordinal()) {
					profiles.add(child);
				}
			}
		}

		return profiles;
	}

	/**
	 * Get guardian by department
	 * @param department Department
	 * @return List of profiles
	 */
	public List<Profile> getGuardiansByDepartment(Department department) {
		List<Profile> profiles = new ArrayList<Profile>();
		
		List<HasDepartment> list = hd.getProfilesByDepartment(department);

		for (HasDepartment hd : list) {
			Profile profile = getProfileById(hd.getIdProfile());
			if (profile.getPRole() == pRoles.GUARDIAN.ordinal()) {
				profiles.add(profile);
			}
		}
		
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
			if (c.moveToFirst()) {
				return cursorToProfile(c);
			}
			c.close();
		}

		return null;
	}

	/**
	 * Get profiles by name
	 * @param name Profile name
	 * @return List of profiles
	 */
	public List<Profile> getProfilesByName(String name) {
		List<Profile> profiles = new ArrayList<Profile>();
		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, 
				ProfilesMetaData.Table.COLUMN_FIRST_NAME + " LIKE '%" + name + "%' OR " +
						ProfilesMetaData.Table.COLUMN_MIDDLE_NAME + " LIKE '%" + name + "%' OR " +
						ProfilesMetaData.Table.COLUMN_SUR_NAME +  " LIKE '%" + name + "%'", null, null);

		if (c != null) {
			profiles = cursorToProfiles(c);
			c.close();
		}

		return profiles;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - Keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to profiles
	 * @param cursor Input cursor
	 * @return Output Profiles
	 */
	private List<Profile> cursorToProfiles(Cursor cursor) {
		List<Profile> profiles = new ArrayList<Profile>();

		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
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
		profile.setSettings(Setting.toSetting(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_SETTINGS))));
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
		contentValues.put(ProfilesMetaData.Table.COLUMN_SETTINGS, Setting.toStringSetting(profile.getSettings()));

		return contentValues;
	}
}
