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
	Profile profile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		helper = new Helper(this);


		//profile er en forud deklareret profil
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			profile = helper.profilesHelper.getProfileById(extras.getLong("ProfileId"));
		}

		updateList();

		bAdd = (Button) findViewById(R.id.add);
		bAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				helper.appsHelper.insertApp(app);
//				List<App> apps = helper.appsHelper.getApps();
//				app = apps.get(apps.size() - 1);
//				List<Profile> profiles = helper.profilesHelper.getProfiles();
//				Profile profile = profiles.get(profiles.size() - 1);
//				helper.appsHelper.attachAppToProfile(app, profile);
							
				App m_App = null;
				
				// Find the app which has the same package name as this one
                m_App = helper.appsHelper.getAppByPackageName();
                        
                if(m_App == null){
                        // If no app has been found, generate one and insert it in Oasis
                        m_App = new App("Wombat", "1", "", AppsView.this.getPackageName(), "MainActivity");
                        m_App.setId(helper.appsHelper.insertApp(m_App));
                }
                                
				updateList();
			}
		});

		bDel = (Button) findViewById(R.id.delete);
		bDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateList();
			}
		});

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		App clickedApp;
		
		clickedApp = helper.appsHelper.getAppByPackageName();
		App clickedAppWithSettings = helper.appsHelper.getAppByIds(app.getId(), profile.getId());
		
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
		values = helper.appsHelper.getAppsByProfile(profile);
		adapter = new ArrayAdapter<App>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
}