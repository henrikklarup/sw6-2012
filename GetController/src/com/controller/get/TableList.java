package com.controller.get;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	List<App> values;
	App app;
	Profile profile;
	Setting settings;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		helper = new Helper(this);
		helper.CreateDummyData();
		
		settings = new Setting<String, String, String>();
		

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Settings", "Favorite Color,Favorite Food,Favorite Animal");
		map.put("Favorite Color", "Blue");
		map.put("Favorite Food", "Carrot");
		map.put("Favorite Animal", "Rabbit");
		settings.put("Profile1", map);
		
		profile = helper.profilesHelper.authenticateProfile("HejJens");
		
		if (profile == null) {
			profile = new Profile("Firstname", "Surname", null, Profile.pRoles.GUARDIAN.ordinal(), 1234567890, "Pic", null);
			profile.setId(helper.profilesHelper.insertProfile(profile));
			helper.profilesHelper.setCertificate("HejJens", profile);
		}
		
		app = helper.appsHelper.getAppByPackageName();
		if (app == null) {
			app = new App("TestApp", this.getPackageName(), "FakeActivity");
			long appId = helper.appsHelper.insertApp(app);
			app.setId(appId);
		}
		
		app.setSettings(settings);
		
		Log.i("Oasis", "DB attach: " + helper.appsHelper.attachAppToProfile(app, profile));
		Log.i("Oasis", "DB modify: " + helper.appsHelper.modifyAppByProfile(app, profile));
		
		values = helper.appsHelper.getAppsByProfile(profile);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tables);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try{
			Class _class = Class.forName("com.controller.get." + tables[position]);
			Intent _intent = new Intent(TableList.this, _class);
			_intent.putExtra("ProfileId", profile.getId());
			startActivity(_intent);
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}