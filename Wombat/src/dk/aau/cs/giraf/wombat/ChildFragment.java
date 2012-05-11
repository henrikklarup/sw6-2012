package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import dk.aau.cs.giraf.TimerLib.Child;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class ChildFragment extends android.app.ListFragment {
	List<Profile> profileList;
	Guardian guard = Guardian.getInstance();
	ChildAdapter adapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayList<Child> m_childs = guard.publishList();

		// Inputs the data into the listview according to the string array
		adapter = new ChildAdapter(getActivity(),
				android.R.layout.simple_list_item_1, m_childs);
		setListAdapter(adapter);		
	}

	@Override
	public void onListItemClick(ListView lv, View view, int position, long id) {
		if (Guardian.profileFirstClick) {
			for (int i = 0; i < lv.getChildCount(); i++) {
				lv.getChildAt(i).setBackgroundResource(R.drawable.list);
			}
			Guardian.profileFirstClick = false;
		}
		for(int i = 0; i < lv.getChildCount(); i++){
			lv.getChildAt(i).setSelected(false);
		}
		view.setSelected(true);
		
		// Update the fragments
		SubProfileFragment detf = (SubProfileFragment) getFragmentManager()
				.findFragmentById(R.id.subprofileFragment);
		CustomizeFragment custF = (CustomizeFragment)getFragmentManager().findFragmentById(R.id.customizeFragment);
		custF.setDefaultProfile();
		
		if (detf != null) {
			// Marks the selected profile in the guard singleton
			Guardian.profilePosition = position; 
			guard.publishList().get(position).select();
			detf.loadSubProfiles();
			
		}
	}
	
	/**
	 * Inserts the templates on profile id in the details list
	 * 
	 */
	public void loadSubProfiles() {
		ArrayList<Child> m_childs = guard.publishList();

		// Inputs the data into the listview according to the string array
		adapter = new ChildAdapter(getActivity(),
				android.R.layout.simple_list_item_1, m_childs);
		setListAdapter(adapter);
	}
}
