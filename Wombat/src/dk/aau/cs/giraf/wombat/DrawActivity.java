package dk.aau.cs.giraf.wombat;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class DrawActivity extends Activity {
	private Guardian guard = Guardian.getInstance();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SubProfile sub = guard.getSubProfile();
		TimerLoader.mainDrawn = false;		// Sets mainDrawn to false, to be able to draw attachment
		if (sub.getAttachment() == null) {
			/* Set the drawing class (which extends View) as the content view */
			View v = genDrawView(sub);
			v.setTag("single");
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
		case DigitalClock:
			return new DrawDigital(getApplicationContext(), sub);
		case Hourglass:
			return new DrawHourglass(getApplicationContext());
		default:
			return null;
		}
	}
}
