package dk.aau.cs.giraf.parrot;



import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;
import parrot.Package.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract.Directory;
import android.widget.GridView;


public class PARROTActivity extends Activity {
	
	private static PARROTProfile parrotUser;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//PARROTProfile parrotUser = loadProfile();	
		
		//TODO replace the temp lines with the above line
		Pictogram tempPic= new Pictogram("Koala", getExternalFilesDir(null)+"/Pictures/005.jpg", null, null);//005
		parrotUser = new PARROTProfile("tempNiels", tempPic);
		Category tempCat = new Category(0);
		tempCat.addPictogram(tempPic);
		tempCat.addPictogram(tempPic);




		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);	//TODO figure out what this does

		Tab tab = actionBar.newTab()
				.setText(R.string.firstTab)		//TODO rename this tab
				.setTabListener(new TabListener<SpeechBoardFragment>(this,"speechboard",SpeechBoardFragment.class));
		actionBar.addTab(tab);

		tab = actionBar.newTab()
				.setText(R.string.secondTab)		//TODO rename this tab
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

	public PARROTProfile loadProfile()
	{
		//This part of the code is supposed to get a profile from the launcher, and read it from the admin.
		Bundle extras = getIntent().getExtras();
		Profile prof;
		Helper help = new Helper(this);
		if(extras !=null)
		{
			prof = help.profilesHelper.getProfileById(extras.getLong("currentProfileId"));
			Pictogram pic = new Pictogram(prof.getFirstname(), prof.getPicture(), null, null);
			PARROTProfile parrotUser = new PARROTProfile(prof.getFirstname(), pic);

			//TODO read categories from settings
			Setting<String, String, String> specialSettings = null;

			//TODO load medias into pictogram categories using settings.

			//Add all of the categories to the profile
			int number = 0;
			String categoryString=null;
			while (true)
			{
				categoryString = specialSettings.get(prof.getFirstname()).get("category"+number);
				if(categoryString !=null)
				{
					String colourString = specialSettings.get(prof.getFirstname()).get("category"+number+"colour");
					int col=Integer.valueOf(colourString);
					//If the category of that number exists
					parrotUser.addCategory(loadCategory(categoryString,col,help));
				}
				else
				{
					break;
				}

			}

			return parrotUser;
		}
		else
		{
			//If no profile is found, return null.
			//TODO find out if this means that a Guardian is using the PARROT app.
			return null;
		}

	}

	public Category loadCategory(String pictureIDs,int colour,Helper help)
	{
		Category cat = new Category(colour);
		List<Integer> listIDs = getIDsFromString(pictureIDs);
		for(int i = 0; i<listIDs.size();i++)
		{
			cat.addPictogram(loadPictogram(listIDs.get(i),help));
		}
		return cat;
	}

	public Pictogram loadPictogram(int id,Helper help)
	{
		Pictogram pic = null;
		Media media=help.mediaHelper.getSingleMediaById(id);
		//Media files can have a link to a sub-media file.
		//TODO Make it so that image-Media files have sound-Media files and word-Media files as sub media links.
		return pic;
	}

	//This function takes a string consisting of IDs, and returns a list of integer IDs instead
	public List<Integer> getIDsFromString(String IDstring)
	{
		List<Integer> listOfID = null;
		
		if(IDstring !=null || IDstring.charAt(0)!='$'||IDstring.charAt(0)!='#')
		{
			String temp = String.valueOf(IDstring.charAt(0));
			int w = 0;
			while(IDstring.charAt(w)!='$')
			{
				w++;
				if(IDstring.charAt(w)!='#')
				{
					temp = temp+ IDstring.charAt(w);
				}
				else
				{
					listOfID.add(Integer.valueOf(temp));
					w++;
					temp = String.valueOf(IDstring.charAt(w));
				}

			}
		}

		return listOfID;
	}

	public void saveProfile(PARROTProfile user)
	{
		//TODO write this method so that it sends the changes to the admin.
		//Seems easy enough, as the admin functionality will automatically replace the media files.

	}
}