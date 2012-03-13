package sw6.autism.admin.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class ProfilesMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/profiles");
	
	public static final String CONTENT_TYPE_PROFILES_LIST = "vnd.android.cursor.dir/vnd.sw6.profiles";
	public static final String CONTENT_TYPE_PROFILE_ONE = "vnd.android.cursor.item/vnd.sw6.profiles";
	
	public class ProfilesTable implements BaseColumns {
		public static final String TABLE_NAME = "tbl_profiles";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_FIRST_NAME = "profile_first_name";
		public static final String COLUMN_ROLE = "profile_role";
	}
}
