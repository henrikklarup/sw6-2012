package dk.aau.cs.giraf.wombat.drawlib;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DoneScreenActivity extends Activity {
    /** Called when the activity is first created. */
	private final String imageInSD = "/sdcard/Pictures/faerdig.png";
	private final String text = "Færdig";
	
	/* path to the picture on the sdcard on the tablet*/
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //TODO: get extra data
        
        LinearLayout frame = new LinearLayout(this);
        frame.setOrientation(LinearLayout.VERTICAL);
        
        ImageView iv = new ImageView(getApplicationContext());
		iv.setScaleType(ScaleType.CENTER_INSIDE);

		Bitmap bm = BitmapFactory.decodeFile(imageInSD);
        iv.setImageBitmap(bm);
        frame.addView(iv);
		
		TextView tv = new TextView(getApplicationContext());
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		tv.setLayoutParams(lp);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 80);
		tv.setTextColor(0xFFFFFFFF);
		tv.setText(text);
		frame.addView(tv);
        setContentView(frame);
		
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    }    
}
