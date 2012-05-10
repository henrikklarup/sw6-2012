package dk.aau.cs.giraf.parrot;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;


public class PARROTActivity extends Activity {

	private static PARROTProfile parrotUser;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main);



		PARROTDataLoader dataLoader = new PARROTDataLoader(this);
		//parrotUser = dataLoader.loadPARROT();			
		//TODO replace the temp lines with the above line
		//START TEMP LINES
		dataLoader.TESTsaveTestProfile();
		parrotUser.setRights(0, true);
		parrotUser.setRights(1, true);
		parrotUser.setRights(2, true);
		//END TEMP LINES

		/* Here all the Tabs in the system is initialized based on whether or not a user
		 * is allowed to use them. If not they will not be initialized.
		 * We wish not make users aware that there exists functionality that they are not
		 * entitled to.
		 * Remember: Make sure the order of the Taps is consistent with the order of their rights in the
		 * 			 Rights array.
		 */
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);	//TODO figure out what this does

		Tab tab = actionBar.newTab()
				.setText(R.string.firstTab)
				.setTabListener(new TabListener<SpeechBoardFragment>(this,"speechboard",SpeechBoardFragment.class));
		actionBar.addTab(tab);
		
		if (parrotUser.getRights(0) == true)
		{
			tab = actionBar.newTab()
					.setText(R.string.secondTab)
					.setTabListener(new TabListener<ManageCategoryFragment>(this,"manage",ManageCategoryFragment.class));
			actionBar.addTab(tab);
		}
		if (parrotUser.getRights(1) == true) 
		{
			tab = actionBar.newTab()
					.setText(R.string.thirdTab)
					.setTabListener(new TabListener<OptionsFragment>(this,"options",OptionsFragment.class));
			actionBar.addTab(tab);
		}
//		if (parrotUser.getRights(2) == true)//TODO put me back in
//		{
//			tab = actionBar.newTab()
//					.setText(R.string.fourthTab)
//					.setTabListener(new TabListener<OptionsFragment>(this,"camera",OptionsFragment.class));
//			actionBar.addTab(tab);
//		}

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		AudioPlayer.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		AudioPlayer.open();
		super.onResume();
	}

	public static PARROTProfile getUser()
	{
		return parrotUser;
	}
	public static void setUser(PARROTProfile user) {
		parrotUser = user;
	}
	
}