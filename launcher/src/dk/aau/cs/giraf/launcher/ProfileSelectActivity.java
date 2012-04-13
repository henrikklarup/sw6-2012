package dk.aau.cs.giraf.launcher;

import java.util.List;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

import android.view.View;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProfileSelectActivity extends Activity{

	private static List<Profile> mProfiles;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profileselect);

		loadProfiles(true);

	}
	private void loadProfiles(boolean isLaunching) {
		if (isLaunching && mProfiles != null) {
			return;
		}

		Helper helper = new Helper(this);
		Profile.setOutput("{1} {2} {3}");
		
		mProfiles = helper.profilesHelper.getProfiles();

		ListView lv = (ListView) findViewById(R.id.profilesList);
		
		for(int i = 0; i < mProfiles.size(); i++) {
			// If profile belongs to guardian:
			if (mProfiles.get(i).getPRole() != 3L) {
				mProfiles.remove(i);
				i--;
			}
		}
		
		ArrayAdapter<Profile> adapter = new ArrayAdapter<Profile>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mProfiles);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	  	final Profile person = (Profile) parent.getItemAtPosition(position);
		            final String packageName = getIntent().getExtras().getString("appPackageName");
		            final String activityName = getIntent().getExtras().getString("appActivityName");
		            
		            Intent intent = new Intent(Intent.ACTION_MAIN);
		    		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		    		intent.setComponent(new ComponentName(packageName, activityName));
		    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
	                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		    		
		            intent.putExtra("currentProfileId", person.getId());
		            
					startActivity(intent);
		          }
		        });
	}
}