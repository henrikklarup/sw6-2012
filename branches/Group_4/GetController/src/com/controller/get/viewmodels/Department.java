package com.controller.get.viewmodels;

import android.net.Uri;
import android.provider.BaseColumns;

public class Department {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/departments");
	
	public class DepartmentsTable implements BaseColumns {
		public static final String TABLE_NAME = "tbl_departments";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "departments_name";
		public static final String COLUMN_PHONE = "departments_phone";
	}
}
