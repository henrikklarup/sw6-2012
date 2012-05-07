package dk.aau.cs.giraf.oasis.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MyProfileFrag extends Fragment {
	
	Button bMedia, bApps;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.myprofile_view, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		bMedia = (Button) getView().findViewById(R.id.bMyProfileMedia);
		bMedia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTransaction t = getFragmentManager().beginTransaction();
				MenuListMedia mFrag = new MenuListMedia();
	      		t.replace(R.id.fMenuList, mFrag);
	      		t.commit();
			}
		});
	}
	
}
