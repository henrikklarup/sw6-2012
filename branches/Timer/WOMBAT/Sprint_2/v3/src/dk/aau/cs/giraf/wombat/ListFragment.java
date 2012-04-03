package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;
import java.util.List;

import sw6.oasis.models.Profile;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import dk.aau.cs.giraf.TimerLib.Child;
import dk.aau.cs.giraf.TimerLib.Guardian;

public class ListFragment extends android.app.ListFragment {
	List<Profile> profileList;
	Guardian guard = Guardian.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TimerLoader.load();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// ArrayList<String> values = new ArrayList<String>();
		// for(Child c : guard.publishList()){
		// values.add(c._name);
		// }
		ArrayList<Child> m_childs = guard.publishList();

		// Inputs the data into the listview according to the string array
		ChildAdapter adapter = new ChildAdapter(getActivity(),
				android.R.layout.simple_list_item_1, m_childs);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView lv, View view, int position, long id) {
		// Update the detailfragment
		DetailFragment fragment = (DetailFragment) getFragmentManager()
				.findFragmentById(R.id.detailFragment);
		if (fragment != null && fragment.isInLayout()) {
			guard.publishList().get(position).select();
			fragment.loadSubProfiles();
		}
	}
}
