package com.controller.get;

import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

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
		
		Setting settings1 = new Setting<String, String, String>();

		HashMap map = new HashMap();
		map.put("Settings", "Favorite Color,Favorite Food,Favorite Animal");
		map.put("Favorite Color", "Blue");
		map.put("Favorite Food", "Carrot");
		map.put("Favorite Animal", "Rabbit");
		settings.put("asdas", map);
		
		HashMap map1 = new HashMap();
		map1.put("Settings", "Favorite Color,Favorite Food,Favorite Animal");
		map1.put("Favorite Color", "Blue");
		map1.put("Favorite Food", "Carrot");
		map1.put("Favorite Animal", "Rabbit");
		settings1.put("supbro", map1);
		
		

		
		profile = new Profile("FEDTE", "FEDTE","FEDTE", 0, 12345678, "FEDTE", null);
		
		App app = new App();
		
		app.setSettings(settings);
		
		App app1 = new App();
		app1.setSettings(settings1);
		
	
		
		
		helper.appsHelper.attachAppToProfile(app, profile);
		helper.appsHelper.attachAppToProfile(app1, profile);
		
		List<App> asd = helper.appsHelper.getAppsByProfile(profile);
		
		asd.get(0).getSettings().get("asdas").remove("Favore cOlor");
		
		
		
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