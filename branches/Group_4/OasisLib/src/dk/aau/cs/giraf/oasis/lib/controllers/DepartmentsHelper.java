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
	private AuthUsersHelper au;
	private String[] columns = new String[] { 
			DepartmentsMetaData.Table.COLUMN_ID,
			DepartmentsMetaData.Table.COLUMN_NAME,
			DepartmentsMetaData.Table.COLUMN_ADDRESS,
			DepartmentsMetaData.Table.COLUMN_PHONE,
			DepartmentsMetaData.Table.COLUMN_EMAIL};

	/**
	 * Constructor
	 * @param context Current context
	 */
	public DepartmentsHelper(Context context) {
		_context = context;
		au = new AuthUsersHelper(_context);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Clear department table
	 */
	public void clearDepartmentsTable() {
		_context.getContentResolver().delete(DepartmentsMetaData.CONTENT_URI,
				null, null);
	}

	public int removeProfileAttachmentToDepartment(Profile profile, Department department) {
		_context.getContentResolver().delete(HasDepartmentMetaData.CONTENT_URI, 
				HasDepartmentMetaData.Table.COLUMN_IDPROFILE + " = '" + profile.getId() + "'" +
						HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + department.getId() + "'", null);
		return 0;
	}

	public int removeSubDepartmentAttachmentToDepartment(Department department, Department subDepartment) {
		_context.getContentResolver().delete(HasSubDepartmentMetaData.CONTENT_URI, 
				HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + " = '" + subDepartment.getId() + "'" +
						HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + department.getId() + "'", null);
		return 0;
	}

	/**
	 * Insert department
	 * @param department Department containg data
	 */
	public long insertDepartment(Department department) {
		department.setId(au.insertAuthUser(1));
		
		ContentValues cv = getContentValues(department);
		cv.put(DepartmentsMetaData.Table.COLUMN_ID, department.getId());
		_context.getContentResolver().insert(DepartmentsMetaData.CONTENT_URI, cv);
		return department.getId();
	}

	public int attachProfileToDepartment(Profile profile, Department department) {
		ContentValues values = new ContentValues();
		values.put(HasDepartmentMetaData.Table.COLUMN_IDPROFILE, profile.getId());
		values.put(HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, department.getId());
		_context.getContentResolver().insert(HasDepartmentMetaData.CONTENT_URI, values);
		return 0;
	}

	public int attachSubDepartmentToDepartment(Department department, Department subDepartment) {
		ContentValues values = new ContentValues();
		values.put(HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT, subDepartment.getId());
		values.put(HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, department.getId());
		_context.getContentResolver().insert(HasSubDepartmentMetaData.CONTENT_URI, values);
		return 0;
	}

	/**
	 * Modify department
	 * @param department
	 * Department containing data to modify
	 */
	public void modifyDepartment(Department department) {
		Uri uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI,
				department.getId());
		ContentValues cv = getContentValues(department);
		_context.getContentResolver().update(uri, cv, null, null);
	}
	
	/**
	 * Authenticates the department
	 * @param certificate the certificate that authenticates the department
	 * @return the authenticated department or null
	 */
	public Department authenticateDepartment(String certificate) {
		Department dep = null;
		long id;
		id = au.getIdByCertificate(certificate);
		dep = getDepartmentById(id);

		return dep;
	}
	
	/**
	 * Set a new certificate
	 * @param certificate the certificate to set
	 * @param department the department whom the certificate is updated for
	 * @return the number of row affected
	 */
	public int setCertificate(String certificate, Department department) {
		int result;
		result = au.setCertificate(certificate, department.getId());
		
		return result;
	}

	/**
	 * Get all departments
	 * @return List<Department>, containing all departments
	 */
	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList<Department>();
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
	
	/**
	 * Retrieve the certificates for a department
	 * @param department
	 * @return List<String> or null
	 */
	public List<String> getCertificatesByDepartment(Department department) {
		List<String> certificate;
		certificate = au.getCertificatesById(department.getId());

		return certificate;
	}

	public Department getDepartmentById(long id) {
		Department _department = null;
		Uri uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, id);
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if(c.moveToFirst()) {
				_department = cursorToDepartment(c);
			}
		}

		c.close();

		return _department;
	}

	public List<Department> getDepartmentByName(String name) {
		List<Department> departments = new ArrayList<Department>();
		Cursor c = _context.getContentResolver().query(DepartmentsMetaData.CONTENT_URI, columns, DepartmentsMetaData.Table.COLUMN_NAME + " = '" + name + "'", null, null);
		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					departments.add(cursorToDepartment(c));
					c.moveToNext();
				}
			}
		}

		c.close();

		return departments;
	}
	
	public List<Department> getDepartmentsByProfile(Profile profile) {
		List<Department> departments = new ArrayList<Department>();
		String[] hasDepartmentColumns = {
				HasDepartmentMetaData.Table.COLUMN_IDPROFILE,
				HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT };
		
		Cursor c = _context.getContentResolver().query(
				HasDepartmentMetaData.CONTENT_URI, hasDepartmentColumns,
				hasDepartmentColumns[0] + " = '" + profile.getId() + "'", null,	null);

		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					Department department = getDepartmentById(c.getLong(c.getColumnIndex(hasDepartmentColumns[1])));
					departments.add(department);
					c.moveToNext();
				}
			}
		}

		c.close();

		return departments;
	}

	public List<Department> getSubDepartments(Department department) {
		List<Department> departments = new ArrayList<Department>();
		String[] hasSubDepartmentsColumns = {
				HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT,
				HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT };
		
		Cursor c = _context.getContentResolver().query(
				HasSubDepartmentMetaData.CONTENT_URI, hasSubDepartmentsColumns,
				hasSubDepartmentsColumns[0] + " = '" + department.getId() + "'", null,	null);

		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					Department subDepartment = getDepartmentById(c.getLong(c.getColumnIndex(hasSubDepartmentsColumns[1])));
					departments.add(subDepartment);
					c.moveToNext();
				}
			}
		}

		c.close();

		return departments;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - Keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to department
	 * @param cursor Input cursor
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
		department.setEmail(cursor.getString(cursor
				.getColumnIndex(DepartmentsMetaData.Table.COLUMN_EMAIL)));
		department.setPhone(cursor.getLong(cursor
				.getColumnIndex(DepartmentsMetaData.Table.COLUMN_PHONE)));
		return department;
	}

	private ContentValues getContentValues(Department department) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DepartmentsMetaData.Table.COLUMN_NAME, department.getName());
		contentValues.put(DepartmentsMetaData.Table.COLUMN_ADDRESS, department.getAddress());
		contentValues.put(DepartmentsMetaData.Table.COLUMN_EMAIL, department.getEmail());
		contentValues.put(DepartmentsMetaData.Table.COLUMN_PHONE, department.getPhone());
		return contentValues;
	}
}
