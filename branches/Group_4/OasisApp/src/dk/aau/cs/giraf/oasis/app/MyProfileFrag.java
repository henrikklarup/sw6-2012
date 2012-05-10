package dk.aau.cs.giraf.oasis.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyProfileFrag extends Fragment {

	private Button bMedia, bApps;
	private TextView tvName, tvPhone;
	private String name; 

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

		tvName = (TextView) getView().findViewById(R.id.tvMyProfileName);
		name = MainActivity.guardian.getFirstname();
		if (MainActivity.guardian.getMiddlename() != null) {
			name += " " + MainActivity.guardian.getMiddlename();
		}
		name += " " + MainActivity.guardian.getSurname();
		tvName.setText(name);
		tvPhone = (TextView) getView().findViewById(R.id.tvMyProfilePhone);
		tvPhone.setText("" + MainActivity.guardian.getPhone());

		bMedia = (Button) getView().findViewById(R.id.bMyProfileMedia);
		bMedia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragParentTab.switchTab(FragParentTab.TABMEDIA);
			}
		});

		bApps = (Button) getView().findViewById(R.id.bMyProfileApps);
		bApps.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragParentTab.switchTab(FragParentTab.TABAPP);
			}
		});
	}
}
