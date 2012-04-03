package dk.aau.cs.giraf.oasis.lib.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class AuthUsersMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/certificates");

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_certificates";

		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_CERTIFICATE = "authusers_certificate";
	}
}
