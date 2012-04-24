package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.HasTagMetaData;
import dk.aau.cs.giraf.oasis.lib.metadata.MediaMetaData;
import dk.aau.cs.giraf.oasis.lib.metadata.TagsMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.HasLink;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.MediaDepartmentAccess;
import dk.aau.cs.giraf.oasis.lib.models.MediaProfileAccess;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Tag;

/**
 * Helper class for Media
 * @author Admin
 *
 */
public class MediaHelper {

	private static Context _context;
	private HasLinkController hl;
	private MediaDepartmentAccessController mda;
	private MediaProfileAccessController mpa;
	private String[] columns = new String[] { 
			MediaMetaData.Table.COLUMN_ID, 
			MediaMetaData.Table.COLUMN_PATH,
			MediaMetaData.Table.COLUMN_NAME,
			MediaMetaData.Table.COLUMN_PUBLIC,
			MediaMetaData.Table.COLUMN_TYPE,
			MediaMetaData.Table.COLUMN_OWNERID};

	/**
	 * Constructor
	 * @param context Current context
	 */
	public MediaHelper(Context context){
		_context = context;
		hl = new HasLinkController(_context);
		mda = new MediaDepartmentAccessController(_context);
		mpa = new MediaProfileAccessController(_context);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Clear media table
	 */
	public void clearMediaTable() {
		_context.getContentResolver().delete(MediaMetaData.CONTENT_URI, null, null);
	}

	public int removeMediaAttachmentToProfile(Media media, Profile profile) {
		return mpa.removeMediaProfileAccess(media, profile); 
	}

	public int removeMediaAttachmentToDepartment(Media media, Department department) {
		return mda.removeMediaProfileAccess(media, department);
	}

	public int removeTagsFromMedia(String[] tags, Media media) {
		int result = 0;
		for (String t : tags) {
			String[] tagColumns = {TagsMetaData.Table.COLUMN_ID, TagsMetaData.Table.COLUMN_CAPTION};
			Cursor cTag = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, tagColumns, tagColumns[1] + " = '" + t + "'", null, null);
			if (cTag != null) {
				if (cTag.moveToFirst()) {
					String caption = cTag.getString(cTag.getColumnIndex(tagColumns[1]));
					Tag _tag = new Tag(caption);
					result += _context.getContentResolver().delete(HasTagMetaData.CONTENT_URI, HasTagMetaData.Table.COLUMN_IDTAG + " = '" + _tag.getId() +
							"' AND " + HasTagMetaData.Table.COLUMN_IDMEDIA + " = '" + media.getId() + "'", null);
				}
			}

		}
		return result;
	}
	
	public int removeSubMediaAttachmentToMedia(Media subMedia, Media media) {
		return hl.removeHasLink(subMedia, media);
	}

	/**
	 * Insert media
	 * @param media Media containing data
	 */
	
	public long insertMedia(Media media) {
		long result = 0;
		Uri uri;
		ContentValues cv = getContentValues(media);
		uri = _context.getContentResolver().insert(MediaMetaData.CONTENT_URI, cv);
		result = Integer.parseInt(uri.getPathSegments().get(1));
		
		return result;
	}

