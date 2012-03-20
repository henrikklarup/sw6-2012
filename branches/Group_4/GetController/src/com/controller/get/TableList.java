package com.controller.get;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TableList extends ListActivity {
	
	String tables[] = new String[] {"AppsView"};
//	String tables[] = new String[] {"AppsView", "DepartmentsView", "MediaView", "ProfilesView", "SettingsView", "StatsView"};
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tables);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try{
			Class _class = Class.forName("com.controller.get." + tables[position]);
			Intent _intent = new Intent(TableList.this, _class);
			startActivity(_intent);
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}