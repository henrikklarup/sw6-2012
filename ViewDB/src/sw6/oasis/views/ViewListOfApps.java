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
import dk.aau.cs.giraf.oasis.lib.models.ListOfApps;

public class ViewListOfApps extends ListFragment {

	Helper helper;
	ListOfApps loa;
	Button bAdd, bDel;
	TextView tvHeader;
	ArrayAdapter<ListOfApps> mAdapter;
	List<ListOfApps> valueList;
	
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
		loa = new ListOfApps();
		
		tvHeader = (TextView) getView().findViewById(R.id.table_header);
		tvHeader.setText("ListOfAppsTable");
		bAdd = (Button) getView().findViewById(R.id.add);
		bAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loa.setSettings("Setting");
				loa.setStats("Stat");
				loa.setAppId(123);
				loa.setProfileId(321);
				
				helper.listOfAppsHelper.insertListOfApps(loa);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.listOfAppsHelper.clearListOfAppsTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		valueList = helper.listOfAppsHelper.getListOfApps();

        mAdapter = new ArrayAdapter<ListOfApps>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, valueList);

        setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		loa.setId(_id);
		loa.setSettings("ModifiedSetting");
		loa.setStats("ModifiedStat");
		loa.setAppId(123);
		loa.setProfileId(321);
		helper.listOfAppsHelper.modifyListOfApps(loa);
		mAdapter.notifyDataSetChanged();
	}
}