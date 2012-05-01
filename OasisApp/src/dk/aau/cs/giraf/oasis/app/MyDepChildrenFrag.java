package dk.aau.cs.giraf.oasis.app;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class MyDepChildrenFrag extends ListFragment {

	Helper helper;
	List<Profile> list;
	Profile guardian;
	List<Department> depList;

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
			list = new ArrayList<Profile>();
			guardian = helper.profilesHelper.getProfileById(MainActivity.guardianId);
			depList = helper.departmentsHelper.getDepartmentsByProfile(guardian);
			
			for (Department d : depList) {
				list.addAll(helper.profilesHelper.getChildrenByDepartmentAndSubDepartments(d));
			}
			setListAdapter(new ChildListAdapter(getActivity().getApplicationContext(), list));
		} else {
			setListAdapter(null);
		}
	}

}
