package dk.aau.cs.giraf.parrot;



import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import parrot.Package.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;


public class PARROTActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        PARROTProfile parrotUser = loadProfile();
        
       
        
        
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
        	//TODO load medias into pictogram categories using settings.
        	return parrotUser;
        }
        else
        {
        	//If no profile is found, return null.
        	//TODO find out if this means that a Guardian is using the PARROT app.
        	return null;
        }
    	
    }
    
    public void saveProfile(PARROTProfile user)
    {
    	//TODO write this method so that it sends the changes to the admin.
    	
    }
}