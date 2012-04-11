package sw6.autism.timer.Wombat;

import java.util.ArrayList;
import java.util.List;

import sw6.autism.timer.Wombat.R;
import sw6.autism.timer.Wombat.R.id;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DetailFragment extends android.app.ListFragment{
	List<Template> templates;
	
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
		
	/**
	 * Inserts the templates on profile id in the details list
	 * @param id id of the template profile
	 */
	public void getTemplates(long id){
		templates = TimerLoader.getTemplatesById(id);
		
		List<String> values = new ArrayList<String>();
		
		for (Template temp : templates) {
			values.add(temp.getInfo());
		}
		
		Log.e("Wombat", "Templates loaded");
		// Create and input adapter with the string array loaded (CHANGE 'values' to get another input)
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView lv, View view, int position, long id){
		lv.setItemChecked(position, true);
		
		Template template = templates.get(position);
		CustomizeFragment fragment = (CustomizeFragment)getFragmentManager().findFragmentById(R.id.customizeFragment);
		fragment.setSettings(template);
	}
}
