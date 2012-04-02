package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class MediaProfileAccessMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/mediaprofileaccess");

	public static final String CONTENT_TYPE_MEDIAPROFILEACCESS_LIST = "vnd.android.cursor.dir/vnd.sw6.mediaprofileaccess";
	public static final String CONTENT_TYPE_MEDIAPROFILEACCESS_ONE = "vnd.android.cursor.item/vnd.sw6.mediaprofileaccess";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_mediaprofileaccess";

		public static final String COLUMN_PROFILEID = "mediaprofileaccess_profileid";
		public static final String COLUMN_MEDIAID = "mediaprofileaccess_mediaid";
	}
}
