package sw6.oasis.views;

import sw6.oasis.controllers.Helper;
import sw6.oasis.viewmodels.ListOfApps;
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

public class ViewListOfApps extends ListFragment {

	Helper helper;
	ListOfApps loa;
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
				
				helper.insertListOfApps(loa);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.clearListOfAppsTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		Cursor cursor = helper.getListOfApps();
        int[] to = new int[] { R.id.listofapp_column_one, R.id.listofapp_column_two, R.id.listofapp_column_three, R.id.listofapp_column_four, R.id.listofapp_column_five};

        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.listofapp_list, cursor, cursor.getColumnNames(), to);

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
		helper.modifyListOfApps(loa);
		mAdapter.notifyDataSetChanged();
	}
}