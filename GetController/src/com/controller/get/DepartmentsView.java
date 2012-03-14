package com.controller.get;

import java.util.List;

import com.controller.get.controller.AutismMethods;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DepartmentsView extends ListActivity {

	private AutismMethods helper;
	ArrayAdapter<String> adapter;
	List<String> values;
	int _position;
	Intent direct;
	SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		helper = new AutismMethods(this);
		
		Cursor cursor = helper.getDepartments();
		int[] to = new int[] { R.id.column_one, R.id.column_two};

        mAdapter = new SimpleCursorAdapter(this, R.layout.list_example, cursor, cursor.getColumnNames(), to);

        setListAdapter(mAdapter);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add:
			helper.insertDepartment("NewDepartment", 123456789);
			mAdapter.notifyDataSetChanged();
			break;
		case R.id.delete:
			helper.clearDepartmentsTable();
			mAdapter.notifyDataSetChanged();
			break;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		helper.modifyDepartment(_id, "DepartmentModified", 987654321);
		mAdapter.notifyDataSetChanged();
	}
}