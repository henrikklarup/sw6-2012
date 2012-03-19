package sw6.autism.admin.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class AppsMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/apps");
	
	public static final String CONTENT_TYPE_APPS_LIST = "vnd.android.cursor.dir/vnd.sw6.apps";
	public static final String CONTENT_TYPE_APP_ONE = "vnd.android.cursor.item/vnd.sw6.apps";
	
	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_apps";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "apps_name";
		public static final String COLUMN_VERSIONNUMBER = "apps_version";
	}
}
