package dk.aau.cs.giraf.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import dk.aau.cs.giraf.launcher.Tools;


public class Home2Activity extends Activity {
	
	//private int key = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCorrectContentView();
		
		if (Tools.isLandscape(this)) {
			final LinearLayout popup = (LinearLayout)findViewById(R.id.popup_window);
			popup.setVisibility(View.GONE);
			final Button btn = (Button)findViewById(R.id.show_popup_button);
			
			btn.setOnClickListener(new View.OnClickListener() {
				private int key = 0;
				
				
				@Override
				public void onClick(View arg0) {
					Log.i("magnus","key: " + key);
					if (key == 0) {
						key = 1;
						popup.setVisibility(View.VISIBLE);
						//btn.setBackgroundResource(R.drawable.slid3_n);
						btn.setText("VISIBLE");
					} else {
						key = 0;
						popup.setVisibility(View.GONE);
						btn.setText("gone");
						//btn.setBackgroundResource(R.drawable.slid4_n);
					}
				}
			});
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
