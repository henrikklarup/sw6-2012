package com.controller.get;

import java.util.List;

import sw6.oasis.controllers.Helper;
import sw6.oasis.models.App;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
		setContentView(R.layout.tableview);
		helper = new Helper(this);
		
		app = new App();
		app.setName("GetControllerInsert");
		app.setVersionNumber("versionNumber");
		helper.appsHelper.insertApp(app);
		helper.appsHelper.insertApp(app);
		helper.appsHelper.insertApp(app);
		
		values = helper.appsHelper.getApps();

		adapter = new ArrayAdapter<App>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
			
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		App app1 = helper.appsHelper.getAppById(_id);
		Toast.makeText(this, app1.toString(), Toast.LENGTH_SHORT).show();
//		app.setId(_id);
//		app.setName("AppModified");
//		app.setVersionNumber("ModifiedVersion");
//		helper.appsHelper.modifyApp(app);
//		adapter.notifyDataSetChanged();
	}
}
