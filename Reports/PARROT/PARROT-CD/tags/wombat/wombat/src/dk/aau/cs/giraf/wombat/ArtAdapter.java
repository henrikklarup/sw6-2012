package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dk.aau.cs.giraf.TimerLib.Art;

public class ArtAdapter extends ArrayAdapter<Art> {

	private ArrayList<Art> items;

	public ArtAdapter(Context context, int textViewResourceId,
			ArrayList<Art> items) {
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
		Art c = items.get(position);
		if (c != null) {
			ImageView iv = (ImageView) v.findViewById(R.id.profilePic);
			TextView tv = (TextView) v.findViewById(R.id.profileName);

			if (iv != null) {
				iv.setImageResource(c.getPath());
			}
			if (tv != null) {
				tv.setText(c.getName());
			}

		}
		
//			if(c.getProfileId() == Guardian.profileID){
//				v.setBackgroundResource(R.drawable.list_selected);
//				Guardian.profileFirstClick = true;
//				Guardian.profileID = -1;
//			}
		return v;
	}
}
