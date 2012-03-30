package dk.aau.cs.giraf.parrot;


import java.util.List;

import parrot.Package.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;


public class PARROTActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Implementing gridview
        setContentView(R.layout.pictogramgrid);
        GridView grid = (GridView)findViewById(R.id.gridview);
        
        List<Pictogram> pictograms = null;//TODO replace this dummy variable with a database input
        
        grid.setAdapter(new PictogramAdapter(pictograms, this));
    }
}