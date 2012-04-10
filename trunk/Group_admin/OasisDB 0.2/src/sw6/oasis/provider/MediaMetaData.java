package sw6.oasis.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class MediaMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.oasis.provider.AutismProvider/media");
	
	public static final String CONTENT_TYPE_MEDIA_LIST = "vnd.android.cursor.dir/vnd.sw6.media";
	public static final String CONTENT_TYPE_MEDIA_ONE = "vnd.android.cursor.item/vnd.sw6.media";
	
	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_media";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "media_name";
		public static final String COLUMN_PATH = "media_path";
		public static final String COLUMN_PUBLIC = "media_public";
		public static final String COLUMN_TYPE = "media_type";
		public static final String COLUMN_TAGS = "media_tags";
		public static final String COLUMN_OWNERID = "media_ownerid";
	}
}
