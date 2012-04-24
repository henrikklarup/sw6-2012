package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.HasTagMetaData;
import dk.aau.cs.giraf.oasis.lib.models.HasTag;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Tag;

class HasTagController {

	private Context _context;
	private String[] columns = new String[] { 
			HasTagMetaData.Table.COLUMN_IDMEDIA, 
			HasTagMetaData.Table.COLUMN_IDTAG};

	public HasTagController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int clearHasTagTable() {
		return _context.getContentResolver().delete(HasTagMetaData.CONTENT_URI, null, null);
	}

	public int removeHasTag(Tag tag, Media media) {
		return _context.getContentResolver().delete(HasTagMetaData.CONTENT_URI, HasTagMetaData.Table.COLUMN_IDTAG + " = '" + tag.getId() +
				"' AND " + HasTagMetaData.Table.COLUMN_IDMEDIA + " = '" + media.getId() + "'", null);
	}
	
	public int removeHasTagList(List<Tag> tags, Media media) {
		int result = 0;
		for (Tag t : tags) {
			result += _context.getContentResolver().delete(HasTagMetaData.CONTENT_URI, HasTagMetaData.Table.COLUMN_IDTAG + " = '" + t.getId() +
					"' AND " + HasTagMetaData.Table.COLUMN_IDMEDIA + " = '" + media.getId() + "'", null);
		}

		return result;
	}

	public long insertHasTag(HasTag ht) {
		ContentValues cv = getContentValues(ht);
		_context.getContentResolver().insert(HasTagMetaData.CONTENT_URI, cv);

		return 0;
	}

	public List<HasTag> getHasTags() {
		List<HasTag> list = new ArrayList<HasTag>();

		Cursor c = _context.getContentResolver().query(HasTagMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			list = cursorToHasTagList(c);

			c.close();
		}

		return list;
	}

	public List<HasTag> getHasTagsByMedia(Media media) {
		List<HasTag> list= new ArrayList<HasTag>();

		Cursor c = _context.getContentResolver().query(HasTagMetaData.CONTENT_URI, columns, 
				HasTagMetaData.Table.COLUMN_IDMEDIA + " = '" + media.getId() + "'", null, null);

		if (c != null) {
			list = cursorToHasTagList(c);

			c.close();
		}

		return list;
	}

	public List<HasTag> getHasTagsByTag(Tag tag) {
		List<HasTag> list= new ArrayList<HasTag>();

		Cursor c = _context.getContentResolver().query(HasTagMetaData.CONTENT_URI, columns, 
				HasTagMetaData.Table.COLUMN_IDTAG + " = '" + tag.getId() + "'", null, null);

		if (c != null) {
			list = cursorToHasTagList(c);

			c.close();
		}

		return list;
	}

	public int modifyHasTag(HasTag ht) {
		ContentValues cv = getContentValues(ht);
		return _context.getContentResolver().update(HasTagMetaData.CONTENT_URI, cv, 
				HasTagMetaData.Table.COLUMN_IDMEDIA + " = '" + ht.getIdMedia() + "' AND " +
						HasTagMetaData.Table.COLUMN_IDTAG + " = '" + ht.getIdTag() + "'", null);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private List<HasTag> cursorToHasTagList(Cursor cursor) {
		List<HasTag> list = new ArrayList<HasTag>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				list.add(cursorToHasTag(cursor));
				cursor.moveToNext();
			}
		}

		return  list;
	}

	private HasTag cursorToHasTag(Cursor cursor) {
		HasTag ht = new HasTag();
		ht.setIdMedia(cursor.getLong(cursor.getColumnIndex(HasTagMetaData.Table.COLUMN_IDMEDIA)));
		ht.setIdTag(cursor.getLong(cursor.getColumnIndex(HasTagMetaData.Table.COLUMN_IDTAG)));
		return ht;
	}

	private ContentValues getContentValues(HasTag ht) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(HasTagMetaData.Table.COLUMN_IDMEDIA, ht.getIdMedia());
		contentValues.put(HasTagMetaData.Table.COLUMN_IDTAG, ht.getIdTag());
		return contentValues;
	}
}