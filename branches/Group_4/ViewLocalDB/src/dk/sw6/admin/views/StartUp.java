package dk.sw6.admin.views;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class StartUp extends Activity {
	
	MediaPlayer introSound;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Fullscreen	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        View main_layout = findViewById(android.R.id.content).getRootView();
        main_layout.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
        
        setContentView(R.layout.logo);
        
        introSound = MediaPlayer.create(StartUp.this, R.raw.dingdingdong);
        introSound.start();
        
        Thread timer = new Thread(){
        	public void run(){
        		try{
        			sleep(7500);
        		} catch (InterruptedException e){
        			e.printStackTrace();
        		} finally{
        			Intent direct = new Intent("dk.sw6.admin.views.MAINACTIVITY");
        			startActivity(direct);
        		}
        	}
        };
        timer.start();
    }

	@Override
	protected void onPause() {
		super.onPause();
		finish();
		introSound.stop();
	}   
}