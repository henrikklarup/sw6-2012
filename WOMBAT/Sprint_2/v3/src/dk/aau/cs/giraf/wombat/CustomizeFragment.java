package dk.aau.cs.giraf.wombat;

import sw6.oasis.controllers.Helper;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
		View view = inflater.inflate(R.layout.customize, container, false);
		return view;
	}
	
	/**
	 * Sets the predefined settings of the chosen template
	 * @param id identification of the template
	 */
	public void setSettings(Template template){
		// TODO: Insert logic to load settings and put them into the view
//		TextView view = (TextView)getView().findViewById(R.id.customizeHeader);
//		view.setText(template.getName());
//		Profile profile = new Profile();
		
//		helper.profilesHelper.
	}
}
