package sw6.oasis.controllers.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class CertificatesMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://sw6.autism.admin.provider.AutismProvider/certificates");

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_certificates";

		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_AUTHKEY = "certificates_authkey";
	}
}
