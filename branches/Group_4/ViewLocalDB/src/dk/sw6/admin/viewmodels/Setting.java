package dk.sw6.admin.viewmodels;

import android.net.Uri;
import android.provider.BaseColumns;

public class Setting {

	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/settings");
	
	public class SettingsTable implements BaseColumns {
		public static final String TABLE_NAME = "tbl_settings";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_SETTINGS = "settings_settings"; //NICE NAVN
		public static final String COLUMN_OWNER = "apps_owner";
	}
}
