package com.controller.get;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class StartUp extends Activity {
	
	MediaPlayer introSound;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Fullscreen	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.logo);
        
        //Sets the sound file
        introSound = MediaPlayer.create(StartUp.this, R.raw.dingdingdong);
        //introSound.seekTo(40500);
        introSound.start();
        
        Thread timer = new Thread(){
        	public void run(){
        		try{
        			sleep(7500);
        		} catch (InterruptedException e){
        			e.printStackTrace();
        		} finally{
        			Intent direct = new Intent("com.controller.get.TABLELIST");
        			startActivity(direct);
        		}
        	}
        };
        timer.start();
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
		introSound.stop();
	}   
}