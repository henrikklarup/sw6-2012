package dk.aau.cs.giraf.wombat;

import android.app.Activity;
import android.os.Bundle;
import dk.aau.cs.giraf.TimerLib.Guardian;

public class DrawActivity extends Activity {
	private Guardian guard = Guardian.getInstance();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* Set the draw2d class (which extends View) as the content view */
		switch (guard.getSubProfile().formType()) {
		case ProgressBar:
			setContentView(new DrawProgressBar(getApplicationContext()));
			break;

		default:
			break;
		}

	}
}
