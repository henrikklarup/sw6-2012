package sw6.oasis.controllers.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class DepartmentsMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.oasis.provider.AutismProvider/departments");
	
	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_departments";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "departments_name";
		public static final String COLUMN_ADDRESS = "departments_address";
		public static final String COLUMN_PHONE = "departments_phone";
	}
}
