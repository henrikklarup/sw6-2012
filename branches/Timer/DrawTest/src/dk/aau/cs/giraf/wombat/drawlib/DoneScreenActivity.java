package dk.aau.cs.giraf.wombat.drawlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import dk.aau.cs.giraf.TimerLib.Attachment;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SplitImg;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class DoneScreenActivity extends Activity {
	/** Called when the activity is first created. */
	private final String imageInSD = "/sdcard/Pictures/faerdig.png";
	private final String text = "Færdig";
	Guardian guard = Guardian.getInstance();
	/* path to the picture on the sdcard on the tablet*/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		  View main_layout = findViewById(android.R.id.content).getRootView();
		  main_layout.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		int frameHeight = disp.getHeight();
		int frameWidth = disp.getWidth();
		//TODO: get extra data
		LinearLayout frame = new LinearLayout(this);
		frame.setOrientation(LinearLayout.HORIZONTAL);
		ImageView i = null;
		ImageView i2 = null;
		SubProfile sub = guard.getSubProfile();
		
		if(sub.getDoneArt() != null){
			switch(sub.getDoneArt().getForm()){
			case SingleImg:
				i = new ImageView(this);
				i.setImageResource(sub.getDoneArt().getImg().getPath());
				i.setBackgroundColor(000);
				frame.addView(i, frameWidth, frameHeight);
				break;
			case SplitImg:
				frameWidth = frameWidth/2;
				i = new ImageView(this);
				i.setImageResource(sub.getDoneArt().getLeftImg().getPath());
				i.setBackgroundColor(000);
				frame.addView(i, frameWidth, frameHeight);

				i2 = new ImageView(this);
				i2.setImageResource(sub.getDoneArt().getRightImg().getPath());
				i2.setBackgroundColor(000);
				frame.addView(i2, frameWidth, frameHeight);
				break;
			}
		} else {
        	i = new ImageView(this);
			i.setImageResource(guard.ArtList.get(0).getPath());
			i.setBackgroundColor(000);
			frame.addView(i, frameWidth, frameHeight);
		}
		
		frame.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// Start 
				Intent i = new Intent("dk.aau.cs.giraf.launcher.AUTHENTICATE");
				i.addCategory("dk.aau.cs.giraf.launcher.GIRAF");
				startActivity(i);
				finish();
			}
		});

		//		TextView tv = new TextView(getApplicationContext());
		//		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		//		tv.setLayoutParams(lp);
		//		tv.setGravity(Gravity.CENTER);
		//		tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 80);
		//		tv.setTextColor(0xFFFFFFFF);
		//		tv.setText(text);
		//		frame.addView(tv);
		setContentView(frame);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}    
}
