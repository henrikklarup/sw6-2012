package sw6.oasis.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class CertificatesMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://sw6.oasis.provider.AutismProvider/certificates");

	public static final String CONTENT_TYPE_CERTIFICATES_LIST = "vnd.android.cursor.dir/vnd.sw6.certificates";
	public static final String CONTENT_TYPE_CERTIFICATE_ONE = "vnd.android.cursor.item/vnd.sw6.certificates";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_certificates";

		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_AUTHKEY = "certificates_authkey";
	}
}
