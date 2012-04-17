package com.controller.get;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;
import java.util.HashMap;

public class TableList extends ListActivity {
	
	String tables[] = new String[] {"AppsView"};
//	String tables[] = new String[] {"AppsView", "DepartmentsView", "MediaView", "ProfilesView", "SettingsView", "StatsView"};
	ArrayAdapter<String> adapter;
	Helper helper;
	List<Profile> values;
	Profile profile, test;
	Setting settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		helper = new Helper(this);
		
		
		
		settings = new Setting<String, String, String>();

		HashMap map = new HashMap();
		map.put("Settings", "Favorite Color,Favorite Food,Favorite Animal");
		map.put("Favorite Color", "Blue");
		map.put("Favorite Food", "Carrot");
		map.put("Favorite Animal", "Rabbit");
		settings.put("FEDTE", map);
		
		profile = new Profile("FEDTE", "FEDTE","FEDTE", 0, 12345678, "FEDTE", settings);
		
		helper.profilesHelper.insertProfile(profile);
		
		values = helper.profilesHelper.getProfiles();
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tables);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try{
			Class _class = Class.forName("com.controller.get." + tables[position]);
			Intent _intent = new Intent(TableList.this, _class);
			_intent.putExtra("ID", values.get(values.size()-1).getId());
			startActivity(_intent);
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}