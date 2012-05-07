package dk.aau.cs.giraf.wombat.drawlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.ProgressBar;
import dk.aau.cs.giraf.TimerLib.SubProfile;
import dk.aau.cs.giraf.TimerLib.TimeTimer;


public class DrawLibActivity extends Activity {


	public static int frameHeight;
	public static int frameWidth;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
//		SubProfile sub = new ProgressBar("", "", 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 3, false);
//		SubProfile sub2 = new ProgressBar("", "", 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 900, true);
//		sub.setAttachment(sub2);
		Guardian guard = Guardian.getInstance();
		SubProfile sub = guard.getSubProfile();
		
		
		// Get display size
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		frameHeight = disp.getHeight();
		frameWidth = disp.getWidth();
				
		
		if (sub.getAttachment() == null) {
			/* Set the drawing class (which extends View) as the content view */
			View v = genDrawView(sub);
			v.setKeepScreenOn(true);
			setContentView(v);
		} else {
			frameWidth = frameWidth/2;
			
			LinearLayout frame = new LinearLayout(this);
			frame.setKeepScreenOn(true);
			View v = genDrawView(sub);
			frame.addView(v, frameWidth, frameHeight);
			
			
			//TODO: Attachment is working different now so this will crash ! :D
			//TODO: Add picture
			View v2 = genDrawView(sub.getAttachment());
			frame.addView(v2, frameWidth, frameHeight);
			
			setContentView(frame);	
		}
		
		new Handler().postDelayed(new Runnable() {
            public void run() {
                final Intent mainIntent = new Intent(DrawLibActivity.this, DoneScreenActivity.class);
                //TODO: putExtra data with picture and text information
                DrawLibActivity.this.startActivity(mainIntent);
                DrawLibActivity.this.finish();
            }
        }, sub.get_totalTime()*1000);
	}
	
	private View genDrawView(SubProfile sub) {
		switch (sub.formType()) {
		case ProgressBar:
			return new DrawProgressBar(getApplicationContext(), sub);
		case Hourglass:
			return new DrawHourglass(getApplicationContext(), sub);
		case DigitalClock:
			return new DrawDigital(getApplicationContext(), sub);
		case TimeTimer:
			return new DrawWatch(getApplicationContext(), sub);
		default:
			return null;
		}
	}
}
