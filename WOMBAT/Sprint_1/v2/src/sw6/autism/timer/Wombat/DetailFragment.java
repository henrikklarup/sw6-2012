package sw6.autism.timer.Wombat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DetailFragment extends ListFragment{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//Write Tag = Detail and Text = Detail Opened in the LogCat
		Log.e("Detail", "Detail Opened");
	}
	
	@Override
	// Start the list empty
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		// TODO: Implement bind to the profile chosen by launcher
		setListAdapter(null);
	}
		
	public void getTemplates(int id){
		// TODO: Insert logic to get templates according to ID
		String[] values = {Integer.toString(id), Integer.toString(id+1), Integer.toString(id+2)};
		
		// Create and input adapter with the string array loaded (CHANGE 'values' to get another input)
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView lv, View view, int position, long id){
		// TODO: Call intent to tilpas with somekind of object with settings
		int profileId = getResources().getIntArray(R.array.ids)[position];
		CustomizeFragment fragment = (CustomizeFragment)getFragmentManager().findFragmentById(R.id.customizeFragment);
		fragment.setSettings(profileId);
	}
}
