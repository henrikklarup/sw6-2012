package dk.aau.cs.giraf.parrot;



import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;


public class PARROTActivity extends Activity {

	private static PARROTProfile parrotUser;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main);

		/*//Made by kim
		GridView gridview = (GridView) findViewById(R.id.pictogramgrid);
		gridview.setAdapter(new PictogramAdapter(getUser().getCategoryAt(0), this));

		findViewById(R.id.pictogramgrid).setOnDragListener(new BoxDragListener());
		 */



		PARROTDataLoader dataLoader = new PARROTDataLoader(this);
		//PARROTProfile parrotUser = dataLoader.loadProfile();			
		//TODO replace the temp lines with the above lines
		//START TEMP LINES
		dataLoader.TESTsaveTestProfile();
		Pictogram tempPic= new Pictogram("Koala","/sdcard/Pictures/005.jpg", null, null);
		Category tempCat = new Category(0,tempPic);
		tempCat.addPictogram(tempPic);
		tempCat.addPictogram(tempPic);
		Pictogram tempPic2 = new Pictogram("Meg", "/sdcard/Pictures/meg.png", null, null);
		tempCat.addPictogram(tempPic2);
		
		PARROTProfile testProfile = new PARROTProfile("Niels", tempPic);
		
		for (int i=0;i<6;i++)
		{
			tempCat.addPictogram(tempPic);
			tempCat.addPictogram(tempPic2);
		}

		Category tempCat2 = new Category(2, tempPic2);
		tempPic = new Pictogram("Bob", "/sdcard/Pictures/007.jpg", null, null);
		tempPic2= new Pictogram("Madeline", "/sdcard/Pictures/003.jpg", null, null);

		for (int i=0;i<6;i++)
		{
			tempCat2.addPictogram(tempPic);
			tempCat2.addPictogram(tempPic2);
		}
		testProfile.addCategory(tempCat);
		testProfile.addCategory(tempCat2);
		PARROTActivity.setUser(testProfile);
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
					.setTabListener(new TabListener<OptionsFragment>(this,"options",OptionsFragment.class));
			actionBar.addTab(tab);
		}
//		if (parrotUser.getRights(1) == true)//TODO put me back in
//		{
//			tab = actionBar.newTab()
//					.setText(R.string.thirdTab)
//					.setTabListener(new TabListener<AddFromCameraFragment>(this,"camera",AddFromCameraFragment.class));
//			actionBar.addTab(tab);
//		}
		if (parrotUser.getRights(2) == true)
		{
			tab = actionBar.newTab()
					.setText(R.string.fourthTab)
					.setTabListener(new TabListener<OptionsFragment>(this,"options",OptionsFragment.class));
			actionBar.addTab(tab);
		}

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