package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class ListFragment extends android.app.ListFragment {
	Helper helper;
	List<Profile> profileList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new Helper(getActivity().getApplicationContext());
		
//		for(int i = 0; i < 12; i++){
//			Profile profile = new Profile();
//			profile.setDepartmentId(1110);
//			profile.setFirstname("Bjørn");
//			profile.setPhone(12345678);
//			profile.setPicture("NONE");
//			profile.setRole(2);
//			profile.setSurname("#" + i);
//			
//			helper.profilesHelper.insertProfile(profile);
//		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<String> values = new ArrayList<String>();
		values.add(getText(R.string.predefined).toString());
		values.add(getText(R.string.last_used).toString());

		profileList = helper.profilesHelper.getProfiles();

		for (Profile profile : profileList) {
			values.add(TimerLoader.getProfileName(profile));
		}

		// Inputs the data into the listview according to the string array
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView lv, View view, int position, long id) {
		long profileId;

		if (position == 0) {
			// "Predefineret" has been selected.
			profileId = -2; // "Predefineret"
		} else if (position == 1){
			// "Predefineret" has been selected.
			profileId = -1;
		} else {
			// Get id from database according to position (-1 because the first
			// item is "Predefineret")
			Profile profile = profileList.get(position-1);
			profileId = profile.getId();
		}

		// Update the detailfragment
		DetailFragment fragment = (DetailFragment) getFragmentManager()
				.findFragmentById(R.id.detailFragment);
		if (fragment != null && fragment.isInLayout()) {
			fragment.getTemplates(profileId);
		} else {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					DetailActivity.class);

			intent.putExtra("value", profileId);
			startActivity(intent);
		}
	}
}
