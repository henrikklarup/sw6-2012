package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dk.aau.cs.giraf.TimerLib.Child;

public class ChildAdapter extends ArrayAdapter<Child> {

	private ArrayList<Child> items;

	public ChildAdapter(Context context, int textViewResourceId,
			ArrayList<Child> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.profile_list, null);
		}
		Child c = items.get(position);
		if (c != null) {
			ImageView iv = (ImageView) v.findViewById(R.id.profilePic);
			TextView tv = (TextView) v.findViewById(R.id.profileName);

			if (iv != null) {
				iv.setImageResource(R.drawable.default_profile);
			}
			if (tv != null) {
				if (c.name == "Last Used") {
					tv.setText(R.string.last_used);
				} else if (c.name == "Predefined Profiles") {
					tv.setText(R.string.predefined);
				} else {
					tv.setText(c.name);
				}
			}

		}
		if(c.getId() == TimerLoader.profileID){
			v.setBackgroundResource(R.drawable.list_selected);
			TimerLoader.profileFirstClick = true;
			TimerLoader.profileID = -1;
		} else {
			v.setBackgroundResource(R.drawable.list);
		}
		return v;
	}
}
