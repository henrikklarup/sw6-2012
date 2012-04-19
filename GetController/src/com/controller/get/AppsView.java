package com.controller.get;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

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


		//profile er en forud deklareret profil
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			app = helper.appsHelper.getAppById(extras.getLong("ID"));
		} else {
			app = new App("Should not be added","0.1");
		}

		updateList();

		bAdd = (Button) findViewById(R.id.add);
		bAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				helper.appsHelper.insertApp(app);
				List<App> apps = helper.appsHelper.getApps();
				app = apps.get(apps.size() - 1);
				List<Profile> profiles = helper.profilesHelper.getProfiles();
				Profile profile = profiles.get(profiles.size() - 1);
				helper.appsHelper.attachAppToProfile(app, profile);
				updateList();
			}
		});

		bDel = (Button) findViewById(R.id.delete);
		bDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				helper.appsHelper.clearAppsTable();
				updateList();
			}
		});

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

//		App clickedApp = new App();
//		clickedApp = helper.appsHelper.getAppById(((App)getListAdapter().getItem(position)).getId());

		List<Profile> profiles = helper.profilesHelper.getProfiles();
		Profile profile = profiles.get(profiles.size() - 1);
		
		List<App> apps;
		apps = helper.appsHelper.getAppsByProfile(profile);
		Log.e("Size of apps", Integer.toString(apps.size()));
		


		App clickedAppWithSettings = apps.get(apps.size() - 1);

		if (clickedAppWithSettings != null) {
			Log.e("clickedAppWithSetting", "clickedAppWithSetting not null");
			Log.e("App Id: ", Long.toString(clickedAppWithSettings.getId()));
			Log.e("Profile Id: ", Long.toString(profile.getId()));
			Setting<String, String, String> setting = clickedAppWithSettings.getSettings();

			if (setting != null) {
				Log.e("Setting", "Setting not null");
				String settings = setting.get("Profile1").get("Settings");
				String[] settingsList = settings.split(",");
				for (String s : settingsList) {
					try {
						Toast.makeText(this, clickedAppWithSettings.getSettings().get("Profile1").get(s), Toast.LENGTH_SHORT).show();
					} catch (NullPointerException e) {
						Toast.makeText(this, "Null Pointer Exception - Must not occur", Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				Log.e("Setting", "Setting is null");
			}
		}
		updateList();
	}

	public void updateList() {
		values = helper.appsHelper.getApps();
		adapter = new ArrayAdapter<App>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
}