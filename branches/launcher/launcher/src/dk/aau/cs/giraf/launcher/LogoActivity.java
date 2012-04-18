package dk.aau.cs.giraf.launcher;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import dk.aau.cs.giraf.launcher.R;
import dk.aau.cs.giraf.oasis.lib.Helper;

public class LogoActivity extends Activity {

	protected int _splashTime = 400; 
	private Thread splashTread;
	
	private final String TIMERKEY = "TIMING";
	private final String DATEKEY = "DATE";
	
	// 24 hours in milliseconds
	//private final long mAuthSpan = 86400000;
	
	private final long mAuthSpan = 14400000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.logo);

	    final LogoActivity sPlashScreen = this;
	    
	    Helper helper = new Helper(this);
	    int size = helper.profilesHelper.getProfiles().size();
	    if (size <= 0) {
	    	helper.CreateDummyData();
	    }

	    splashTread = new Thread() {
	        @Override
	        public void run() {
	            try {
	            	synchronized(this) {
	            		wait(_splashTime);
	            	}
	            } catch(InterruptedException e) {}
	            finally {
	            	Date d = new Date();
	            	Intent i;
	            	
	            	Log.i("GIRAF", "" + d.getTime());
	            	Log.i("GIRAF", "" + getLastAuthTime() + mAuthSpan);
	            	
	            	if (d.getTime() > getLastAuthTime() + mAuthSpan) {
	            		i = new Intent(sPlashScreen, AuthenticationActivity.class);
	            	} else {
	            		i = new Intent(sPlashScreen, HomeActivity.class);
	            		
	            		SharedPreferences sp = getSharedPreferences(TIMERKEY, 0);
	            		i.putExtra("currentGuardianID", sp.getLong("currentGuardianID", -1));
	            	}
	            	
	                startActivity(i);
	                stop();
	            }
	        }
	    };
	    splashTread.start();
	}
	
	private Long getLastAuthTime() {
		SharedPreferences sp = getSharedPreferences(TIMERKEY, 0);
		
		return sp.getLong(DATEKEY, 1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	    	synchronized(splashTread){
	    		splashTread.notifyAll();
	    	}
	    }
	    return true;
	}
}