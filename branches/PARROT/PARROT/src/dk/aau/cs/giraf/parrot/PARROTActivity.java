package dk.aau.cs.giraf.parrot;

import com.example.hellogridview.ImageAdapter;

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
        grid.setAdapter(null);
    }
}