package sw6.oasis.controllers.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class ListOfAppsMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://sw6.oasis.provider.AutismProvider/listofapps");

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_listofapps";

		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_APPSID = "listofapps_appsid";
		public static final String COLUMN_PROFILESID = "listofapps_profilesid";
		public static final String COLUMN_SETTINGS = "listofapps_settings";
		public static final String COLUMN_STATS = "listofapps_stats";
	}
}
