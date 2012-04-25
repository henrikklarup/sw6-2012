package dk.aau.cs.giraf.wombat;

import dk.aau.cs.giraf.TimerLib.Guardian;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		long guardianId;
		long childId;
		
		Bundle extras = getIntent().getExtras();
        if (extras != null) {        	
        	guardianId = extras.getLong("currentGuardianID");
        	childId = extras.getLong("currentChildId");
        } else {
        	guardianId = -1;
        	childId = -1;
        }

    	Guardian.getInstance(childId, guardianId, getApplicationContext());
    	
		// Set content view according to main, which implements two fragments
		setContentView(R.layout.main);
	}
}
