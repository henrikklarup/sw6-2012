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
import android.widget.Toast;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;

public class ViewApps extends ListFragment {

	Helper helper;
	App app;
	Button bAdd, bDel;
	TextView tvHeader;
	ArrayAdapter<App> mAdapter;
	List<App> valueList;
	
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
				Toast.makeText(getActivity().getApplicationContext(), "ADD", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(getActivity().getApplicationContext(), "CLEAR", Toast.LENGTH_SHORT).show();
				helper.appsHelper.clearAppsTable();
				mAdapter.notifyDataSetChanged();
			}
		});
		
		valueList = helper.appsHelper.getApps();

        mAdapter = new ArrayAdapter<App>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, valueList);

        setListAdapter(mAdapter);

	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		long _id = getListAdapter().getItemId(position);
		Toast.makeText(getActivity().getApplicationContext(), "LIST ITEM CLICK", Toast.LENGTH_SHORT).show();
		app.setId(_id);
		app.setName("AppModified");
		app.setVersionNumber("ModifiedVersion");
		helper.appsHelper.modifyApp(app);
		mAdapter.notifyDataSetChanged();
	}
}