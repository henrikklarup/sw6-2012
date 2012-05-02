package dk.aau.cs.giraf.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import dk.aau.cs.giraf.launcher.R;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;

public class LogoActivity extends Activity {

	protected int _splashTime = 2000; 
	private Thread splashTread;
	private Context mContext;
	private Activity mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.logo);
	    
	    mContext = this.getApplicationContext();
	    
	    
	    
	    Tools.updateGirafApps_DB(mContext);
	    Tools.updateAndroidApps_DB(mContext);
	    
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
	            	Intent i;

	            	if (Tools.sessionExpired(mContext)) {
	            		i = new Intent(mContext, AuthenticationActivity.class);
	            	} else {
	            		i = new Intent(mContext, HomeActivity.class);
	            		
	            		SharedPreferences sp = getSharedPreferences(Tools.TIMERKEY, 0);
	            		i.putExtra(Tools.GUARDIANID, sp.getLong(Tools.GUARDIANID, -1));
	            		i.putExtra(Tools.SKIP, true);
	            	}
	            	
	                startActivity(i);
	                stop();
	                finish();
	            }
	        }
	    };
	    
	    this.setOrientation();
	    
	    splashTread.start();
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
	
	/**
	 * Sets the orientation based on current session information. If session has expired, then switch to portrait, else go to landscape.
	 * This is make the flow more consistent with the screens which the user will be greeted with later. (homescreen = landscape, authentication = portrait)
	 */
	private void setOrientation() {
		if (Tools.sessionExpired(mContext)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}
}