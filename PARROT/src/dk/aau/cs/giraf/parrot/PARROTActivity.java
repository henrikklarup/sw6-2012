package dk.aau.cs.giraf.parrot;



import parrot.Package.R;
import android.R.color;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridView;


public class PARROTActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 	       
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);	//TODO figure out what this does
        
        Tab tab = actionBar.newTab()
        		.setText(R.string.artist)		//TODO rename this tab
        		.setTabListener(new TabListener<ArtistFragment>(this,"artist",ArtistFragment.class));
        actionBar.addTab(tab);
        tab = actionBar.newTab()
        		.setText(R.string.album)		//TODO rename this tab
        		.setTabListener(new TabListener<AlbumFragment>(this,"album",AlbumFragment.class));
        actionBar.addTab(tab);
       
    }
}