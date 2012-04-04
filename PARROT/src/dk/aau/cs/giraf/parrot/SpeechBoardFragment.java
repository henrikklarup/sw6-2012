package dk.aau.cs.giraf.parrot;


import parrot.Package.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.widget.GridView;

public class SpeechBoardFragment extends Fragment
{
	public SpeechBoardFragment()
	{
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{        
		


		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.speechboard_layout, container, false);
	}
}
