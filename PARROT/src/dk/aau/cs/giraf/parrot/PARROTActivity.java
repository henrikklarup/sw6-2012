package dk.aau.cs.giraf.parrot;



import java.util.ArrayList;
import java.util.List;

import parrot.Package.R;
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



		//PARROTDataLoader dataLoader = new PARROTDataLoader(this);
		//PARROTProfile parrotUser = dataLoader.loadProfile();			
		//TODO replace the temp lines with the above lines
		//START TEMP LINES
		Pictogram tempPic= new Pictogram("Koala","/sdcard/Pictures/005.jpg", null, null);
		parrotUser = new PARROTProfile("tempNiels", tempPic);
		Category tempCat = new Category(0,tempPic);
		tempCat.addPictogram(tempPic);
		tempCat.addPictogram(tempPic);
		Pictogram tempPic2 = new Pictogram("Meg", "/sdcard/Pictures/meg.png", null, null);
		tempCat.addPictogram(tempPic2);

		for (int i=0;i<6;i++)
		{
			tempCat.addPictogram(tempPic);
			tempCat.addPictogram(tempPic2);
		}
		parrotUser.addCategory(tempCat);

		Category tempCat2 = new Category(2, tempPic2);
		tempPic = new Pictogram("Bob", "/sdcard/Pictures/007.jpg", null, null);
		tempPic2= new Pictogram("Madeline", "/sdcard/Pictures/003.jpg", null, null);

		for (int i=0;i<6;i++)
		{
			tempCat2.addPictogram(tempPic);
			tempCat2.addPictogram(tempPic2);
		}
		parrotUser.addCategory(tempCat2);
		//END TEMP LINES

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);	//TODO figure out what this does

		Tab tab = actionBar.newTab()
				.setText(R.string.firstTab)
				.setTabListener(new TabListener<SpeechBoardFragment>(this,"speechboard",SpeechBoardFragment.class));
		actionBar.addTab(tab);

		tab = actionBar.newTab()
				.setText(R.string.secondTab)
				.setTabListener(new TabListener<OptionsFragment>(this,"options",OptionsFragment.class));
		actionBar.addTab(tab);

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
	
}