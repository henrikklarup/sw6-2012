package sw6.oasis.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class DepartmentsMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.oasis.provider.AutismProvider/departments");
	
	public static final String CONTENT_TYPE_DEPARTMENTS_LIST = "vnd.android.cursor.dir/vnd.sw6.departments";
	public static final String CONTENT_TYPE_DEPARTMENT_ONE = "vnd.android.cursor.item/vnd.sw6.departments";
	
	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_departments";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "departments_name";
		public static final String COLUMN_ADDRESS = "departments_address";
		public static final String COLUMN_PHONE = "departments_phone";
	}
}
