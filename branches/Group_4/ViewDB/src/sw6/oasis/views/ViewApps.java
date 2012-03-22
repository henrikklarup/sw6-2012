package sw6.oasis.views;

import sw6.oasis.controllers.Helper;
import sw6.oasis.viewmodels.App;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewApps extends ListFragment {

	Helper helper;
	App app;
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
		
		app = new App();
		
		tvHeader = (TextView) getView().findViewById(R.id.table_header);
		tvHeader.setText("AppsTable");
		bAdd = (Button) getView().findViewById(R.id.add);
		bAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				app.setName("AppName");
				app.setVersionNumber("versionNumber");
				
				helper.appsHelper.insertApp(app);
				mAdapter.notifyDataSetChanged();
			}
		});
		bDel = (Button) getView().findViewById(R.id.delete);
		bDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				helper.appsHelper.clearAppsTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		Cursor cursor = helper.appsHelper.getApps();
        int[] to = new int[] { R.id.app_column_one, R.id.app_column_two, R.id.app_column_three};

        mAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.app_list, cursor, cursor.getColumnNames(), to);

        setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		app.setId(_id);
		app.setName("AppModified");
		app.setVersionNumber("ModifiedVersion");
		helper.appsHelper.modifyApp(app);
		mAdapter.notifyDataSetChanged();
	}
}