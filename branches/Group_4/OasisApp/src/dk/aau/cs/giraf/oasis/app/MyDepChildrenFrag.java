package dk.aau.cs.giraf.oasis.app;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class MyDepChildrenFrag extends ExpandableListFragment {

	Helper helper;
	List<Profile> list;
	List<Department> depList;
	TextView tvHeader;
	ChildListAdapter adapter;

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

		tvHeader = (TextView) getView().findViewById(R.id.tvChildHeader);
		tvHeader.setText("My Dep. Children");

		if (MainActivity.guardian != null) {
			updateList();
		} else {
			setListAdapter(null);
		}
	}

	private void updateList() {
		adapter = new ChildListAdapter(getActivity().getApplicationContext());

		depList = helper.departmentsHelper.getDepartmentsByProfile(MainActivity.guardian);

		for(Department d : depList) {
			ArrayList<Profile> result = new ArrayList<Profile>();
			List<Profile> pList = helper.profilesHelper.getChildrenByDepartment(d);		

			for(Profile p : pList) {
				result.add(p);
			}
			if (!result.isEmpty()) {
				adapter.AddGroup(d.getName(), result);
			}
		}

		setListAdapter(adapter);
	}
}
