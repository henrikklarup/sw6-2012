package dk.aau.cs.giraf.wombat;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// Set content view according to main, which implements two fragments
		setContentView(R.layout.main);
		
		Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	TimerLoader.appID = 928;
        	TimerLoader.guardianID = extras.getLong("currentGuardianID");
        	TimerLoader.profileID = extras.getLong("currentChildId");
        } else {
        	TimerLoader.appID = -1;
        	TimerLoader.guardianID = -1;
        	TimerLoader.profileID = -1;
        }
        
		TimerLoader.load();

	}
}