	public int addTagsToMedia(String[] tags, Media media) {
		int result = -1;
		for (String tag : tags) {
			String[] tagColumns = {TagsMetaData.Table.COLUMN_ID, TagsMetaData.Table.COLUMN_CAPTION};
			Cursor cTag = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, tagColumns, tagColumns[1] + " = '" + tag + "'", null, null);
			if (cTag != null) {
				if (cTag.moveToFirst()) {
					String caption = cTag.getString(cTag.getColumnIndex(tagColumns[1]));
					Tag _tag = new Tag(caption); 
					addHasTag(_tag, media);
					result = 0;
				}
			} else {
				ContentValues values = new ContentValues();
				values.put(TagsMetaData.Table.COLUMN_CAPTION, tag);
				_context.getContentResolver().insert(TagsMetaData.CONTENT_URI, values);

				Cursor cNewTag = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, tagColumns, tagColumns[1] + " = '" + tag + "'", null, null);
				if (cNewTag != null) {
					if (cNewTag.moveToFirst()) {
						String caption = cNewTag.getString(cNewTag.getColumnIndex(tagColumns[1]));
						Tag _newTag = new Tag(caption); 
						addHasTag(_newTag, media);
						result = 0;
					}
				} else {
					result = -1;
				}
			}
		}
		return result;
	}

	private void addHasTag(Tag tag, Media media) {
		ContentValues values = new ContentValues();
		values.put(HasTagMetaData.Table.COLUMN_IDTAG, tag.getId());
		values.put(HasTagMetaData.Table.COLUMN_IDMEDIA, media.getId());
		_context.getContentResolver().insert(HasTagMetaData.CONTENT_URI, values);
	}

	public long attachMediaToProfile(Media media, Profile profile, Profile owner) {
		if (media.isMPublic() || media.getOwnerId() == owner.getId()) {
			MediaProfileAccess mpaModel  = new MediaProfileAccess();
			mpaModel.setIdMedia(media.getId());
			mpaModel.setIdProfile(profile.getId());
			return mpa.insertMediaProfileAccess(mpaModel);
		} else {
			return -1;
		}
	}

	public long attachMediaToDepartment(Media media, Department department, Profile owner) {
		if (media.isMPublic() || media.getOwnerId() == owner.getId()) {
			MediaDepartmentAccess mdaModel = new MediaDepartmentAccess();
			mdaModel.setIdMedia(media.getId());
			mdaModel.setIdDepartment(department.getId());
			return mda.insertMediaDepartmentAccess(mdaModel);
		} else {
			return -1;
		}
	}
	
	public long attachSubMediaToMedia(Media subMedia, Media media) {
		HasLink hlModel = new HasLink();
		hlModel.setIdMedia(media.getId());
		hlModel.setIdSubMedia(subMedia.getId());
		
		return hl.insertHasLink(hlModel);
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
	 * Get all media
	 * @return List<Media>, containing all media
	 */
	public List<Media> getMedia() {
		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, null, null, null);

		List<Media> media = new ArrayList<Media>();
		media = cursorToMedia(c);

		c.close();
		return media;
	}
	
	public List<Media> getSubMediaByMedia(Media media) {
		List<Media> medias = new ArrayList<Media>();
		
		for (HasLink link : hl.getSubMediaByMedia(media)) {
			medias.add(getSingleMediaById(link.getIdSubMedia()));
		}
		
		return medias;
	}

	/**
	 * Get media by id
	 * @param id the id of the media
	 * @return the media or null
	 */
	public Media getSingleMediaById(long id) {
		Uri uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, id);
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
		List<Media> media = new ArrayList<Media>();
		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, MediaMetaData.Table.COLUMN_NAME + " LIKE '%" + name + "%'", null, null);
		
		if (c != null) {
			media = cursorToMedia(c);
			c.close();
		}
		return media;
	}

	public List<Media> getMediaByTags(String[] tags) {
		List<Media> mediaList = new ArrayList<Media>();

		for (String tag : tags) {
			String[] tagColumns = {TagsMetaData.Table.COLUMN_ID, TagsMetaData.Table.COLUMN_CAPTION};
			Cursor cTag = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, tagColumns, tagColumns[1] + " = '" + tag + "'", null, null);
			if (cTag != null) {
				if (cTag.moveToFirst()) {
					long tagId = cTag.getLong(cTag.getColumnIndex(tagColumns[0]));
					List<Long> mediaIdList = new ArrayList<Long>();
					String[] hasTagColumns = {HasTagMetaData.Table.COLUMN_IDTAG, HasTagMetaData.Table.COLUMN_IDMEDIA};
					Cursor cHasTag = _context.getContentResolver().query(HasTagMetaData.CONTENT_URI, hasTagColumns, 
							hasTagColumns[0] + " = '" + tagId + "'", null, null);
					if (cHasTag != null) {
						if (cHasTag.moveToFirst()) {
							while (!cHasTag.isAfterLast()) {
								long mediaId = cHasTag.getLong(cHasTag.getColumnIndex(hasTagColumns[1]));
								mediaIdList.add(mediaId);
							}
						}
						for (long mId : mediaIdList) {
							Cursor cMedia = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, 
									MediaMetaData.Table.COLUMN_ID + " = '" + mId + "'", null, null);
							if (cMedia != null) {
								if (cMedia.moveToFirst()) {
									while (!cMedia.isAfterLast()) {
										mediaList.add(cursorToSingleMedia(cMedia));
									}
								}
							}
							cMedia.close();
						}
					}
					cHasTag.close();
				}
			}
			cTag.close();
		}

		return mediaList;
	}

	public List<Media> getMediaByDepartment(Department department) {
		List<Media> mediaList = new ArrayList<Media>();

		for (MediaDepartmentAccess mdaModel : mda.getMediaByDepartment(department)) {
			mediaList.add(getMediaById(mdaModel.getIdMedia()));
		}
		
		return mediaList;
	}

	public List<Media> getMediaByProfile(Profile profile) {
		List<Media> mediaList = new ArrayList<Media>();

		for (MediaProfileAccess mpaModel : mpa.getMediaByProfile(profile)) {
			mediaList.add(getMediaById(mpaModel.getIdMedia()));
		}
		
		return mediaList;
	}

	public Media getMediaById(long id) {
		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, MediaMetaData.Table.COLUMN_ID + " = '" + id + "'", null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				return cursorToSingleMedia(c);
			}
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - Keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
		media.setMPath(cursor.getString(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_PATH)));
		media.setMType(cursor.getString(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_TYPE)));
		media.setOwnerId(cursor.getLong(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_OWNERID)));
		if (cursor.getLong(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_PUBLIC)) == 1) {
			media.setMPublic(true);
		} else {
			media.setMPublic(false);
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
		contentValues.put(MediaMetaData.Table.COLUMN_PATH, media.getMPath());
		contentValues.put(MediaMetaData.Table.COLUMN_NAME, media.getName());
		contentValues.put(MediaMetaData.Table.COLUMN_PUBLIC, media.isMPublic());
		contentValues.put(MediaMetaData.Table.COLUMN_TYPE, media.getMType());
		contentValues.put(MediaMetaData.Table.COLUMN_OWNERID, media.getOwnerId());

		return contentValues;
	}
}