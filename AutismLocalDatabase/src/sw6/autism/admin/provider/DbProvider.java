package sw6.autism.admin.provider;

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
	private static final int SETTINGS_TYPE_LIST = 9;
	private static final int SETTINGS_TYPE_ONE = 10;
	private static final int STATS_TYPE_LIST = 11;
	private static final int STATS_TYPE_ONE = 12;
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
		sUriMatcher.addURI(DbHelper.AUTHORITY, "settings", SETTINGS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "settings/#", SETTINGS_TYPE_ONE);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "stats", STATS_TYPE_LIST);
		sUriMatcher.addURI(DbHelper.AUTHORITY, "stats/#", STATS_TYPE_ONE);
	}

	private static final HashMap<String, String> appsProjectionMap;
	static {
		appsProjectionMap = new HashMap<String, String>();
		appsProjectionMap.put(AppsMetaData.AppsTable.COLUMN_ID, AppsMetaData.AppsTable.COLUMN_ID);
		appsProjectionMap.put(AppsMetaData.AppsTable.COLUMN_NAME, AppsMetaData.AppsTable.COLUMN_NAME);
	}

	private static final HashMap<String, String> departmentsProjectionMap;
	static {
		departmentsProjectionMap = new HashMap<String, String>();
		departmentsProjectionMap.put(DepartmentsMetaData.DepartmentsTable.COLUMN_ID, DepartmentsMetaData.DepartmentsTable.COLUMN_ID);
		departmentsProjectionMap.put(DepartmentsMetaData.DepartmentsTable.COLUMN_NAME, DepartmentsMetaData.DepartmentsTable.COLUMN_NAME);
		departmentsProjectionMap.put(DepartmentsMetaData.DepartmentsTable.COLUMN_PHONE, DepartmentsMetaData.DepartmentsTable.COLUMN_PHONE);
	}

	private static final HashMap<String, String> mediaProjectionMap;
	static {
		mediaProjectionMap = new HashMap<String, String>();
		mediaProjectionMap.put(MediaMetaData.MediaTable.COLUMN_ID, MediaMetaData.MediaTable.COLUMN_ID);
		mediaProjectionMap.put(MediaMetaData.MediaTable.COLUMN_NAME, MediaMetaData.MediaTable.COLUMN_NAME);
	}

	private static final HashMap<String, String> profilesProjectionMap;
	static {
		profilesProjectionMap = new HashMap<String, String>();
		profilesProjectionMap.put(ProfilesMetaData.ProfilesTable.COLUMN_ID, ProfilesMetaData.ProfilesTable.COLUMN_ID);
		profilesProjectionMap.put(ProfilesMetaData.ProfilesTable.COLUMN_FIRST_NAME, ProfilesMetaData.ProfilesTable.COLUMN_FIRST_NAME);
		profilesProjectionMap.put(ProfilesMetaData.ProfilesTable.COLUMN_ROLE, ProfilesMetaData.ProfilesTable.COLUMN_ROLE);
	}

	private static final HashMap<String, String> settingsProjectionMap;
	static {
		settingsProjectionMap = new HashMap<String, String>();
		settingsProjectionMap.put(SettingsMetaData.SettingsTable.COLUMN_ID, SettingsMetaData.SettingsTable.COLUMN_ID);
		settingsProjectionMap.put(SettingsMetaData.SettingsTable.COLUMN_OWNER, SettingsMetaData.SettingsTable.COLUMN_OWNER);
		settingsProjectionMap.put(SettingsMetaData.SettingsTable.COLUMN_SETTINGS, SettingsMetaData.SettingsTable.COLUMN_SETTINGS);
	}

	private static final HashMap<String, String> statsProjectionMap;
	static {
		statsProjectionMap = new HashMap<String, String>();
		statsProjectionMap.put(StatsMetaData.StatsTable.COLUMN_ID, StatsMetaData.StatsTable.COLUMN_ID);
		statsProjectionMap.put(StatsMetaData.StatsTable.COLUMN_OWNER, StatsMetaData.StatsTable.COLUMN_OWNER);
		statsProjectionMap.put(StatsMetaData.StatsTable.COLUMN_STATS, StatsMetaData.StatsTable.COLUMN_STATS);
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
			rowsDeleted = db.delete(AppsMetaData.AppsTable.TABLE_NAME, where, whereArgs); 
			break;
		case APPS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(AppsMetaData.AppsTable.TABLE_NAME, 
					AppsMetaData.AppsTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case DEPARTMENTS_TYPE_LIST:
			rowsDeleted = db.delete(DepartmentsMetaData.DepartmentsTable.TABLE_NAME, where, whereArgs);
			break;
		case DEPARTMENTS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(DepartmentsMetaData.DepartmentsTable.TABLE_NAME, 
					DepartmentsMetaData.DepartmentsTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case MEDIA_TYPE_LIST:
			rowsDeleted = db.delete(MediaMetaData.MediaTable.TABLE_NAME, where, whereArgs);
			break;
		case MEDIA_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(MediaMetaData.MediaTable.TABLE_NAME, 
					MediaMetaData.MediaTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case PROFILES_TYPE_LIST:
			rowsDeleted = db.delete(ProfilesMetaData.ProfilesTable.TABLE_NAME, where, whereArgs);
			break;
		case PROFILES_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(ProfilesMetaData.ProfilesTable.TABLE_NAME, 
					ProfilesMetaData.ProfilesTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case SETTINGS_TYPE_LIST:
			rowsDeleted = db.delete(SettingsMetaData.SettingsTable.TABLE_NAME, where, whereArgs);
			break;
		case SETTINGS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(SettingsMetaData.SettingsTable.TABLE_NAME, 
					SettingsMetaData.SettingsTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
					whereArgs);
			break;
		case STATS_TYPE_LIST:
			rowsDeleted = db.delete(StatsMetaData.StatsTable.TABLE_NAME, where, whereArgs);
			break;
		case STATS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsDeleted = db.delete(StatsMetaData.StatsTable.TABLE_NAME, 
					StatsMetaData.StatsTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ")" : ""),
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
		case SETTINGS_TYPE_LIST:
			return SettingsMetaData.CONTENT_TYPE_SETTINGS_LIST;
		case SETTINGS_TYPE_ONE:
			return SettingsMetaData.CONTENT_TYPE_SETTING_ONE;
		case STATS_TYPE_LIST:
			return StatsMetaData.CONTENT_TYPE_STATS_LIST;
		case STATS_TYPE_ONE:
			return StatsMetaData.CONTENT_TYPE_STAT_ONE;
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
			rowId = db.insert(AppsMetaData.AppsTable.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(AppsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case DEPARTMENTS_TYPE_LIST:
			rowId = db.insert(DepartmentsMetaData.DepartmentsTable.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case MEDIA_TYPE_LIST:
			rowId = db.insert(MediaMetaData.MediaTable.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case PROFILES_TYPE_LIST:
			rowId = db.insert(ProfilesMetaData.ProfilesTable.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case SETTINGS_TYPE_LIST:
			rowId = db.insert(SettingsMetaData.SettingsTable.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(SettingsMetaData.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
		case STATS_TYPE_LIST:
			rowId = db.insert(StatsMetaData.StatsTable.TABLE_NAME, null, values);
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(StatsMetaData.CONTENT_URI, rowId);
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
			builder.setTables(AppsMetaData.AppsTable.TABLE_NAME);
			builder.setProjectionMap(appsProjectionMap);
			break;
		case APPS_TYPE_ONE:
			builder.setTables(AppsMetaData.AppsTable.TABLE_NAME);
			builder.setProjectionMap(appsProjectionMap);
			builder.appendWhere(AppsMetaData.AppsTable.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case DEPARTMENTS_TYPE_LIST:
			builder.setTables(DepartmentsMetaData.DepartmentsTable.TABLE_NAME);
			builder.setProjectionMap(departmentsProjectionMap);
			break;
		case DEPARTMENTS_TYPE_ONE:
			builder.setTables(DepartmentsMetaData.DepartmentsTable.TABLE_NAME);
			builder.setProjectionMap(departmentsProjectionMap);
			builder.appendWhere(DepartmentsMetaData.DepartmentsTable.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case MEDIA_TYPE_LIST:
			builder.setTables(MediaMetaData.MediaTable.TABLE_NAME);
			builder.setProjectionMap(mediaProjectionMap);
			break;
		case MEDIA_TYPE_ONE:
			builder.setTables(MediaMetaData.MediaTable.TABLE_NAME);
			builder.setProjectionMap(mediaProjectionMap);
			builder.appendWhere(MediaMetaData.MediaTable.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case PROFILES_TYPE_LIST:
			builder.setTables(ProfilesMetaData.ProfilesTable.TABLE_NAME);
			builder.setProjectionMap(profilesProjectionMap);
			break;
		case PROFILES_TYPE_ONE:
			builder.setTables(ProfilesMetaData.ProfilesTable.TABLE_NAME);
			builder.setProjectionMap(profilesProjectionMap);
			builder.appendWhere(ProfilesMetaData.ProfilesTable.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case SETTINGS_TYPE_LIST:
			builder.setTables(SettingsMetaData.SettingsTable.TABLE_NAME);
			builder.setProjectionMap(settingsProjectionMap);
			break;
		case SETTINGS_TYPE_ONE:
			builder.setTables(SettingsMetaData.SettingsTable.TABLE_NAME);
			builder.setProjectionMap(settingsProjectionMap);
			builder.appendWhere(SettingsMetaData.SettingsTable.COLUMN_ID + " = " + uri.getPathSegments().get(1));
			break;
		case STATS_TYPE_LIST:
			builder.setTables(StatsMetaData.StatsTable.TABLE_NAME);
			builder.setProjectionMap(statsProjectionMap);
			break;
		case STATS_TYPE_ONE:
			builder.setTables(StatsMetaData.StatsTable.TABLE_NAME);
			builder.setProjectionMap(statsProjectionMap);
			builder.appendWhere(StatsMetaData.StatsTable.COLUMN_ID + " = " + uri.getPathSegments().get(1));
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
			rowsUpdated = db.update(AppsMetaData.AppsTable.TABLE_NAME, values, where, whereArgs);
			break;
		case APPS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(AppsMetaData.AppsTable.TABLE_NAME, 
					values, 
					AppsMetaData.AppsTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + ")" : ""),
					whereArgs);
			break;
		case DEPARTMENTS_TYPE_LIST:
			rowsUpdated = db.update(DepartmentsMetaData.DepartmentsTable.TABLE_NAME, values, where, whereArgs);
			break;
		case DEPARTMENTS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(DepartmentsMetaData.DepartmentsTable.TABLE_NAME, 
					values, 
					DepartmentsMetaData.DepartmentsTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + ")" : ""),
					whereArgs);
			break;
		case MEDIA_TYPE_LIST:
			rowsUpdated = db.update(MediaMetaData.MediaTable.TABLE_NAME, values, where, whereArgs);
			break;
		case MEDIA_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(MediaMetaData.MediaTable.TABLE_NAME, 
					values, 
					MediaMetaData.MediaTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + ")" : ""),
					whereArgs);
			break;
		case PROFILES_TYPE_LIST:
			rowsUpdated = db.update(ProfilesMetaData.ProfilesTable.TABLE_NAME, values, where, whereArgs);
			break;
		case PROFILES_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(ProfilesMetaData.ProfilesTable.TABLE_NAME, 
					values, 
					ProfilesMetaData.ProfilesTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + ")" : ""),
					whereArgs);
			break;
		case SETTINGS_TYPE_LIST:
			rowsUpdated = db.update(SettingsMetaData.SettingsTable.TABLE_NAME, values, where, whereArgs);
			break;
		case SETTINGS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(SettingsMetaData.SettingsTable.TABLE_NAME, 
					values, 
					SettingsMetaData.SettingsTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + ")" : ""),
					whereArgs);
			break;
		case STATS_TYPE_LIST:
			rowsUpdated = db.update(StatsMetaData.StatsTable.TABLE_NAME, values, where, whereArgs);
			break;
		case STATS_TYPE_ONE:
			rowId = uri.getPathSegments().get(1);
			rowsUpdated = db.update(StatsMetaData.StatsTable.TABLE_NAME, 
					values, 
					StatsMetaData.StatsTable.COLUMN_ID + " = " + rowId + (!TextUtils.isEmpty(where) ? " AND (" + ")" : ""),
					whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}


}
