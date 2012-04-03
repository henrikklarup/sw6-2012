package dk.aau.cs.giraf.parrot;



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
        setContentView(R.layout.main);
 	    
        Category temp1 = new Category(0xff0000); //TODO dummy variable. Change this when database is up.
        Pictogram pic = new Pictogram("001", "/root/pictures/001.jpg", null, null);
        temp1.addPictogram(pic);
        pic = new Pictogram("002", "/root/pictures/002.jpg", null, null);
        temp1.addPictogram(pic);
        pic = new Pictogram("003", "/root/pictures/003.jpg", null, null);
        temp1.addPictogram(pic);
        pic = new Pictogram("004", "/root/pictures/004.jpg", null, null);
        temp1.addPictogram(pic);
        pic = new Pictogram("005", "/root/pictures/005.jpg", null, null);
        temp1.addPictogram(pic);
        pic = new Pictogram("006", "/root/pictures/006.jpg", null, null);
        temp1.addPictogram(pic);
        pic = new Pictogram("007", "/root/pictures/007.jpg", null, null);
        temp1.addPictogram(pic);
        pic = new Pictogram("008", "/root/pictures/008.jpg", null, null);
        temp1.addPictogram(pic);
         
        GridView superCategory = (GridView) findViewById(R.id.supercategory); //This is to show the supercategories
		superCategory.setAdapter(new PictogramAdapter(temp1, this)); //FIXME
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);	//TODO figure out what this does
        
        Tab tab = actionBar.newTab()
        		.setText(R.string.firstTab)		//TODO rename this tab
        		.setTabListener(new TabListener<SpeechBoardFragment>(this,"speechboard",SpeechBoardFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
        		.setText(R.string.firstTab)		//TODO rename this tab
        		.setTabListener(new TabListener<SpeechBoardFragment>(this,"speechboard2",SpeechBoardFragment.class));
        actionBar.addTab(tab);
       
    }
}