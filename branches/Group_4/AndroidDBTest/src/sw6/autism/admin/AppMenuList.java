package sw6.autism.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.app.ListActivity;

public class AppMenuList extends ListActivity{
	
	String applications[] = new String[] {"Users", "Create", "test", "felt", "field"};
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, applications);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		try{
			Class _class = Class.forName("sw6.autism.admin." + applications[position]);
			Intent _intent = new Intent(AppMenuList.this, _class);
			startActivity(_intent);
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}
