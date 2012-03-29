package sw6.autism.timer.Wombat;

import sw6.autism.timer.Wombat.R;
import sw6.autism.timer.Wombat.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		 getWindow().getDecorView().setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		// Set content view according to main, which implements two fragments
		setContentView(R.layout.main);
		
	}
}
