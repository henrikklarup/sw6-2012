package dk.raaen.firstgame;

import android.app.Activity;
import android.os.Bundle;

public class FirstGameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Set the draw2d class (which extends View) as the content view */
        Draw2d d = new Draw2d(this);
        setContentView(d);

    }
}