package sw6.autism.timer.Wombat;

import sw6.oasis.controllers.Helper;
import sw6.oasis.viewmodels.Profile;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomizeFragment extends Fragment{
	Helper helper;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		helper = new Helper(getActivity().getApplicationContext());
		//Write Tag = Detail and Text = Detail Opened in the LogCat
		Log.e("Customize", "Customize Opened");
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Populate the fragment according to the details layout
		View view = inflater.inflate(R.layout.details, container, false);
		return view;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setSettings(int id){
		// TODO: Insert logic to load settings and put them into the view
		TextView view = (TextView)getView().findViewById(R.id.detailsText);
		view.setText(Integer.toString(id));
		Profile profile = new Profile();
		
//		helper.profilesHelper.
	}
}
