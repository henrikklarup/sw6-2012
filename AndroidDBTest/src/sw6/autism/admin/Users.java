package sw6.autism.admin;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Users extends ListActivity{

	private DataSource datasource;
	ArrayAdapter<User> adapter;
	List<User> values;
	int _position;
	Intent direct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		datasource = new DataSource(this);
		datasource.open();

		values = datasource.getAllUsers();

		adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		_position = position;
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setTitle("Delete");
		ad.setMessage("Delete User?");
		ad.setButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				datasource.deleteUser(values.get(_position));
				direct = new Intent("sw6.autism.admin.USERS");
				finish();
				startActivity(direct);
			}
		});
		ad.setButton2("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		ad.show();
		
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}
