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
import dk.aau.cs.giraf.TimerLib.Guardian;

public class ChildAdapter extends ArrayAdapter<Child> {

	private ArrayList<Child> items;
	Guardian guard = Guardian.getInstance();

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
		// TODO: Pictures
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
		
			if(c.getProfileId() == guard.profileID){
				v.setBackgroundResource(R.drawable.list_selected);
				guard.profileFirstClick = true;
			}
		return v;
	}
}
