package sw6.autism.admin.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class StatsMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/stats");
	
	public static final String CONTENT_TYPE_STATS_LIST = "vnd.android.cursor.dir/vnd.sw6.stats";
	public static final String CONTENT_TYPE_STAT_ONE = "vnd.android.cursor.item/vnd.sw6.stats";
	
	public class StatsTable implements BaseColumns {
		public static final String TABLE_NAME = "tbl_stats";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_STATS = "stats_stats"; //NICE NAVN
		public static final String COLUMN_OWNER = "stats_owner";
	}
}
