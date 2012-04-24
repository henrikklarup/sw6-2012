package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.TagsMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Tag;

public class TagsHelper {

	private Context _context;
	private String[] columns = new String[] { 
			TagsMetaData.Table.COLUMN_ID, 
			TagsMetaData.Table.COLUMN_CAPTION};

	public TagsHelper(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int clearTagsTable() {
		return _context.getContentResolver().delete(TagsMetaData.CONTENT_URI, null, null);
	}
	
	public long insertTag(Tag tag) {
		long id = 0;
		
		Cursor c = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, columns, TagsMetaData.Table.COLUMN_CAPTION + " = '" + tag.getCaption() + "'", null, null);
		
		if (c != null) {
			if (c.moveToFirst()) {
				id = c.getLong(c.getColumnIndex(TagsMetaData.Table.COLUMN_ID));
			}
			c.close();
		} else {
			Uri uri;
			ContentValues cv = getContentValues(tag);
			uri = _context.getContentResolver().insert(TagsMetaData.CONTENT_URI, cv);
			id = Integer.parseInt(uri.getPathSegments().get(1));
		}
		
		return id;
	}

	public int modifyTag(Tag tag) {
		Uri uri = ContentUris.withAppendedId(TagsMetaData.CONTENT_URI, tag.getId());
		ContentValues cv = getContentValues(tag);
		return _context.getContentResolver().update(uri, cv, null, null);
	}

	public List<Tag> getTags() {
		List<Tag> tags = new ArrayList<Tag>();

		Cursor c = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			tags = cursorToTagList(c);
		}

		c.close();

		return tags;
	}
	
	public List<Tag> getTagsByCaption(String name) {
		List<Tag> tags = new ArrayList<Tag>();

		Cursor c = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, columns, TagsMetaData.Table.COLUMN_CAPTION + " = '" + name + "'", null, null);

		if (c != null) {
			tags = cursorToTagList(c);
		}

		c.close();

		return tags;
	}
	
	public List<Tag> getTagsById(Long id) {
		List<Tag> tags = new ArrayList<Tag>();

		Cursor c = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, columns, TagsMetaData.Table.COLUMN_ID + " = '" + id + "'", null, null);

		if (c != null) {
			tags = cursorToTagList(c);
		}

		c.close();

		return tags;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to Tagss
	 * @param cursor Input cursor
	 * @return Output List of Tags
	 */
	private List<Tag> cursorToTagList(Cursor cursor) {
		List<Tag> tags = new ArrayList<Tag>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				tags.add(cursorToTag(cursor));
				cursor.moveToNext();
			}
		}


		return tags;
	}

	/**
	 * Cursor to AuthUser
	 * @param cursor Input cursor
	 * @return Output AuthUser
	 */
	private Tag cursorToTag(Cursor cursor) {
		Tag tag = new Tag();
		tag.setId(cursor.getLong(cursor.getColumnIndex(TagsMetaData.Table.COLUMN_ID)));
		tag.setCaption(cursor.getString(cursor.getColumnIndex(TagsMetaData.Table.COLUMN_CAPTION)));
		return tag;
	}

	/**
	 * @param authUser the authUser to put in the database
	 * @return the contentValues
	 */
	private ContentValues getContentValues(Tag tag) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(TagsMetaData.Table.COLUMN_CAPTION, tag.getCaption());
		return contentValues;
	}
}
