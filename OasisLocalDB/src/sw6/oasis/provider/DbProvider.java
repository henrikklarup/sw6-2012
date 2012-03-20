package sw6.oasis.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DbProvider extends ContentProvider {

	private DbHelper dbHelper;
	private static final UriMatcher sUriMatcher;
	private static final int APPS_TYPE_LIST = 1;
	private static final int APPS_TYPE_ONE = 2;
	private static final int DEPARTMENTS_TYPE_LIST = 3;
	private static final int DEPARTMENTS_TYPE_ONE = 4;
	private static final int MEDIA_TYPE_LIST = 5;
	private static final int MEDIA_TYPE_ONE = 6;
	private static final int PROFILES_TYPE_LIST = 7;
	private static final int PROFILES_TYPE_ONE = 8;
	private static final int CERTIFICATES_TYPE_LIST = 9;
	private static final int CERTIFICATES_TYPE_ONE = 10;
	private static final int LISTOFAPPS_TYPE_LIST = 11;
	private static final int LISTOFAPPS_TYPE_ONE = 12;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "apps", APPS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "apps/#", APPS_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "departments", DEPARTMENTS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "departments/#", DEPARTMENTS_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "media", MEDIA_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "media/#", MEDIA_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "profiles", PROFILES_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "profiles/#", PROFILES_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "certificates", CERTIFICATES_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "certificates/#", CERTIFICATES_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "listofapps", LISTOFAPPS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "listofapps/#", LISTOFAPPS_TYPE_ONE);
	}

	private static final HashMap<String, String> appsProjectionMap;
	static {
		appsProjectionMap = new HashMap<String, String>();
		appsProjectionMap.put(AppsMetaData.Table.COLUMN_ID, AppsMetaData.Table.COLUMN_ID);
		appsProjectionMap.put(AppsMetaData.Table.COLUMN_NAME, AppsMetaData.Table.COLUMN_NAME);
		appsProjectionMap.put(AppsMetaData.Table.COLUMN_VERSIONNUMBER, AppsMetaData.Table.COLUMN_VERSIONNUMBER);
	}

	private static final HashMap<String, String> departmentsProjectionMap;
	static {
		departmentsProjectionMap = new HashMap<String, String>();
		departmentsProjectionMap.put(DepartmentsMetaData.Table.COLUMN_ID, DepartmentsMetaData.Table.COLUMN_ID);
		departmentsProjectionMap.put(DepartmentsMetaData.Table.COLUMN_NAME, DepartmentsMetaData.Table.COLUMN_NAME);
		departmentsProjectionMap.put(DepartmentsMetaData.Table.COLUMN_ADDRESS, DepartmentsMetaData.Table.COLUMN_ADDRESS);
		departmentsProjectionMap.put(DepartmentsMetaData.Table.COLUMN_PHONE, DepartmentsMetaData.Table.COLUMN_PHONE);
	}

	private static final HashMap<String, String> mediaProjectionMap;
	static {
		mediaProjectionMap = new HashMap<String, String>();
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_ID, MediaMetaData.Table.COLUMN_ID);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_NAME, MediaMetaData.Table.COLUMN_NAME);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_PATH, MediaMetaData.Table.COLUMN_PATH);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_PUBLIC, MediaMetaData.Table.COLUMN_PUBLIC);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_TYPE, MediaMetaData.Table.COLUMN_TYPE);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_TAGS, MediaMetaData.Table.COLUMN_TAGS);
		mediaProjectionMap.put(MediaMetaData.Table.COLUMN_OWNERID, MediaMetaData.Table.COLUMN_OWNERID);
	}

	private static final HashMap<String, String> profilesProjectionMap;
	static {
		profilesProjectionMap = new HashMap<String, String>();
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_ID, ProfilesMetaData.Table.COLUMN_ID);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_FIRST_NAME, ProfilesMetaData.Table.COLUMN_FIRST_NAME);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_SUR_NAME, ProfilesMetaData.Table.COLUMN_SUR_NAME);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_MIDDLE_NAME, ProfilesMetaData.Table.COLUMN_MIDDLE_NAME);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_ROLE, ProfilesMetaData.Table.COLUMN_ROLE);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_PHONE, ProfilesMetaData.Table.COLUMN_PHONE);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_PICTURE, ProfilesMetaData.Table.COLUMN_PICTURE);
		profilesProjectionMap.put(ProfilesMetaData.Table.COLUMN_DEPARTMENTID, ProfilesMetaData.Table.COLUMN_DEPARTMENTID);
	}

	private static final HashMap<String, String> certificatesProjectionMap;
	static {
		certificatesProjectionMap = new HashMap<String, String>();
		certificatesProjectionMap.put(CertificatesMetaData.Table.COLUMN_ID, CertificatesMetaData.Table.COLUMN_ID);
		certificatesProjectionMap.put(CertificatesMetaData.Table.COLUMN_AUTHKEY, CertificatesMetaData.Table.COLUMN_AUTHKEY);
	}

	private static final HashMap<String, String> listofappsProjectionMap;
	static {
		listofappsProjectionMap = new HashMap<String, String>();
		listofappsProjectionMap.put(ListOfAppsMetaData.Table.COLUMN_ID, ListOfAppsMetaData.Table.COLUMN_ID);
		listofappsProjectionMap.put(ListOfAppsMetaData.Table.COLUMN_APPSID, ListOfAppsMetaData.Table.COLUMN_APPSID);
		listofappsProjectionMap.put(ListOfAppsMetaData.Table.COLUMN_PROFILESID, ListOfAppsMetaData.Table.COLUMN_PROFILESID);
		listofappsProjectionMap.put(ListOfAppsMetaData.Table.COLUMN_SETTINGS, ListOfAppsMetaData.Table.COLUMN_SETTINGS);
		listofappsProjectionMap.put(ListOfAppsMetaData.Table.COLUMN_STATS, ListOfAppsMetaData.Table.COLUMN_STATS);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return false;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rowsDeleted = 0;
		String rowId;
		
		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			rowsDeleted = db.delete(AppsMetaData.Table.TABLE_NAME, where, whereArgs); 
			break;
		case APPS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(AppsMetaData.Table.TABLE_NAME, 
					AppsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case DEPARTMENTS_TYPE_LIST:
			rowsDeleted = db.delete(DepartmentsMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case DEPARTMENTS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(DepartmentsMetaData.Table.TABLE_NAME, 
					DepartmentsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case MEDIA_TYPE_LIST:
			rowsDeleted = db.delete(MediaMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case MEDIA_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(MediaMetaData.Table.TABLE_NAME, 
					MediaMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case PROFILES_TYPE_LIST:
			rowsDeleted = db.delete(ProfilesMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case PROFILES_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(ProfilesMetaData.Table.TABLE_NAME, 
					ProfilesMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case CERTIFICATES_TYPE_LIST:
			rowsDeleted = db.delete(CertificatesMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case CERTIFICATES_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(CertificatesMetaData.Table.TABLE_NAME, 
					CertificatesMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case LISTOFAPPS_TYPE_LIST:
			rowsDeleted = db.delete(ListOfAppsMetaData.Table.TABLE_NAME, where, whereArgs);
			break;
		case LISTOFAPPS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(ListOfAppsMetaData.Table.TABLE_NAME, 
					ListOfAppsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			return AppsMetaData.CONTENT_TYPE_APPS_LIST;
		case APPS_TYPE_ONE:
			return AppsMetaData.CONTENT_TYPE_APP_ONE;
		case DEPARTMENTS_TYPE_LIST:
			return DepartmentsMetaData.CONTENT_TYPE_DEPARTMENTS_LIST;
		case DEPARTMENTS_TYPE_ONE:
			return DepartmentsMetaData.CONTENT_TYPE_DEPARTMENT_ONE;
		case MEDIA_TYPE_LIST:
			return MediaMetaData.CONTENT_TYPE_MEDIA_LIST;
		case MEDIA_TYPE_ONE:
			return MediaMetaData.CONTENT_TYPE_MEDIA_ONE;
		case PROFILES_TYPE_LIST:
			return ProfilesMetaData.CONTENT_TYPE_PROFILES_LIST;
		case PROFILES_TYPE_ONE:
			return ProfilesMetaData.CONTENT_TYPE_PROFILE_ONE;
		case CERTIFICATES_TYPE_LIST:
			return CertificatesMetaData.CONTENT_TYPE_CERTIFICATES_LIST;
		case CERTIFICATES_TYPE_ONE:
			return CertificatesMetaData.CONTENT_TYPE_CERTIFICATE_ONE;
		case LISTOFAPPS_TYPE_LIST:
			return ListOfAppsMetaData.CONTENT_TYPE_LISTOFAPPS_LIST;
		case LISTOFAPPS_TYPE_ONE:
			return ListOfAppsMetaData.CONTENT_TYPE_LISTOFAPP_ONE;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId;
		Uri _uri;
		
		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			rowId = db.insert(AppsMetaData.Table.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case DEPARTMENTS_TYPE_LIST:
			rowId = db.insert(DepartmentsMetaData.Table.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case MEDIA_TYPE_LIST:
			rowId = db.insert(MediaMetaData.Table.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case PROFILES_TYPE_LIST:
			rowId = db.insert(ProfilesMetaData.Table.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case CERTIFICATES_TYPE_LIST:
			rowId = db.insert(CertificatesMetaData.Table.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(CertificatesMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case LISTOFAPPS_TYPE_LIST:
			rowId = db.insert(ListOfAppsMetaData.Table.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(ListOfAppsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		
		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			builder.setTables(AppsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(appsProjectionMap);
			break;
		case APPS_TYPE_ONE:
			builder.setTables(AppsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(appsProjectionMap);
			builder.appendWhere(AppsMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case DEPARTMENTS_TYPE_LIST:
			builder.setTables(DepartmentsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(departmentsProjectionMap);
			break;
		case DEPARTMENTS_TYPE_ONE:
			builder.setTables(DepartmentsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(departmentsProjectionMap);
			builder.appendWhere(DepartmentsMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case MEDIA_TYPE_LIST:
			builder.setTables(MediaMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(mediaProjectionMap);
			break;
		case MEDIA_TYPE_ONE:
			builder.setTables(MediaMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(mediaProjectionMap);
			builder.appendWhere(MediaMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case PROFILES_TYPE_LIST:
			builder.setTables(ProfilesMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(profilesProjectionMap);
			break;
		case PROFILES_TYPE_ONE:
			builder.setTables(ProfilesMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(profilesProjectionMap);
			builder.appendWhere(ProfilesMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case CERTIFICATES_TYPE_LIST:
			builder.setTables(CertificatesMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(certificatesProjectionMap);
			break;
		case CERTIFICATES_TYPE_ONE:
			builder.setTables(CertificatesMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(certificatesProjectionMap);
			builder.appendWhere(CertificatesMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case LISTOFAPPS_TYPE_LIST:
			builder.setTables(ListOfAppsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(listofappsProjectionMap);
			break;
		case LISTOFAPPS_TYPE_ONE:
			builder.setTables(ListOfAppsMetaData.Table.TABLE_NAME);
			builder.setProjectionMap(listofappsProjectionMap);
			builder.appendWhere(ListOfAppsMetaData.Table.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor queryCursor = builder.query(db, projection, selection, selectionArgs, null, null, null);
		queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
		return queryCursor;
	}
	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rowsUpdated = 0;
		String rowId;
		
		switch(sUriMatcher.match(uri)) {
		case APPS_TYPE_LIST:
			rowsUpdated = db.update(AppsMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case APPS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(AppsMetaData.Table.TABLE_NAME, 
					values, 
					AppsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case DEPARTMENTS_TYPE_LIST:
			rowsUpdated = db.update(DepartmentsMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case DEPARTMENTS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(DepartmentsMetaData.Table.TABLE_NAME, 
					values, 
					DepartmentsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case MEDIA_TYPE_LIST:
			rowsUpdated = db.update(MediaMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case MEDIA_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(MediaMetaData.Table.TABLE_NAME, 
					values, 
					MediaMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case PROFILES_TYPE_LIST:
			rowsUpdated = db.update(ProfilesMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case PROFILES_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(ProfilesMetaData.Table.TABLE_NAME, 
					values, 
					ProfilesMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case CERTIFICATES_TYPE_LIST:
			rowsUpdated = db.update(CertificatesMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case CERTIFICATES_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(CertificatesMetaData.Table.TABLE_NAME, 
					values, 
					CertificatesMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case LISTOFAPPS_TYPE_LIST:
			rowsUpdated = db.update(ListOfAppsMetaData.Table.TABLE_NAME, values, where, whereArgs);
			break;
		case LISTOFAPPS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(ListOfAppsMetaData.Table.TABLE_NAME, 
					values, 
					ListOfAppsMetaData.Table.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}


}
