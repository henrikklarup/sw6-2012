package dk.aau.cs.giraf.launcher;

import java.util.ArrayList;
import java.util.List;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProfileSelectActivity extends Activity{

	private static List<Profile> mProfiles;
	private List<String> mStringList;
	private static Context context;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profileselect);

		loadProfiles(true);

	}
	private void loadProfiles(boolean isLaunching){
		if (isLaunching && mProfiles != null) {
			return;
		}

		Helper helper = new Helper(this);
		Profile.setOutput("{1} {2} {3}");

		Profile profile = new Profile("Bo","Bent","Bente",3,12345678,null,null);
		helper.profilesHelper.insertProfile(profile);

		mProfiles = helper.profilesHelper.getProfiles();

		ListView lv = (ListView) findViewById(R.id.profilesList);
		mStringList = new ArrayList<String>();

		for(int i = 0; i < mProfiles.size(); i++){
			mStringList.add(mProfiles.get(i).toString());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mStringList);
		lv.setAdapter(adapter);
	}
}