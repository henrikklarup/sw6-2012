package dk.aau.cs.giraf.wombat.drawlib;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.Hourglass;
import dk.aau.cs.giraf.TimerLib.ProgressBar;
import dk.aau.cs.giraf.TimerLib.SubProfile;


public class DrawLibActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SubProfile sub = new Hourglass("", "", 100, 0xff3D3D3D, 0xff000066, 0xffB8B8B8, 0xff000000, 30, true);
//		Guardian guard = Guardian.getInstance();
//		SubProfile sub = guard.getSubProfile();
		if (sub.getAttachment() == null) {
			/* Set the drawing class (which extends View) as the content view */
			View v = genDrawView(sub);
			setContentView(v);
		} else {
			// Get display size
			Display disp = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			disp.getSize(size);
			
			LinearLayout frame = new LinearLayout(this);
			View v = genDrawView(sub);
			v.setTag("main");
			frame.addView(v, size.x/2, size.y);
			
			v = genDrawView(sub.getAttachment());
			v.setTag("attachment");
			frame.addView(v, size.x/2, size.y);
			
			setContentView(frame);
		}

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
