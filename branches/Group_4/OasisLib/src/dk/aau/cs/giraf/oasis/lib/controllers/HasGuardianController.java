package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.HasGuardianMetaData;
import dk.aau.cs.giraf.oasis.lib.models.HasGuardian;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Has guardian controller
 * @author Admin
 *
 */
class HasGuardianController {

	private Context _context;
	private String[] columns = new String[] { 
			HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, 
			HasGuardianMetaData.Table.COLUMN_IDCHILD};

	/**
	 * Constructor
	 * @param context
	 */
	public HasGuardianController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear has guardian
//	 * @return Rows
//	 */
//	public int clearHasGuardianTable() {
//		return _context.getContentResolver().delete(HasGuardianMetaData.CONTENT_URI, null, null);
//	}
	
	/**
	 * Remove has guardian
	 * @param child Child profile
	 * @param guardian Guardian profile
	 * @return Rows
	 */
	public int removeHasGuardian(Profile child, Profile guardian) {
		return _context.getContentResolver().delete(HasGuardianMetaData.CONTENT_URI, 
				HasGuardianMetaData.Table.COLUMN_IDCHILD + " = '" + child.getId() + "'" +
						HasGuardianMetaData.Table.COLUMN_IDGUARDIAN + " = '" + guardian.getId() + "'", null);
	}

	/**
	 * Insert has guardian
	 * @param hg Has guardian
	 * @return 0
	 */
	public long insertHasGuardian(HasGuardian hg) {
		ContentValues cv = getContentValues(hg);
		_context.getContentResolver().insert(HasGuardianMetaData.CONTENT_URI, cv);

		return 0;
	}
	
//	/**
//	 * Get has guardian
//	 * @return List of has guardians
//	 */
//	public List<HasGuardian> getHasGuardians() {
//		List<HasGuardian> hgList = new ArrayList<HasGuardian>();
//		
//		Cursor c = _context.getContentResolver().query(HasGuardianMetaData.CONTENT_URI, columns, null, null, null);
//
//		if (c != null) {
//			hgList = cursorToHasGuardianList(c);
//			
//			c.close();
//		}
//
//		return hgList;
//	}
	
	/**
	 * Get children by guardian
	 * @param guardian Guardian profile
	 * @return List of has guardians
	 */
	public List<HasGuardian> getChildrenByGuardian(Profile guardian) {
		List<HasGuardian> hgList = new ArrayList<HasGuardian>();
		
		Cursor c = _context.getContentResolver().query(HasGuardianMetaData.CONTENT_URI, columns, 
				HasGuardianMetaData.Table.COLUMN_IDGUARDIAN + " = '" + guardian.getId() + "'", null, null);

		if (c != null) {
			hgList = cursorToHasGuardianList(c);
			
			c.close();
		}

		return hgList;
	}
	
	/**
	 * Modify has guardians
	 * @param hg Has guardian
	 * @return Rows
	 */
	public int modifyHasGuardian(HasGuardian hg) {
		ContentValues cv = getContentValues(hg);
		return _context.getContentResolver().update(HasGuardianMetaData.CONTENT_URI, cv, 
				HasGuardianMetaData.Table.COLUMN_IDGUARDIAN + " = '" + hg.getIdGuardian() + "' AND " +
						HasGuardianMetaData.Table.COLUMN_IDCHILD + " = '" + hg.getIdChild() + "'", null);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to has guardian list
	 * @param cursor Cursor
	 * @return List of has guardians
	 */
	private List<HasGuardian> cursorToHasGuardianList(Cursor cursor) {
		List<HasGuardian> hgList = new ArrayList<HasGuardian>();
		
		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				hgList.add(cursorToHasGuardian(cursor));
				cursor.moveToNext();
			}
		}
		
		return  hgList;
	}

	/**
	 * Cursor to has guardian
	 * @param cursor Cursor
	 * @return Has guardian
	 */
	private HasGuardian cursorToHasGuardian(Cursor cursor) {
		HasGuardian hg = new HasGuardian();
		hg.setIdGuardian(cursor.getLong(cursor.getColumnIndex(HasGuardianMetaData.Table.COLUMN_IDGUARDIAN)));
		hg.setIdChild(cursor.getLong(cursor.getColumnIndex(HasGuardianMetaData.Table.COLUMN_IDCHILD)));
		return hg;
	}

	/**
	 * Content values
	 * @param hg Has guardian
	 * @return Content values
	 */
	private ContentValues getContentValues(HasGuardian hg) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, hg.getIdGuardian());
		contentValues.put(HasGuardianMetaData.Table.COLUMN_IDCHILD, hg.getIdChild());
		return contentValues;
	}
}