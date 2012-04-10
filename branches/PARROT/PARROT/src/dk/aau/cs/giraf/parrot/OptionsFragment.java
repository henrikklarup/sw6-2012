package dk.aau.cs.giraf.parrot;


import parrot.Package.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OptionsFragment extends Fragment
{
	
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{        
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.options_layout, container, false);
	}
}
