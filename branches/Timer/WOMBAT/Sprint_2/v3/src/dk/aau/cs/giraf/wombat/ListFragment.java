package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;
import java.util.List;

import sw6.oasis.controllers.Helper;
import sw6.oasis.models.Profile;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dk.aau.cs.giraf.TimerLib.Child;
import dk.aau.cs.giraf.TimerLib.Guardian;

public class ListFragment extends android.app.ListFragment {
	Helper helper;
	List<Profile> profileList;
	Guardian guard = Guardian.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new Helper(getActivity().getApplicationContext());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<String> values = new ArrayList<String>();
		for(Child c : guard.Children()){
			values.add(c._name);
		}
		/*values.add("Predefineret");

		profileList = helper.profilesHelper.getProfiles();

		for (Profile profile : profileList) {
			values.add(TimerLoader.getProfileName(profile));
		}
*/
		// Inputs the data into the listview according to the string array
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView lv, View view, int position, long id) {
	
		

		// Update the detailfragment
		DetailFragment fragment = (DetailFragment) getFragmentManager()
				.findFragmentById(R.id.detailFragment);
		if (fragment != null && fragment.isInLayout()) {
			fragment.loadSubProfiles(guard.Children().get(position));
		}
	}
}
