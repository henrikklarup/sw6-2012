package sw6.autism.admin.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class SettingsMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/settings");
	
	public static final String CONTENT_TYPE_SETTINGS_LIST = "vnd.android.cursor.dir/vnd.sw6.settings";
	public static final String CONTENT_TYPE_SETTING_ONE = "vnd.android.cursor.item/vnd.sw6.settings";
	
	public class SettingsTable implements BaseColumns {
		public static final String TABLE_NAME = "tbl_settings";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_SETTINGS = "settings_settings"; //NICE NAVN
		public static final String COLUMN_OWNER = "apps_owner";
	}
}
