package sw6.oasis.controllers.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class ProfilesMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.oasis.provider.AutismProvider/profiles");
	
	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_profiles";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_FIRST_NAME = "profile_first_name";
		public static final String COLUMN_SUR_NAME = "profile_sur_name";
		public static final String COLUMN_MIDDLE_NAME = "profile_middle_name";
		public static final String COLUMN_ROLE = "profile_role";
		public static final String COLUMN_PHONE = "profile_phone";
		public static final String COLUMN_PICTURE = "profile_picture";
		public static final String COLUMN_DEPARTMENTID = "profile_departmentid";
	}
}
