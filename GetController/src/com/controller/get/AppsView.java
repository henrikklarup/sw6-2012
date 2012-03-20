package com.controller.get;

import sw6.oasis.controllers.Helper;
import sw6.oasis.viewmodels.App;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AppsView extends ListActivity {

	Helper helper;
	App app;
	ArrayAdapter<String> adapter;
	Button bAdd, bDel;
	TextView tvHeader;
	int _position;
	SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tableview);
		helper = new Helper(this);
		
		app = new App();
		
		tvHeader = (TextView) findViewById(R.id.table_header);
		tvHeader.setText("AppsTable");
		bAdd = (Button) findViewById(R.id.add);
		bAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				app.setName("AppName");
				app.setVersionNumber("versionNumber");
				
				helper.insertApp(app);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.clearAppsTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		Cursor cursor = helper.getApps();
        int[] to = new int[] { R.id.app_column_one, R.id.app_column_two, R.id.app_column_three};

        mAdapter = new SimpleCursorAdapter(this, R.layout.app_list, cursor, cursor.getColumnNames(), to);

        setListAdapter(mAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		app.setName("AppModified");
		app.setId(_id);
		helper.modifyApp(app);
		mAdapter.notifyDataSetChanged();
	}
}
