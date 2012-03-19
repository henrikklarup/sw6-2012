package dk.sw6.admin.controllers;

import android.net.Uri;
import android.provider.BaseColumns;

public class MediasMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/media");
	
	public class MediaTable implements BaseColumns {
		public static final String TABLE_NAME = "tbl_media";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "media_name";
	}
}
