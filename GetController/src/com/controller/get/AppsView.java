package com.controller.get;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;

public class AppsView extends ListActivity {

	Helper helper;
	App app;
	ArrayAdapter<App> adapter;
	Button bAdd, bDel;
	TextView tvHeader;
	int _position;
	List<App> values;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		helper = new Helper(this);

		app = new App();
		app.setName("GetControllerInsert");
		app.setVersionNumber("versionNumber");
		helper.appsHelper.insertApp(app);
		helper.appsHelper.insertApp(app);
		helper.appsHelper.insertApp(app);

//		values = helper.appsHelper.getApps();

		updateList();

		bAdd = (Button) findViewById(R.id.add);
		bAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				helper.appsHelper.insertApp(app);
				updateList();
			}
		});
		
		bDel = (Button) findViewById(R.id.delete);
		bDel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				helper.appsHelper.clearAppsTable();
				updateList();
			}
		});
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		App _app = new App();
		App fedte = new App();
		_app = (App) getListAdapter().getItem(position);
		long _id = _app.getId();

		fedte = helper.appsHelper.getAppById(_id);

		if (fedte != null) {
			Toast.makeText(this, fedte.toString(), Toast.LENGTH_SHORT).show();
		} else {
			//   Toast.makeText(this, String.valueOf(_id), Toast.LENGTH_SHORT).show();
		}
		
		updateList();
	}
	
	public void updateList() {
		values = helper.appsHelper.getApps();
		adapter = new ArrayAdapter<App>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
}