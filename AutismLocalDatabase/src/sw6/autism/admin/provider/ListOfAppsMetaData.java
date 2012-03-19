package sw6.autism.admin.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class ListOfAppsMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/listofapps");

	public static final String CONTENT_TYPE_LISTOFAPPS_LIST = "vnd.android.cursor.dir/vnd.sw6.listofapps";
	public static final String CONTENT_TYPE_LISTOFAPP_ONE = "vnd.android.cursor.item/vnd.sw6.listofapps";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_listofapps";

		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_APPSID = "listofapps_appsid";
		public static final String COLUMN_PROFILESID = "listofapps_profilesid";
		public static final String COLUMN_SETTINGS = "listofapps_settings";
		public static final String COLUMN_STATS = "listofapps_stats";
	}
}
