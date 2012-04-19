package dk.aau.cs.giraf.launcher;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
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
	private Context authActivity;
	
	private final String TIMERKEY = "TIMING";
	private final String DATEKEY = "DATE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentication);

		final ImageView instruct = (ImageView) findViewById(R.id.animation);
		instruct.setBackgroundResource(R.animator.instruct_ani);
		instruct.post(new Runnable(){
			@Override
			public void run(){
				AnimationDrawable anim = (AnimationDrawable) instruct.getBackground();
				anim.start();
			}
		});
		
		ResetAuthData();
		
		gLoginButton = (GButton)this.findViewById(R.id.loginGButton);
		loginNameView = (TextView)this.findViewById(R.id.loginname);
		infoView = (TextView)this.findViewById(R.id.authentication_step1);
		authActivity = this;

		final GButton button = (GButton) findViewById(R.id.loginGButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				gLoginButton.setVisibility(View.INVISIBLE);
				loginNameView.setVisibility(View.INVISIBLE);
				infoView.setText(R.string.authentication_step1);
				((AuthenticationActivity) authActivity).changeCamerafeedBorderColor(0xFFDD9639);
				
				if (!getIntent().hasCategory("dk.aau.cs.giraf.launcher.GIRAF")) {
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
			this.changeCamerafeedBorderColor(0xFF3AAA35);
			loginNameView.setText(profile.getFirstname() + " " + profile.getSurname());
			loginNameView.setVisibility(View.VISIBLE);
			gLoginButton.setVisibility(View.VISIBLE);
			infoView.setText(R.string.saadan);
			
			mHomeIntent = new Intent(AuthenticationActivity.this, HomeActivity.class);
			mHomeIntent.putExtra("currentGuardianID", profile.getId());
			
			SaveDate(profile.getId());
		} else {
			this.changeCamerafeedBorderColor(0xFFFF0000);
			gLoginButton.setVisibility(View.INVISIBLE);
			loginNameView.setVisibility(View.INVISIBLE);
			infoView.setText(R.string.authentication_step1);

			//ProgressDialog dialog = ProgressDialog.show(AuthenticationActivity.this, "", "INVALID profile: " + rawResult.getText(), true, true);
		}

		this.getHandler().sendEmptyMessageDelayed(R.id.restart_preview, 500);
	}
	
	/**
	 * Saves the date and time of an authentication, and the user that was authenticated.
	 * @param ID Profile ID of the authenticated user.
	 */
	private void SaveDate(Long ID) {
		SharedPreferences sp = getSharedPreferences(TIMERKEY, 0);
		SharedPreferences.Editor editor = sp.edit();
		
		Date d = new Date();
		editor.putLong(DATEKEY, d.getTime());
		
		editor.putLong("currentGuardianID", ID);
		
		editor.commit();
	}
	
	/**
	 * Logs the current user out of the system.
	 */
	private void ResetAuthData() {
		SharedPreferences sp = getSharedPreferences(TIMERKEY, 0);
		SharedPreferences.Editor editor = sp.edit();
		
		editor.putLong(DATEKEY, 1);
		editor.putLong("currentGuardianID", -1);
		
		editor.commit();
	}
}
