package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SubProfile;
import dk.aau.cs.giraf.oasis.lib.Helper;

public class SubProfileFragment extends android.app.ListFragment {
	Guardian guard = Guardian.getInstance();
	ListView thisListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	// Start the list empty
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(TimerLoader.profileID != -1){
			Helper helper = new Helper(getActivity());
			int position;
			// Marks the selected profile in the guard singleton
			position = guard.publishList().indexOf(helper.profilesHelper.getProfileById(TimerLoader.profileID));
			if(position != -1){
				guard.publishList().get(position).select();
				ArrayList<SubProfile> subprofiles = guard.getChild().SubProfiles();
				SubProfileAdapter adapter = new SubProfileAdapter(getActivity(),
						android.R.layout.simple_list_item_1, subprofiles);
				setListAdapter(adapter);
			}	
			else {
				Toast.makeText(getActivity(), getString(R.string.no_child), Toast.LENGTH_SHORT).show();
				setListAdapter(null);
			}
			
			
		}else {
			setListAdapter(null);
		}
		ListView lv = getListView();
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					final int row, long arg3) {
				
					TextView tv = new TextView(getActivity());
					tv.setText(getString(R.string.delete_description) + " " + guard.getChild().SubProfiles().get(row).name + "?");
					tv.setTextColor(0xFFFFFFFF);
				
					AlertDialog alertDialog = new AlertDialog.Builder(v
							.getContext()).create();
					alertDialog.setTitle(R.string.delete_subprofile_message);
					alertDialog.setView(tv);
					alertDialog.setButton(getText(R.string.delete_yes),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,
										int arg1) {
									if (guard.getChild() != null && guard.getChild().deleteCheck()) {
									guard.getChild().SubProfiles().get(row)
											.delete();
									Toast t = Toast.makeText(getActivity(),
											R.string.delete_subprofile_toast,
											5000);
									t.show();
									loadSubProfiles();
									} else {
										Toast t = Toast.makeText(getActivity(),
												R.string.cannot_delete_subprofile_toast, 5000);
										t.show();
									}
								}
							});

					alertDialog.setButton2(getText(R.string.delete_no),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,
										int arg1) {
									// do nothing

								}
							});

					alertDialog.show();
				return true;
			}
		});
	}

	/**
	 * Inserts the templates on profile id in the details list
	 * 
	 */
	public void loadSubProfiles() {
		if(guard.getChild() != null){
		ArrayList<SubProfile> subprofiles = guard.getChild().SubProfiles();
		SubProfileAdapter adapter = new SubProfileAdapter(getActivity(),
				android.R.layout.simple_list_item_1, subprofiles);
		setListAdapter(adapter);
		}
	}

	public void onListItemClick(ListView lv, View view, int position, long id) {
		if (TimerLoader.subProfileFirstClick) {
			for (int i = 0; i < lv.getChildCount(); i++) {
				lv.getChildAt(i).setBackgroundResource(R.drawable.list);
			}
			TimerLoader.subProfileFirstClick = false;
		}
		for (int i = 0; i < lv.getChildCount(); i++) {
			lv.getChildAt(i).setSelected(false);
		}
		view.setSelected(true);
		
		CustomizeFragment fragment = (CustomizeFragment) getFragmentManager()
				.findFragmentById(R.id.customizeFragment);
		fragment.loadSettings(guard.getChild().SubProfiles().get(position));
	}

}
