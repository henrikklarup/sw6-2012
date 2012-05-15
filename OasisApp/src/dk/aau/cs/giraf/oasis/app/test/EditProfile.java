package dk.aau.cs.giraf.oasis.app.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.app.MainActivity;
import dk.aau.cs.giraf.oasis.app.R;

public class EditProfile extends ActivityInstrumentationTestCase2<MainActivity> {

	private TextView tvName;
	private TextView tvPhone;
	
	public EditProfile() {
		super("dk.aau.cs.giraf.oasis.app", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		MainActivity mainActivity = getActivity();
		tvName = (TextView) mainActivity.findViewById(R.id.tvMyProfileName);
		tvPhone = (TextView) mainActivity.findViewById(R.id.tvMyProfilePhone);
	}
	
	public void testGetProfileName() {
		
		assertTrue();
	}
	

}
