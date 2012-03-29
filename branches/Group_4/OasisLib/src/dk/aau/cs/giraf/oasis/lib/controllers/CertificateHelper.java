package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.CertificatesMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Certificate;

/**
 * Helper class for Certificate
 * @author Admin
 *
 */
public class CertificateHelper {

	private static Context _context;
	/**
	 * Constructor
	 * @param context Current context
	 */
	public CertificateHelper(Context context){
		_context = context;
	}

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
	 * @return List<Certificate>, containing all certificates
	 */
	public List<Certificate> getCertificates() {

		List<Certificate> certificates = new ArrayList<Certificate>();

		String[] columns = new String[] { CertificatesMetaData.Table.COLUMN_ID, 
				CertificatesMetaData.Table.COLUMN_AUTHKEY};
		Cursor c = _context.getContentResolver().query(CertificatesMetaData.CONTENT_URI, columns, null, null, null);

		if(c.moveToFirst()) {
			while(!c.isAfterLast()) {
				certificates.add(cursorToCertificate(c));
				c.moveToNext();
			}
		}

		c.close();

		return certificates;
	}

	/**
	 * Clear certificates table
	 */
	public void clearCertificateTable() {
		_context.getContentResolver().delete(CertificatesMetaData.CONTENT_URI, null, null);
	}

	/**
	 * Cursor to certificate
	 * @param cursor Input cursor
	 * @return Output Certificate
	 */
	private Certificate cursorToCertificate(Cursor cursor) {
		Certificate certificate = new Certificate();
		certificate.setId(cursor.getLong(cursor.getColumnIndex(CertificatesMetaData.Table.COLUMN_ID)));
		certificate.setAuthkey(cursor.getString(cursor.getColumnIndex(CertificatesMetaData.Table.COLUMN_AUTHKEY)));
		return certificate;
	}
}
