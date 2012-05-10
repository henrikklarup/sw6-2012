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

public class MyChildrenFrag extends ExpandableListFragment {

	Helper helper;
	List<Profile> list;
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

		tvHeader = (TextView) getView().findViewById(R.id.tvChildHeader);
		tvHeader.setText("My Children");

		helper = new Helper(getActivity().getApplicationContext());

		if (MainActivity.guardian != null) {
			updateList();
		} else {
			setListAdapter(null);
		}
	}

	private void updateList() {
		adapter = new ChildListAdapter(getActivity().getApplicationContext());

		list = helper.profilesHelper.getChildrenByGuardian(MainActivity.guardian);

		List<Department> depList = helper.departmentsHelper.getDepartments();

		for(Department d : depList) {
			ArrayList<Profile> result = new ArrayList<Profile>();
			List<Profile> pList = helper.profilesHelper.getChildrenByDepartment(d);		

			for(Profile p : pList) {
				if (!list.contains(p)) {
					result.add(p);
				}
			}
			if (!result.isEmpty()) {
				adapter.AddGroup(d.getName(), result);
			}
		}

		setListAdapter(adapter);
	}

}
