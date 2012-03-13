package sw6.autism.admin.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class MediaMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/media");
	
	public static final String CONTENT_TYPE_MEDIA_LIST = "vnd.android.cursor.dir/vnd.sw6.media";
	public static final String CONTENT_TYPE_MEDIA_ONE = "vnd.android.cursor.item/vnd.sw6.media";
	
	public class MediaTable implements BaseColumns {
		public static final String TABLE_NAME = "tbl_media";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "media_name";
	}
}
