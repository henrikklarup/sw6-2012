package dk.aau.cs.giraf.launcher;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import dk.aau.cs.giraf.launcher.Tools;


public class Home2Activity extends Activity {
	
	//public UpdateTimer ut;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCorrectContentView();

		SlidingDrawer sd = (SlidingDrawer)findViewById(R.id.drawer);
		//this.ut = new UpdateTimer(sd);
		
		if (Tools.isLandscape(this)) {
			

		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		setCorrectContentView();
	}

	private void setCorrectContentView() {
		if (Tools.isLandscape(this)) {
			setContentView(R.layout.home_land);
		} else {
			setContentView(R.layout.home_port);
		}
	}

}
