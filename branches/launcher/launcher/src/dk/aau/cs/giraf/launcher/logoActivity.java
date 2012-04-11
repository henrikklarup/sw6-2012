package dk.aau.cs.giraf.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import dk.aau.cs.giraf.launcher.R;

public class logoActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        
    	
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(logoActivity.this, AuthenticationActivity.class);
            	
            	startActivity(intent);
            }
        });
    }
    
    
    /*@Override
    public void onResume() {
    	super.onResume();
    	
    	try {Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    	Intent intent = new Intent(logoActivity.this, AuthenticationActivity.class);
    	startActivity(intent);
    }*/
	
}
