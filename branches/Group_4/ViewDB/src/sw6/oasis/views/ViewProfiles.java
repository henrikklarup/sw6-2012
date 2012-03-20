package sw6.oasis.views;

import sw6.oasis.controllers.Helper;
import sw6.oasis.viewmodels.Profile;
import sw6.oasis.views.R;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewProfiles extends ListFragment {

	Helper helper;
	Profile profile;
	ArrayAdapter<String> adapter;
	Button bAdd, bDel;
	TextView tvHeader;
	int _position;
	SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tableview, container, false);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		helper = new Helper(getActivity().getApplicationContext());
		
		profile = new Profile();
		
		tvHeader = (TextView) getView().findViewById(R.id.table_header);
		tvHeader.setText("ProfilesTable");
		bAdd = (Button) getView().findViewById(R.id.add);
		bAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				profile.setFirstname("FirstName");
				profile.setMiddlename("MiddleName");
				profile.setSurname("SurName");
				profile.setPicture("Picture");
				profile.setPhone(123456789);
				profile.setRole(2);
				profile.setDepartmentId(321);
				
				helper.insertProfile(profile);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.clearProfilesTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		Cursor cursor = helper.getProfiles();
        int[] to = new int[] { R.id.profile_column_one, R.id.profile_column_two, R.id.profile_column_three, R.id.profile_column_four, R.id.profile_column_five, R.id.profile_column_six, R.id.profile_column_seven, R.id.profile_column_eight};

        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.profile_list, cursor, cursor.getColumnNames(), to);

        setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		profile.setId(_id);
		profile.setFirstname("ModifiedFirstName");
		profile.setMiddlename("ModifiedMiddleName");
		profile.setSurname("ModifiedSurName");
		profile.setPicture("ModifiedPicture");
		profile.setPhone(987654321);
		profile.setRole(1);
		profile.setDepartmentId(123);
		helper.modifyProfile(profile);
		mAdapter.notifyDataSetChanged();
	}
}