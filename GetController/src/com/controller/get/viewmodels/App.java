package com.controller.get.viewmodels;

import android.net.Uri;
import android.provider.BaseColumns;

public class App {

	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/apps");
	
	public class AppsTable implements BaseColumns {
		public static final String TABLE_NAME = "tbl_apps";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "apps_name";
	}
}
