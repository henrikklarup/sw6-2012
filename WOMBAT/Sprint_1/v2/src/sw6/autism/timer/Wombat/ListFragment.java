package sw6.autism.timer.Wombat;

import sw6.oasis.controllers.Helper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListFragment extends android.app.ListFragment {
	Helper helper;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		helper = new Helper(getActivity().getApplicationContext());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/********** DUMMY DATA **********/
		// insert correct string array here
		String[] values = getResources().getStringArray(R.array.profiles);

		// Inputs the data into the listview according to the string array
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView lv, View view, int position, long id) {
		/********** DUMMY DATA **********/
		// Get id from database according to position
		int profileId = getResources().getIntArray(R.array.ids)[position];

		// Update the detailfragment
		DetailFragment fragment = (DetailFragment) getFragmentManager()
				.findFragmentById(R.id.detailFragment);
		if (fragment != null && fragment.isInLayout()) {
			fragment.getTemplates(profileId);
		} else {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					DetailActivity.class);

			intent.putExtra("value", Integer.toString(profileId));
			startActivity(intent);
		}
	}
}
