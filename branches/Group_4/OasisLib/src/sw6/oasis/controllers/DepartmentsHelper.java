package sw6.oasis.controllers;

import java.util.ArrayList;
import java.util.List;

import sw6.oasis.controllers.metadata.DepartmentsMetaData;
import sw6.oasis.viewmodels.Department;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Helper class for Departments
 * @author Admin
 *
 */
public class DepartmentsHelper {

	private static Context _context;
	/**
	 * Constructor
	 * @param context Current context
	 */
	public DepartmentsHelper(Context context){
		_context = context;
	}
	
	/**
	 * Insert department
	 * @param _department Department containg data
	 */
	public void insertDepartment(Department _department) {
		ContentValues cv = new ContentValues();
		cv.put(DepartmentsMetaData.Table.COLUMN_NAME, _department.getName());
		cv.put(DepartmentsMetaData.Table.COLUMN_ADDRESS, _department.getAddress());
		cv.put(DepartmentsMetaData.Table.COLUMN_PHONE, _department.getPhone());
		_context.getContentResolver().insert(DepartmentsMetaData.CONTENT_URI, cv);
	}
	
	/**
	 * Modify department
	 * @param _department Department containing data to modify
	 */
	public void modifyDepartment(Department _department) {
		Uri uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, _department.getId());
		ContentValues cv = new ContentValues();
		cv.put(DepartmentsMetaData.Table.COLUMN_NAME, _department.getName());
		cv.put(DepartmentsMetaData.Table.COLUMN_ADDRESS, _department.getAddress());
		cv.put(DepartmentsMetaData.Table.COLUMN_PHONE, _department.getPhone());
		_context.getContentResolver().update(uri, cv, null, null);
	}
	
	/**
	 * Get all departments
	 * @return List<Department>, containing all departments
	 */
	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList<Department>();
		String[] columns = new String[] { DepartmentsMetaData.Table.COLUMN_ID, 
										  DepartmentsMetaData.Table.COLUMN_NAME,
										  DepartmentsMetaData.Table.COLUMN_PHONE,
										  DepartmentsMetaData.Table.COLUMN_ADDRESS};
		Cursor c = _context.getContentResolver().query(DepartmentsMetaData.CONTENT_URI, columns, null, null, null);
		
		if(c.isFirst()) {
		while(c.isAfterLast()) {
			departments.add(cursorToDepartment(c));
			c.moveToNext();
		}
		}

		return departments;
	}
	
	/**
	 * Clear department table
	 */
	public void clearDepartmentsTable() {
		_context.getContentResolver().delete(DepartmentsMetaData.CONTENT_URI, null, null);
	}
	
	/**
	 * Cursor to department
	 * @param cursor Input cursor
	 * @return Output department
	 */
	private Department cursorToDepartment(Cursor cursor) {
		Department department = new Department();
		department.setId(cursor.getLong(cursor.getColumnIndex(DepartmentsMetaData.Table.COLUMN_ID)));
		department.setName(cursor.getString(cursor.getColumnIndex(DepartmentsMetaData.Table.COLUMN_NAME)));
		department.setAddress(cursor.getString(cursor.getColumnIndex(DepartmentsMetaData.Table.COLUMN_ADDRESS)));
		department.setPhone(cursor.getLong(cursor.getColumnIndex(DepartmentsMetaData.Table.COLUMN_PHONE)));
		return department;
	}
}
