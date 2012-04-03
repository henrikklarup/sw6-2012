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
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class SubProfileAdapter extends ArrayAdapter<SubProfile> {

	private ArrayList<SubProfile> items;

	public SubProfileAdapter(Context context, int textViewResourceId,
			ArrayList<SubProfile> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		if(v == null){
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.subprofile_list, null);
		}
		SubProfile sp = items.get(position);
		if( sp != null ){
			ImageView iv = (ImageView)v.findViewById(R.id.subProfilePic);
			TextView tv = (TextView)v.findViewById(R.id.subProfileName);
			
			if(iv != null){
				iv.setImageResource(R.drawable.default_profile);
			}
			if(tv != null){
				tv.setText(sp._name);
			}
			
		}
		return v;
	}
}
