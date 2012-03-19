package dk.sw6.admin.views;

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
import dk.sw6.admin.controllers.Helper;

public class ViewApps extends ListFragment {

	Helper helper;
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
		
		tvHeader = (TextView) getView().findViewById(R.id.table_header);
		tvHeader.setText("AppsTable");
		bAdd = (Button) getView().findViewById(R.id.add);
		bAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.insertApp("NewApp", );
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.clearAppsTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		Cursor cursor = helper.getApps();
        int[] to = new int[] { R.id.column_one, R.id.column_two};

        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_example, cursor, cursor.getColumnNames(), to);

        setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		helper.modifyApp(_id, "AppModified");
		mAdapter.notifyDataSetChanged();
	}
}