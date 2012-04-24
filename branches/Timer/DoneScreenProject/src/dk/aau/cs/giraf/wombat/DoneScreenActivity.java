package dk.aau.cs.giraf.wombat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class DoneScreenActivity extends Activity {
    /** Called when the activity is first created. */
	
	/* path to the picture on the sdcard on the tablet*/
	private final String imageInSD = "/sdcard/Pictures/donescreen.png";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* decodes the .png and sets the picture in the imageview*/
        Bitmap bm = BitmapFactory.decodeFile(imageInSD);
        ImageView im = (ImageView)findViewById(R.id.done_pict);
        im.setImageBitmap(bm);
    }    
}
