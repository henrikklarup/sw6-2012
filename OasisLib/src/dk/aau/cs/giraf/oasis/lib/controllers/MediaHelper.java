package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.MediaMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Media;

/**
 * Helper class for Media
 * @author Admin
 *
 */
public class MediaHelper {

	private static Context _context;
	/**
	 * Constructor
	 * @param context Current context
	 */
	public MediaHelper(Context context){
		_context = context;
	}

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
	 * @return List<Media>, containing all media
	 */
	public List<Media> getMedia() {
		List<Media> media = new ArrayList<Media>();
		String[] columns = new String[] { MediaMetaData.Table.COLUMN_ID, 
				MediaMetaData.Table.COLUMN_PATH,
				MediaMetaData.Table.COLUMN_NAME,
				MediaMetaData.Table.COLUMN_PUBLIC,
				MediaMetaData.Table.COLUMN_TYPE,
				MediaMetaData.Table.COLUMN_TAGS,
				MediaMetaData.Table.COLUMN_OWNERID};
		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, null, null, null);

		if(c.moveToFirst()) {
			while(!c.isAfterLast()) {
				media.add(cursorToMedia(c));
				c.moveToNext();
			}
		}

		c.close();

		return media;
	}

	/**
	 * Clear media table
	 */
	public void clearMediaTable() {
		_context.getContentResolver().delete(MediaMetaData.CONTENT_URI, null, null);
	}

	/**
	 * Cursor to media
	 * @param cursor Input cursor
	 * @return Output Media
	 */
	private Media cursorToMedia(Cursor cursor) {
		Media media = new Media();
		media.setId(cursor.getLong(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_ID)));
		media.setName(cursor.getString(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_NAME)));
		media.setPath(cursor.getString(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_PATH)));
		media.setTags(cursor.getString(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_TAGS)));
		media.setType(cursor.getString(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_TYPE)));
		media.setOwnerId(cursor.getLong(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_OWNERID)));
		if (cursor.getLong(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_PUBLIC)) == 1) {
			media.set_public(true);
		} else {
			media.set_public(false);
		}
		return media;
	}
}
