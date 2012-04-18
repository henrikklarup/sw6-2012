package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.HasDepartmentMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.HasDepartment;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

class HasDepartmentController {

	private Context _context;
	private String[] columns = new String[] { 
			HasDepartmentMetaData.Table.COLUMN_IDPROFILE, 
			HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT};

	public HasDepartmentController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int clearHasDepartmentTable() {
		return _context.getContentResolver().delete(HasDepartmentMetaData.CONTENT_URI, null, null);
	}

	public long insertHasDepartment(HasDepartment hd) {
		ContentValues cv = getContentValues(hd);
		_context.getContentResolver().insert(HasDepartmentMetaData.CONTENT_URI, cv);

		return 0;
	}

	public List<HasDepartment> getHasDepartments() {
		List<HasDepartment> list = new ArrayList<HasDepartment>();

		Cursor c = _context.getContentResolver().query(HasDepartmentMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			list = cursorToHasDepartmentList(c);

			c.close();
		}

		return list;
	}

	public List<HasDepartment> getProfilesByDepartment(Department dep) {
		List<HasDepartment> list= new ArrayList<HasDepartment>();

		Cursor c = _context.getContentResolver().query(HasDepartmentMetaData.CONTENT_URI, columns, 
				HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + dep.getId() + "'", null, null);

		if (c != null) {
			list = cursorToHasDepartmentList(c);

			c.close();
		}

		return list;
	}
	
	public List<HasDepartment> getDepartmentsByProfile(Profile profile) {
		List<HasDepartment> list= new ArrayList<HasDepartment>();

		Cursor c = _context.getContentResolver().query(HasDepartmentMetaData.CONTENT_URI, columns, 
				HasDepartmentMetaData.Table.COLUMN_IDPROFILE+ " = '" + profile.getId() + "'", null, null);

		if (c != null) {
			list = cursorToHasDepartmentList(c);

			c.close();
		}

		return list;
	}
	
	public int modifyHasDepartment(HasDepartment hd) {
		ContentValues cv = getContentValues(hd);
		return _context.getContentResolver().update(HasDepartmentMetaData.CONTENT_URI, cv, 
				HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + hd.getIdDepartment() + "' AND " +
				HasDepartmentMetaData.Table.COLUMN_IDPROFILE + " = '" + hd.getIdProfile() + "'", null);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private List<HasDepartment> cursorToHasDepartmentList(Cursor cursor) {
		List<HasDepartment> list = new ArrayList<HasDepartment>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				list.add(cursorToHasDepartment(cursor));
				cursor.moveToNext();
			}
		}

		return  list;
	}

	private HasDepartment cursorToHasDepartment(Cursor cursor) {
		HasDepartment hd = new HasDepartment();
		hd.setIdDepartment(cursor.getLong(cursor.getColumnIndex(HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT)));
		hd.setIdProfile(cursor.getLong(cursor.getColumnIndex(HasDepartmentMetaData.Table.COLUMN_IDPROFILE)));
		return hd;
	}

	private ContentValues getContentValues(HasDepartment hd) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, hd.getIdDepartment());
		contentValues.put(HasDepartmentMetaData.Table.COLUMN_IDPROFILE, hd.getIdProfile());
		return contentValues;
	}
}