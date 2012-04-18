package dk.aau.cs.giraf.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import dk.aau.cs.giraf.launcher.R;
import dk.aau.cs.giraf.oasis.lib.Helper;

public class LogoActivity extends Activity {

	protected int _splashTime = 400; 
	private Thread splashTread;

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
	            	Intent i = new Intent(sPlashScreen, AuthenticationActivity.class);
	                startActivity(i);
	                stop();
	            }
	        }
	    };
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
	
//	/** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.logo);
//        
//        final Button button = (Button) findViewById(R.id.button1);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//            	Intent intent = new Intent(logoActivity.this, AuthenticationActivity.class);
//            	
//            	startActivity(intent);
//            }
//        });
//    }

}