package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.HasGuardianMetaData;
import dk.aau.cs.giraf.oasis.lib.models.HasGuardian;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

class HasGuardianController {

	private Context _context;
	private String[] columns = new String[] { 
			HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, 
			HasGuardianMetaData.Table.COLUMN_IDCHILD};

	public HasGuardianController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int clearHasGuardianTable() {
		return _context.getContentResolver().delete(HasGuardianMetaData.CONTENT_URI, null, null);
	}
	
	public int removeHasGuardian(Profile child, Profile guardian) {
		return _context.getContentResolver().delete(HasGuardianMetaData.CONTENT_URI, 
				HasGuardianMetaData.Table.COLUMN_IDCHILD + " = '" + child.getId() + "'" +
						HasGuardianMetaData.Table.COLUMN_IDGUARDIAN + " = '" + guardian.getId() + "'", null);
	}

	public long insertHasGuardian(HasGuardian hg) {
		ContentValues cv = getContentValues(hg);
		_context.getContentResolver().insert(HasGuardianMetaData.CONTENT_URI, cv);

		return 0;
	}
	
	public List<HasGuardian> getHasGuardians() {
		List<HasGuardian> hgList = new ArrayList<HasGuardian>();
		
		Cursor c = _context.getContentResolver().query(HasGuardianMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			hgList = cursorToHasGuardianList(c);
			
			c.close();
		}

		return hgList;
	}
	
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
	
	public int modifyHasGuardian(HasGuardian hg) {
		ContentValues cv = getContentValues(hg);
		return _context.getContentResolver().update(HasGuardianMetaData.CONTENT_URI, cv, 
				HasGuardianMetaData.Table.COLUMN_IDGUARDIAN + " = '" + hg.getIdGuardian() + "' AND " +
						HasGuardianMetaData.Table.COLUMN_IDCHILD + " = '" + hg.getIdChild() + "'", null);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

	private HasGuardian cursorToHasGuardian(Cursor cursor) {
		HasGuardian hg = new HasGuardian();
		hg.setIdGuardian(cursor.getLong(cursor.getColumnIndex(HasGuardianMetaData.Table.COLUMN_IDGUARDIAN)));
		hg.setIdChild(cursor.getLong(cursor.getColumnIndex(HasGuardianMetaData.Table.COLUMN_IDCHILD)));
		return hg;
	}

	private ContentValues getContentValues(HasGuardian hg) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, hg.getIdGuardian());
		contentValues.put(HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, hg.getIdChild());
		return contentValues;
	}
}