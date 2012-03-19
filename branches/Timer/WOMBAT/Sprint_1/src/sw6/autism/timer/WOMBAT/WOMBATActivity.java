package sw6.autism.timer.WOMBAT;

import android.os.Bundle;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class WOMBATActivity extends ListActivity {
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  String[] profiles = getResources().getStringArray(R.array.profiles_array);
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.main, profiles));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);
	  }
}
