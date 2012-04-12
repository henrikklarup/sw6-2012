package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.DepartmentsMetaData;
import dk.aau.cs.giraf.oasis.lib.metadata.HasDepartmentMetaData;
import dk.aau.cs.giraf.oasis.lib.metadata.HasSubDepartmentMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Helper class for Departments
 * 
 * @author Admin
 * 
 */
public class DepartmentsHelper {

	private static Context _context;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            Current context
	 */
	public DepartmentsHelper(Context context) {
		_context = context;
	}

	/**
	 * Insert department
	 * 
	 * @param _department
	 *            Department containg data
	 */
	public void insertDepartment(Department _department) {
		ContentValues cv = new ContentValues();
		cv.put(DepartmentsMetaData.Table.COLUMN_NAME, _department.getName());
		cv.put(DepartmentsMetaData.Table.COLUMN_ADDRESS,
				_department.getAddress());
		cv.put(DepartmentsMetaData.Table.COLUMN_PHONE, _department.getPhone());
		_context.getContentResolver().insert(DepartmentsMetaData.CONTENT_URI,
				cv);
	}

	/**
	 * Modify department
	 * 
	 * @param _department
	 *            Department containing data to modify
	 */
	public void modifyDepartment(Department _department) {
		Uri uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI,
				_department.getId());
		ContentValues cv = new ContentValues();
		cv.put(DepartmentsMetaData.Table.COLUMN_NAME, _department.getName());
		cv.put(DepartmentsMetaData.Table.COLUMN_ADDRESS,
				_department.getAddress());
		cv.put(DepartmentsMetaData.Table.COLUMN_PHONE, _department.getPhone());
		_context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Get all departments
	 * 
	 * @return List<Department>, containing all departments
	 */
	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList<Department>();
		String[] columns = new String[] { DepartmentsMetaData.Table.COLUMN_ID,
				DepartmentsMetaData.Table.COLUMN_NAME,
				DepartmentsMetaData.Table.COLUMN_PHONE,
				DepartmentsMetaData.Table.COLUMN_ADDRESS };
		Cursor c = _context.getContentResolver().query(
				DepartmentsMetaData.CONTENT_URI, columns, null, null, null);

		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				departments.add(cursorToDepartment(c));
				c.moveToNext();
			}
		}

		c.close();

		return departments;
	}
	
	public int attachProfileToDepartment(Profile profile, Department department) {
			ContentValues values = new ContentValues();
			values.put(HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT, profile.getId());
			values.put(HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, department.getId());
			_context.getContentResolver().insert(HasSubDepartmentMetaData.CONTENT_URI, values);
			return 0;
	}
	
	public int removeProfileAttachmentToDepartment(Profile profile, Department department) {
			_context.getContentResolver().delete(HasSubDepartmentMetaData.CONTENT_URI, 
					HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + " = '" + profile.getId() + "'" +
					HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + department.getId() + "'", null);
			return 0;
	}
	
	public int attachSubDepartmentToDepartment(Department department, Department subDepartment) {
			ContentValues values = new ContentValues();
			values.put(HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT, subDepartment.getId());
			values.put(HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, department.getId());
			_context.getContentResolver().insert(HasDepartmentMetaData.CONTENT_URI, values);
			return 0;
	}
	
	public int removeSubDepartmentAttachmentToDepartment(Department department, Department subDepartment) {
			_context.getContentResolver().delete(HasSubDepartmentMetaData.CONTENT_URI, 
					HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + " = '" + subDepartment.getId() + "'" +
					HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + department.getId() + "'", null);
			return 0;
	}
	
	public Department getDepartmentById(long id) {
		Uri uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, id);
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if(c.moveToFirst()) {
				return cursorToDepartment(c);
			}
		}
		
		c.close();

		return null;
	}
	
	public List<Department> getDepartmentByName(String name) {
		List<Department> departments = new ArrayList<Department>();
		Cursor c = _context.getContentResolver().query(DepartmentsMetaData.CONTENT_URI, columns, DepartmentsMetaData.Table.COLUMN_NAME + " = '" + name + "'", null, null);
		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					cursorToDepartment(c);
					c.moveToNext();
				}
			}
		}
		
		c.close();
		
		return departments;
	}
	
	public List<Department> getSubDepartments(Department department) {
		List<Department> departments = new ArrayList<Department>();
		String[] hasDepartmentsColumns = {
				HasDepartmentMetaData.Table.COLUMN_IDPROFILE,
				HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT };
		Cursor c = _context.getContentResolver().query(
				HasDepartmentMetaData.CONTENT_URI, hasDepartmentsColumns,
				hasDepartmentsColumns[0] + " = '" + department.getId() + "'", null,
				null);

		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					Department subDepartment = getDepartmentById(c.getLong(c
							.getColumnIndex(hasDepartmentsColumns[1])));
					departments.add(subDepartment);
					c.moveToNext();
				}
			}
		}

		c.close();

		return departments;
	}

	/**
	 * Clear department table
	 */
	public void clearDepartmentsTable() {
		_context.getContentResolver().delete(DepartmentsMetaData.CONTENT_URI,
				null, null);
	}
	
	private String[] columns = new String[] { 
			DepartmentsMetaData.Table.COLUMN_ID,
			DepartmentsMetaData.Table.COLUMN_NAME,
			DepartmentsMetaData.Table.COLUMN_ADDRESS,
			DepartmentsMetaData.Table.COLUMN_PHONE,
			DepartmentsMetaData.Table.COLUMN_EMAIL};

	/**
	 * Cursor to department
	 * 
	 * @param cursor
	 *            Input cursor
	 * @return Output department
	 */
	private Department cursorToDepartment(Cursor cursor) {
		Department department = new Department();
		department.setId(cursor.getLong(cursor
				.getColumnIndex(DepartmentsMetaData.Table.COLUMN_ID)));
		department.setName(cursor.getString(cursor
				.getColumnIndex(DepartmentsMetaData.Table.COLUMN_NAME)));
		department.setAddress(cursor.getString(cursor
				.getColumnIndex(DepartmentsMetaData.Table.COLUMN_ADDRESS)));
		department.setPhone(cursor.getLong(cursor
				.getColumnIndex(DepartmentsMetaData.Table.COLUMN_PHONE)));
		return department;
	}
}
