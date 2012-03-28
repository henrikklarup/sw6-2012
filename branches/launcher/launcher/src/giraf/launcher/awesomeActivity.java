package giraf.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class awesomeActivity extends Activity {


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awesome);
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(awesomeActivity.this, logoActivity.class);
            	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
            	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	startActivity(intent);
            }
        });
    }
    
}
