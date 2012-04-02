package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dk.aau.cs.giraf.TimerLib.Child;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class DetailFragment extends android.app.ListFragment {
	Guardian guard = Guardian.getInstance("John");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Write Tag = Detail and Text = Detail Opened in the LogCat
		Log.e("Detail", "Detail Opened");

	}

	@Override
	// Start the list empty
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// TODO: Implement bind to the profile chosen by launcher
		setListAdapter(null);
	}

	/**
	 * Inserts the templates on profile id in the details list
	 * 
	 * @param id
	 *            id of the template profile
	 */
	private Child selectedChild;
	public void loadSubProfiles(Child child) {

		selectedChild = child;
		
		List<String> values = new ArrayList<String>();

		for (SubProfile p : child.Profiles()) {
			values.add(p._name);
		}

		Log.e("Wombat", "Templates loaded");
		// Create and input adapter with the string array loaded (CHANGE
		// 'values' to get another input)
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	public void onListItemClick(ListView lv, View view, int position, long id) {

		CustomizeFragment fragment = (CustomizeFragment) getFragmentManager()
				.findFragmentById(R.id.customizeFragment);
		fragment.setSettings(selectedChild.Profiles().get(position));
	}
}
