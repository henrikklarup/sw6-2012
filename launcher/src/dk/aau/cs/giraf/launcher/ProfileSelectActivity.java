package dk.aau.cs.giraf.launcher;

import java.util.ArrayList;
import java.util.List;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Department;

import dk.aau.cs.giraf.gui.*;

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
	
	private long childID;
  	private long guardianID;
  	
    private String packageName;
    private String activityName;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profileselect);
		
	  	guardianID = getIntent().getExtras().getLong("currentGuardianID");
        packageName = getIntent().getExtras().getString("appPackageName");
        activityName = getIntent().getExtras().getString("appActivityName");
		
		loadProfiles();
	}
	
	private void loadProfiles() {
		Helper helper = new Helper(this);
		Profile.setOutput("{1} {2} {3}");
		
		// Get "freeflying" profiles
		mProfiles = helper.profilesHelper.getProfiles();
		
		/// TODO implement using recursive mehtod which we wait for from admin group!
		// Get profiles from departments
		/*Profile guardianProfile = helper.profilesHelper.getProfileById(guardianID);
		ArrayList<Department> departments = (ArrayList<Department>) helper.departmentsHelper.getDepartmentsByProfile(guardianProfile);
		
		for (Department department : departments) {
			for (Profile profile : helper.profilesHelper.getGuardiansByDepartment(department)) {
				mProfiles.add(profile);
			}
		}*/
		
		// Remove profiles which are not children
		for(int i = 0; i < mProfiles.size(); i++) {
			if (mProfiles.get(i).getPRole() != 3L) {
				mProfiles.remove(i);
				i--;
			}
		}
		
		GProfileAdapter adapter = new GProfileAdapter(this, mProfiles);
		ListView lv = (ListView) findViewById(R.id.profilesList);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	  	childID = ((Profile) parent.getAdapter().getItem(position)).getId();
		            
		            Intent intent = new Intent(Intent.ACTION_MAIN);
		    		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		    		intent.setComponent(new ComponentName(packageName, activityName));
		    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
	                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		    		
		            intent.putExtra("currentChildID", childID);
		            intent.putExtra("currentGuardianID", guardianID);
		            
					startActivity(intent);
		          }
		        });
	}
}