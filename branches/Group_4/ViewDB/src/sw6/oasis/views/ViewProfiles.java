package sw6.oasis.views;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class ViewProfiles extends ListFragment {

	Helper helper;
	Profile profile;
	Button bAdd, bDel;
	TextView tvHeader;
	ArrayAdapter<Profile> mAdapter;
	List<Profile> valueList;
	
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
				profile.setIdCertificate(321);
				
				helper.profilesHelper.insertProfile(profile);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.profilesHelper.clearProfilesTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		valueList = helper.profilesHelper.getProfiles();

        mAdapter = new ArrayAdapter<Profile>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, valueList);

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
		profile.setIdCertificate(123);
		helper.profilesHelper.modifyProfile(profile);
		mAdapter.notifyDataSetChanged();
	}
}