package dk.aau.cs.giraf.oasis.app;

import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class MyChildrenFrag extends ListFragment {

	Helper helper;
	List<Profile> list;
	Profile guardian;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.children_view, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		helper = new Helper(getActivity().getApplicationContext());
		
		if (MainActivity.guardianId != -1) {
			guardian = helper.profilesHelper.getProfileById(MainActivity.guardianId);
			list = helper.profilesHelper.getChildrenByGuardian(guardian);
			setListAdapter(new ChildListAdapter(getActivity().getApplicationContext(), list));
		} else {
			setListAdapter(null);
		}
	}

}
