package dk.aau.cs.giraf.wombat;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import dk.aau.cs.giraf.TimerLib.Art;
import dk.aau.cs.giraf.TimerLib.Guardian;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		long guardianId;
		long childId;
		int color;	

		Bundle extras = getIntent().getExtras();
        if (extras != null) {        	
        	guardianId = extras.getLong("currentGuardianID");
        	childId = extras.getLong("currentChildID");
        	color = extras.getInt("appBackgroundColor");
        } else {
        	guardianId = -1;
        	childId = -3;
        	color = 0xFFFFBB55;
        }

    	Guardian guard = Guardian.getInstance(childId, guardianId, getApplicationContext());
    	
    	Guardian.backgroundColor = color;

		Art p_done = new Art(R.drawable.p_done,"Færdig");
		Art p_skema = new Art(R.drawable.p_gaa_til_skema,"Gå til skema");
		Art p_taxa = new Art(R.drawable.p_gaa_til_taxa,"Gå til taxa");
		
		guard.ArtList.clear();
		guard.ArtList.add(p_done);
		guard.ArtList.add(p_skema);
		guard.ArtList.add(p_taxa);
    	
		// Set content view according to main, which implements two fragments
		setContentView(R.layout.main);
		
		Drawable d = getResources().getDrawable(R.drawable.background);
		d.setColorFilter(color, PorterDuff.Mode.OVERLAY);
		findViewById(R.id.mainLayout).setBackgroundDrawable(d);
	}
}
