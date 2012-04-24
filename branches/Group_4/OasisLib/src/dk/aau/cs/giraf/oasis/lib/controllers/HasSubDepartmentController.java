package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.HasSubDepartmentMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.HasSubDepartment;

class HasSubDepartmentController {

	private Context _context;
	private String[] columns = new String[] { 
			HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, 
			HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT};

	public HasSubDepartmentController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int clearHasSubDepartmentTable() {
		return _context.getContentResolver().delete(HasSubDepartmentMetaData.CONTENT_URI, null, null);
	}
	
	public int removeHasSubDepartment(Department department, Department subDepartment) {
		return _context.getContentResolver().delete(HasSubDepartmentMetaData.CONTENT_URI, 
				HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + " = '" + subDepartment.getId() + "'" +
						HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + department.getId() + "'", null);
	}

	public long insertHasSubDepartment(HasSubDepartment hsd) {
		ContentValues cv = getContentValues(hsd);
		_context.getContentResolver().insert(HasSubDepartmentMetaData.CONTENT_URI, cv);

		return 0;
	}

	public List<HasSubDepartment> getHasSubDepartments() {
		List<HasSubDepartment> hsdList = new ArrayList<HasSubDepartment>();

		Cursor c = _context.getContentResolver().query(HasSubDepartmentMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			hsdList = cursorToHasSubDepartmentList(c);

			c.close();
		}

		return hsdList;
	}

	public List<HasSubDepartment> getSubDepartmentsByDepartment(Department dep) {
		List<HasSubDepartment> hsdList= new ArrayList<HasSubDepartment>();

		Cursor c = _context.getContentResolver().query(HasSubDepartmentMetaData.CONTENT_URI, columns, 
				HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + dep.getId() + "'", null, null);

		if (c != null) {
			hsdList = cursorToHasSubDepartmentList(c);

			c.close();
		}

		return hsdList;
	}
	
	public int modifyHasSubDepartment(HasSubDepartment hsd) {
		ContentValues cv = getContentValues(hsd);
		return _context.getContentResolver().update(HasSubDepartmentMetaData.CONTENT_URI, cv, 
				HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + hsd.getIdDepartment() + "' AND " +
				HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + " = '" + hsd.getIdSubDepartment() + "'", null);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private List<HasSubDepartment> cursorToHasSubDepartmentList(Cursor cursor) {
		List<HasSubDepartment> list = new ArrayList<HasSubDepartment>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				list.add(cursorToHasSubDepartment(cursor));
				cursor.moveToNext();
			}
		}

		return  list;
	}

	private HasSubDepartment cursorToHasSubDepartment(Cursor cursor) {
		HasSubDepartment hsd = new HasSubDepartment();
		hsd.setIdDepartment(cursor.getLong(cursor.getColumnIndex(HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT)));
		hsd.setIdSubDepartment(cursor.getLong(cursor.getColumnIndex(HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT)));
		return hsd;
	}

	private ContentValues getContentValues(HasSubDepartment hsd) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, hsd.getIdDepartment());
		contentValues.put(HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT, hsd.getIdSubDepartment());
		return contentValues;
	}
}