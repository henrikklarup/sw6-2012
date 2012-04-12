package dk.aau.cs.giraf.wombat;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class SubProfileFragment extends Fragment {
	Guardian guard = Guardian.getInstance("John");
	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Write Tag = Detail and Text = Detail Opened in the LogCat
		Log.e("Detail", "Detail Opened");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Populate the fragment according to the details layout
		return inflater.inflate(R.layout.details, container, false);
	}

	@Override
	// Start the list empty
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		lv = (ListView) getActivity().findViewById(R.id.list);
		if (guard.selected() != null) {
			ArrayList<SubProfile> subprofiles = guard.selected().SubProfiles();
			SubProfileAdapter adapter = new SubProfileAdapter(getActivity(),
					android.R.layout.simple_list_item_1, subprofiles);
			lv.setAdapter(adapter);
		} else {
			lv.setAdapter(null);
		}
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					int row, long arg3) {
				AlertDialog alertDialog = new AlertDialog.Builder(v
						.getContext()).create();
				alertDialog.setTitle(R.string.delete_subprofile_message);
				alertDialog.setButton(getText(R.string.delete_yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub

								Toast t = Toast.makeText(getActivity(),
										"Profile Deleted", 5000); // TODO: Which
																	// profile
																	// has been
																	// deleted?
								t.show();
								reloadSubProfiles();
							}
						});

				alertDialog.setButton2(getText(R.string.delete_no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub

							}
						});

				alertDialog.show();
				return true;
			}
		});
		/* Ny Kode */
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				for (int i = 0; i < lv.getChildCount(); i++) {
					lv.getChildAt(i).setSelected(false);
				}
				view.setSelected(true);

				CustomizeFragment cf = (CustomizeFragment) getFragmentManager()
						.findFragmentById(R.id.customizeFragment);
				cf.loadSettings(guard.selected().SubProfiles().get(position));

			}
		});
		/* Ny Kode slut */
	}

	public void reloadSubProfiles() {
		loadSubProfiles();
	}

	/**
	 * Inserts the templates on profile id in the details list
	 * 
	 */
	public void loadSubProfiles() {
		ArrayList<SubProfile> subprofiles = guard.selected().SubProfiles();
		SubProfileAdapter adapter = new SubProfileAdapter(getActivity(),
				android.R.layout.simple_list_item_1, subprofiles);
		lv.setAdapter(adapter);
	}
	/*
	 * public void onListItemClick(ListView lv, View view, int position, long
	 * id) { for (int i = 0; i < lv.getChildCount(); i++) {
	 * lv.getChildAt(i).setSelected(false); } view.setSelected(true);
	 * 
	 * CustomizeFragment fragment = (CustomizeFragment) getFragmentManager()
	 * .findFragmentById(R.id.customizeFragment);
	 * fragment.loadSettings(guard.selected().SubProfiles().get(position)); }
	 */
}
