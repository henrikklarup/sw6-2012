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
	 * @param media Media containing data
	 */
	public void insertMedia(Media media) {
		ContentValues cv = getContentValues(media);
		_context.getContentResolver().insert(MediaMetaData.CONTENT_URI, cv);
	}

	/**
	 * Modify media
	 * @param media Media containing data to modify
	 */
	public void modifyMedia(Media media) {
		Uri uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, media.getId());
		ContentValues cv = getContentValues(media);
		_context.getContentResolver().update(uri, cv, null, null);
	}
	
	/**
	 * Get media by id
	 * @param id the id of the media
	 * @return the media or null
	 */
	public Media getSingleMediaById(long id) {
		Uri uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, id);
		String[] columns = getTableColumns();
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);
		Media media = null;
		
		if(c.moveToFirst()) {
			media = cursorToSingleMedia(c);			
		}
		
		c.close();
		return media;
	}
	
	/**
	 * Get media by name
	 * @param name the name of the media
	 * @return List<Media>, containing all media of that name
	 */
	public List<Media> getMediaByName(String name) {
		String[] columns = getTableColumns();
		Cursor c = _context.getContentResolver().query(Uri.withAppendedPath(MediaMetaData.CONTENT_URI, name), columns, null, null, null);
		
		List<Media> media = new ArrayList<Media>();
		media = cursorToMedia(c);

		c.close();
		return media;
	}
	
	/**
	 * Get all media
	 * @return List<Media>, containing all media
	 */
	public List<Media> getMedia() {
		String[] columns = getTableColumns();
		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, null, null, null);
		
		List<Media> media = new ArrayList<Media>();
		media = cursorToMedia(c);

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
	 * @return List<Media>, containing all the media from the cursor
	 */
	private List<Media> cursorToMedia(Cursor cursor) {
		List<Media> media = new ArrayList<Media>();
		
		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				media.add(cursorToSingleMedia(cursor));
				cursor.moveToNext();
			}
		}
		
		return media;
	}

	/**
	 * Cursor to single media
	 * @param cursor Input cursor
	 * @return Single Media
	 */
	private Media cursorToSingleMedia(Cursor cursor) {
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
	
	/**
	 * Getter for the content values
	 * @param media the media which values should be used
	 * @return the contentValues
	 */
	private ContentValues getContentValues(Media media) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(MediaMetaData.Table.COLUMN_PATH, media.getPath());
		contentValues.put(MediaMetaData.Table.COLUMN_NAME, media.getName());
		contentValues.put(MediaMetaData.Table.COLUMN_PUBLIC, media.isMPublic());
		contentValues.put(MediaMetaData.Table.COLUMN_TYPE, media.getType());
		contentValues.put(MediaMetaData.Table.COLUMN_TAGS, media.getTags());
		contentValues.put(MediaMetaData.Table.COLUMN_OWNERID, media.getOwnerId());
		
		return contentValues;
	}
	
	/**
	 * Getter for table columns
	 * @return the columns
	 */
	private String[] getTableColumns() {
		String[] columns = new String[] { 
				MediaMetaData.Table.COLUMN_ID, 
				MediaMetaData.Table.COLUMN_PATH,
				MediaMetaData.Table.COLUMN_NAME,
				MediaMetaData.Table.COLUMN_PUBLIC,
				MediaMetaData.Table.COLUMN_TYPE,
				MediaMetaData.Table.COLUMN_TAGS,
				MediaMetaData.Table.COLUMN_OWNERID};
		
		return columns;
	}
}