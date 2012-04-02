package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class HasLinkMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/haslink");

	public static final String CONTENT_TYPE_HASLINKS_LIST = "vnd.android.cursor.dir/vnd.sw6.haslink";
	public static final String CONTENT_TYPE_HASLINK_ONE = "vnd.android.cursor.item/vnd.sw6.haslink";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_apps";

		public static final String COLUMN_IDPARENT = "haslink_parentid";
		public static final String COLUMN_IDCHILD = "haslink_childid";
	}
}
