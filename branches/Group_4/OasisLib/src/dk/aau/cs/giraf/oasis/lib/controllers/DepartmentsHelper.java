package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.DepartmentsMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.HasDepartment;
import dk.aau.cs.giraf.oasis.lib.models.HasSubDepartment;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Helper class for Departments
 * 
 * @author Admin
 * 
 */
public class DepartmentsHelper {

	private static Context _context;
	private AuthUsersController au;
	private HasDepartmentController hd;
	private HasSubDepartmentController hsd;
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
		au = new AuthUsersController(_context);
		hd = new HasDepartmentController(_context);
		hsd = new HasSubDepartmentController(_context);
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

	/**
	 * Remove profile attachment to department
	 * @param profile Profile to remove
	 * @param department Department to remove from
	 * @return Rows
	 */
	public int removeProfileAttachmentToDepartment(Profile profile, Department department) {
		return hd.removeHasDepartment(profile, department);
	}

	/**
	 * Remove sub department attachment to department
	 * @param department Department
	 * @param subDepartment Subdepartment
	 * @return Rows
	 */
	public int removeSubDepartmentAttachmentToDepartment(Department department, Department subDepartment) {
		return hsd.removeHasSubDepartment(subDepartment, department);
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

	/**
	 * Attach profile to department
	 * @param profile Profile to attach
	 * @param department Department to attach to
	 * @return Rows
	 */
	public long attachProfileToDepartment(Profile profile, Department department) {
		HasDepartment hdModel = new HasDepartment();
		hdModel.setIdDepartment(department.getId());
		hdModel.setIdProfile(profile.getId());
		return hd.insertHasDepartment(hdModel);
	}

	/**
	 * Attach sub department to department
	 * @param department Department to attach to
	 * @param subDepartment Subdepartment to attach
	 * @return Rows
	 */
	public long attachSubDepartmentToDepartment(Department department, Department subDepartment) {
		HasSubDepartment hsdModel = new HasSubDepartment();
		hsdModel.setIdDepartment(department.getId());
		hsdModel.setIdSubDepartment(subDepartment.getId());
		return hsd.insertHasSubDepartment(hsdModel);
	}

	/**
	 * Modify department
	 * @param department
	 * Department containing data to modify
	 */
	public void modifyDepartment(Department department) {
		Uri uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, department.getId());
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

		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					departments.add(cursorToDepartment(c));
					c.moveToNext();
				}
			}

			c.close();
		}

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

	/**
	 * Get departments by id
	 * @param id ID
	 * @return Department
	 */
	public Department getDepartmentById(long id) {
		Department _department = null;
		Uri uri = ContentUris.withAppendedId(DepartmentsMetaData.CONTENT_URI, id);
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if(c.moveToFirst()) {
				_department = cursorToDepartment(c);
			}
			c.close();
		}

		return _department;
	}

	/**
	 * Get departments by name
	 * @param name Department name
	 * @return List of departments
	 */
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
			c.close();
		}

		return departments;
	}
	
	/**
	 * Get departments by profile
	 * @param profile Profile
	 * @return List of departments
	 */
	public List<Department> getDepartmentsByProfile(Profile profile) {
		List<Department> departments = new ArrayList<Department>();
		
		List<HasDepartment> list = hd.getDepartmentsByProfile(profile);
		
		for (HasDepartment hdModel : list) {
			Department department = getDepartmentById(hdModel.getIdDepartment());
			departments.add(department);
		}
		
		return departments;
	}

	/**
	 * Get sub departments
	 * @param department Department
	 * @return List of sub departments
	 */
	public List<Department> getSubDepartments(Department department) {
		List<Department> departments = new ArrayList<Department>();
		
		List<HasSubDepartment> list = hsd.getSubDepartmentsByDepartment(department);
		
		for (HasSubDepartment hsdModel : list) {
			Department subDepartment = getDepartmentById(hsdModel.getIdSubDepartment());
			departments.add(subDepartment);
		}

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
