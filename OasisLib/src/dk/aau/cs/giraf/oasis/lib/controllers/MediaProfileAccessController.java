package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.MediaProfileAccessMetaData;
import dk.aau.cs.giraf.oasis.lib.models.MediaProfileAccess;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

class MediaProfileAccessController {

	private Context _context;
	private String[] columns = new String[] { 
			MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA, 
			MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE};

	public MediaProfileAccessController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int clearMediaProfileAccessTable() {
		return _context.getContentResolver().delete(MediaProfileAccessMetaData.CONTENT_URI, null, null);
	}

	public long insertMediaProfileAccess(MediaProfileAccess mpa) {
		ContentValues cv = getContentValues(mpa);
		_context.getContentResolver().insert(MediaProfileAccessMetaData.CONTENT_URI, cv);

		return 0;
	}

	public List<MediaProfileAccess> getMediaProfileAccesses() {
		List<MediaProfileAccess> list = new ArrayList<MediaProfileAccess>();

		Cursor c = _context.getContentResolver().query(MediaProfileAccessMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			list = cursorToMediaProfileAccessList(c);

			c.close();
		}

		return list;
	}

	public List<MediaProfileAccess> getMediaByProfile(Profile profile) {
		List<MediaProfileAccess> list= new ArrayList<MediaProfileAccess>();

		Cursor c = _context.getContentResolver().query(MediaProfileAccessMetaData.CONTENT_URI, columns, 
				MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE + " = '" + profile.getId() + "'", null, null);

		if (c != null) {
			list = cursorToMediaProfileAccessList(c);

			c.close();
		}

		return list;
	}
	
	public int modifyMediaProfileAccess(MediaProfileAccess mpa) {
		ContentValues cv = getContentValues(mpa);
		return _context.getContentResolver().update(MediaProfileAccessMetaData.CONTENT_URI, cv, 
				MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA + " = '" + mpa.getIdMedia() + "' AND " +
				MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE + " = '" + mpa.getIdProfile() + "'", null);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private List<MediaProfileAccess> cursorToMediaProfileAccessList(Cursor cursor) {
		List<MediaProfileAccess> list = new ArrayList<MediaProfileAccess>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				list.add(cursorToMediaProfileAccess(cursor));
				cursor.moveToNext();
			}
		}

		return  list;
	}

	private MediaProfileAccess cursorToMediaProfileAccess(Cursor cursor) {
		MediaProfileAccess mpa = new MediaProfileAccess();
		mpa.setIdMedia(cursor.getLong(cursor.getColumnIndex(MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA)));
		mpa.setIdProfile(cursor.getLong(cursor.getColumnIndex(MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE)));
		return mpa;
	}

	private ContentValues getContentValues(MediaProfileAccess mpa) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA, mpa.getIdMedia());
		contentValues.put(MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE, mpa.getIdProfile());
		return contentValues;
	}
}