package sw6.autism.timer.Wombat;

import java.util.ArrayList;
import java.util.List;

import sw6.oasis.controllers.Helper;
import sw6.oasis.viewmodels.App;
import sw6.oasis.viewmodels.Profile;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListFragment extends android.app.ListFragment {
	Helper helper;
	List<Profile> profileList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new Helper(getActivity().getApplicationContext());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<String> values = new ArrayList<String>();
		values.add("Predefineret");

		profileList = helper.profilesHelper.getProfiles();

		for (Profile profile : profileList) {
			values.add(StartLoader.getProfileName(profile));
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
			// "Predefineret" or "Sidste Brugt" has been selected.
			profileId = -2; // "Predefineret"
		} else {
			// Get id from database according to position (-1 because the first
			// item is "Predefineret")
			Profile profile = profileList.get(position);
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
