package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
			ImageView ivBG = (ImageView)v.findViewById(R.id.subProfilePicBackground);
			TextView tvName = (TextView)v.findViewById(R.id.subProfileName);
			TextView tvDesc = (TextView)v.findViewById(R.id.subProfileDesc);
			
			if(iv != null){
				switch(sp.formType()){
				case Hourglass:
					iv.setImageResource(R.drawable.thumbnail_hourglass);
					break;
				case DigitalClock:
					iv.setImageResource(R.drawable.thumbnail_digital);
					break;
				case ProgressBar:
					iv.setImageResource(R.drawable.thumbnail_progressbar);
					break;
				case TimeTimer:
					iv.setImageResource(R.drawable.thumbnail_timetimer);
					break;
				default:
					iv.setImageResource(R.drawable.thumbnail_hourglass);
					break;
				}
			}
			if(ivBG != null){
				
				ivBG.setBackgroundColor(sp._timeLeftColor);
				
			}
			if(tvName != null){
				tvName.setText(sp._name);
			}
			if(tvDesc != null){
				tvDesc.setText(sp._desc);
			}
			
		}

		OnLongClickListener myListener = new OnLongClickListener() {
			
			public boolean onLongClick(final View v) {
				AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                alertDialog.setTitle(R.string.delete_subprofile_message);
                alertDialog.setButton(getContext().getText(R.string.delete_yes), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
                });
                
                alertDialog.setButton2(getContext().getText(R.string.delete_no), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
                });
                
                alertDialog.show();
				return false;
			}
		};
		v.setOnLongClickListener(myListener);
		return v;
	}
}
