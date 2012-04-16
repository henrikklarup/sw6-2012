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
import dk.aau.cs.giraf.oasis.lib.models.AuthUser;

class AuthUsersHelper {

	private Context _context;

	public AuthUsersHelper(Context context) {
		_context = context;
	}

	private String[] columns = new String[] { 
			AuthUsersMetaData.Table.COLUMN_ID, 
			AuthUsersMetaData.Table.COLUMN_CERTIFICATE,
			AuthUsersMetaData.Table.COLUMN_ROLE};

	public long insertAuthUser(AuthUser authUser) {
		ContentValues cv = getContentValues(authUser);
		_context.getContentResolver().insert(AuthUsersMetaData.CONTENT_URI, cv);

		return getIdByCertificate(authUser.getCertificate());
	}
	
	public int modifyAuthUser(AuthUser authUser) {
		Uri uri = ContentUris.withAppendedId(AuthUsersMetaData.CONTENT_URI, authUser.getId());
		ContentValues cv = getContentValues(authUser);
		return _context.getContentResolver().update(uri, cv, null, null);
	}
	
	public int clearAuthUsersTable() {
		return _context.getContentResolver().delete(AuthUsersMetaData.CONTENT_URI, null, null);
	}
	
	public List<AuthUser> getAuthUsers() {
		List<AuthUser> authUsers = new ArrayList<AuthUser>();
		
		Cursor c = _context.getContentResolver().query(AuthUsersMetaData.CONTENT_URI, columns, null, null, null);
		
		if (c != null) {
			authUsers = cursorToAuthUsers(c);
		}
		
		c.close();
		
		return authUsers;
	}

	public List<String> getCertificatesById(long id) {
		List<String> certificates = new ArrayList<String>();
		Uri uri = ContentUris.withAppendedId(AuthUsersMetaData.CONTENT_URI, id);

		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					certificates.add(c.getString(c.getColumnIndex(AuthUsersMetaData.Table.COLUMN_CERTIFICATE)));
					c.moveToNext();
				}
			}
		}
		
		c.close();

		return certificates;
	}

	public long getIdByCertificate(String certificate) {
		long id = -1;
		Cursor c = _context.getContentResolver().query(AuthUsersMetaData.CONTENT_URI, columns, null, new String[] {certificate}, null);
		
		if(c != null) {
			if(c.moveToFirst()) {
				id = c.getLong(c.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ID));
			}
		}
		
		c.close();
		return id;
	}
	
	/**
	 * Cursor to AuthUsers
	 * @param cursor Input cursor
	 * @return Output AuthUsers
	 */
	private List<AuthUser> cursorToAuthUsers(Cursor cursor) {
		List<AuthUser> authUsers = new ArrayList<AuthUser>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				authUsers.add(cursorToAuthUser(cursor));
				cursor.moveToNext();
			}
		}
		

		return authUsers;
	}

	/**
	 * Cursor to AuthUser
	 * @param cursor Input cursor
	 * @return Output AuthUser
	 */
	private AuthUser cursorToAuthUser(Cursor cursor) {
		AuthUser authUser = new AuthUser();
		authUser.setId(cursor.getLong(cursor.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ID)));
		authUser.setCertificate(cursor.getString(cursor.getColumnIndex(AuthUsersMetaData.Table.COLUMN_CERTIFICATE)));
		authUser.setaRole(cursor.getLong(cursor.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ROLE)));
		return authUser;
	}
	
	/**
	 * @param authUser the authUser to put in the database
	 * @return the contentValues
	 */
	private ContentValues getContentValues(AuthUser authUser) {
		ContentValues contentValues = new ContentValues();
		
		if (authUser.getCertificate() == null) {
			contentValues.put(AuthUsersMetaData.Table.COLUMN_CERTIFICATE, getNewCertificate());
		} else {
			contentValues.put(AuthUsersMetaData.Table.COLUMN_CERTIFICATE, authUser.getCertificate());
		}
		
		contentValues.put(AuthUsersMetaData.Table.COLUMN_ROLE, authUser.getaRole());
		return contentValues;
	}
	
	/**
	 * @return a certificate
	 */
	private String getNewCertificate() {
		Random rnd = new Random();
		String certificate = "";
		for (int i = 0; i < 256 + 1; i++)
		{
			certificate += (char)(rnd.nextInt(26) + 97);
		}
		return certificate;
	}
}
