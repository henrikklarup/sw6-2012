package dk.aau.cs.giraf.oasis.app;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentMenuList extends ListFragment {

	String menuItems[] = new String[] {"My Profile", "My Children", "My Departments", "My Dep. Children"};
	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menulist_fragment_view, container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, menuItems);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		FragmentTransaction t = getFragmentManager().beginTransaction();

		switch (position) {
		case 0:
			MyProfileFrag pFrag = new MyProfileFrag();
      		t.replace(R.id.fDetails, pFrag);
      		t.commit();
			break;
		case 1:
			MyChildrenFrag cFrag = new MyChildrenFrag();
      		t.replace(R.id.fDetails, cFrag);
      		t.commit();
			break;
		case 2:
			MyDepartmentsFrag dFrag = new MyDepartmentsFrag();
      		t.replace(R.id.fDetails, dFrag);
      		t.commit();
			break;
		case 3:
			MyDepChildrenFrag dcFrag = new MyDepChildrenFrag();
      		t.replace(R.id.fDetails, dcFrag);
      		t.commit();
			break;
		default:
			Log.e("menulist", "Error in menulist");
		}
	}
}
