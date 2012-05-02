package dk.aau.cs.giraf.launcher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;

import dk.aau.cs.giraf.gui.GButton;
import dk.aau.cs.giraf.launcher.R;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class AuthenticationActivity extends CaptureActivity {
	
	private Intent mHomeIntent;
	private GButton gLoginButton;
	private TextView loginNameView;
	private TextView infoView;
	private Context mContext;
	private Vibrator mVibrator;
	private Profile mPreviousProfile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentication);
		
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


		final ImageView instruct = (ImageView) findViewById(R.id.animation);
		instruct.setBackgroundResource(R.animator.instruct_ani);
		instruct.post(new Runnable(){
			@Override
			public void run(){
				AnimationDrawable anim = (AnimationDrawable) instruct.getBackground();
				anim.start();
			}
		});
		
		gLoginButton = (GButton)this.findViewById(R.id.loginGButton);
		loginNameView = (TextView)this.findViewById(R.id.loginname);
		infoView = (TextView)this.findViewById(R.id.authentication_step1);
		mContext = this;

		gLoginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (!getIntent().hasCategory("dk.aau.cs.giraf.launcher.GIRAF")) {
					mHomeIntent.putExtra(Tools.SKIP, true);
					startActivity(mHomeIntent);
				} else {
					finish();
				}
			}
		});
	}

	private void changeCamerafeedBorderColor(int color) {
		ViewGroup cameraFeedView = (ViewGroup)this.findViewById(R.id.camerafeed);
		RectF rectf = new RectF(10,10,10,10);
		RoundRectShape rect = new RoundRectShape( new float[] {15,15, 15,15, 15,15, 15,15}, rectf, null); // 15,15, 15,15, 15,15, 15,15 // 30,30, 30,30, 30,30, 30,30
		ShapeDrawable bg = new ShapeDrawable(rect);
		bg.getPaint().setColor(color);
		cameraFeedView.setBackgroundDrawable(bg);
	}

	@SuppressWarnings("unused")
	@Override
	public void handleDecode(Result rawResult, Bitmap barcode)
	{
		Helper helper = new Helper(this);
		Profile profile = helper.profilesHelper.authenticateProfile(rawResult.getText());

		if (profile != null) {	
			
			if (mPreviousProfile == null || !profile.toString().equals(mPreviousProfile.toString())) {
				mVibrator.vibrate(400);
			}
			
			mPreviousProfile = profile;
			
			this.changeCamerafeedBorderColor(0xFF3AAA35);
			loginNameView.setText(profile.getFirstname() + " " + profile.getSurname());
			loginNameView.setVisibility(View.VISIBLE);
			gLoginButton.setVisibility(View.VISIBLE);
			infoView.setText(R.string.saadan);
			
			mHomeIntent = new Intent(AuthenticationActivity.this, HomeActivity.class);
			mHomeIntent.putExtra("currentGuardianID", profile.getId());
			Log.i("magnus","guardianID: " + profile.getId());
			
			Tools.saveLogInData(mContext, profile.getId());
		} else {
			this.changeCamerafeedBorderColor(0xFFFF0000);
			gLoginButton.setVisibility(View.INVISIBLE);
			loginNameView.setVisibility(View.INVISIBLE);
			infoView.setText(R.string.authentication_step1);

		}

		this.getHandler().sendEmptyMessageDelayed(R.id.restart_preview, 500);
	}
}
