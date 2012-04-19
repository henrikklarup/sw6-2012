package dk.aau.cs.giraf.launcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dk.aau.cs.giraf.gui.GButton;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.view.WindowManager;

public class HomeActivity extends Activity {

	private static Context mContext;
	private static ArrayList<ApplicationInfo> mApplications;
	private GridView mGrid;
	private Profile mCurrentUser; 
	private Helper mHelper;
	private TextView mNameView;
	private LinearLayout mPictureLayout;
	
	private GButton mLogoutButton;
	
	//Hash keys
	public final String BACKGROUNDCOLOR = "backgroundColor";
	public final String APPBACKGROUNDCOLOR = "appBackgroundColor";
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		HomeActivity.mContext = getApplicationContext();
		mHelper = new Helper(mContext);

		mCurrentUser = mHelper.profilesHelper.getProfileById(getIntent().getExtras().getLong("currentGuardianID"));

		mNameView = (TextView)this.findViewById(R.id.nameView);
		mNameView.setText(mCurrentUser.getFirstname() + " " + mCurrentUser.getSurname());
		
		mPictureLayout = (LinearLayout)this.findViewById(R.id.profile_pic);
		
		// Log ud knap:
		/*mLogoutButton = (GButton) findViewById(R.id.logoutGButton);
		mLogoutButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences sp = getSharedPreferences("TIMING", 0);
				SharedPreferences.Editor editor = sp.edit();
				
				editor.putLong("DATE", 1);
				editor.putLong("currentGuardianID", -1);
				
				editor.commit();
				
				Intent i = new Intent(mContext, AuthenticationActivity.class);
				startActivity(i);
			}
		});*/
		
		loadApplications(true);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		this.resizeBar();
	}

	private void resizeBar() {
		GridView homeGridView = (GridView)this.findViewById(R.id.GridViewHome);
		RelativeLayout homebar = (RelativeLayout)this.findViewById(R.id.HomeBarLayout);

		LayoutParams paramsGrid = (RelativeLayout.LayoutParams)homeGridView.getLayoutParams();
		LayoutParams paramsBar = (RelativeLayout.LayoutParams)homebar.getLayoutParams();

		int barHeightLandscape = 100;
		int barHeightPortrait = 200;

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;

		final boolean isLandscape = isLandscape();

		if (isLandscape) {
			paramsBar.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			paramsBar.height = LayoutParams.MATCH_PARENT;
			paramsBar.width = barHeightLandscape;

			paramsGrid.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			paramsGrid.width = screenWidth - barHeightLandscape;
		} else {
			paramsBar.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			paramsBar.height = barHeightPortrait;
			paramsBar.width = LayoutParams.MATCH_PARENT;

			paramsGrid.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			paramsGrid.height = screenHeight - barHeightPortrait;
		}

		homeGridView.setLayoutParams(paramsGrid);
		homebar.setLayoutParams(paramsBar);
		
		RelativeLayout.LayoutParams pictureLayoutParams = (LayoutParams) mPictureLayout.getLayoutParams();
		//LayoutParams pictureLayoutParams;
		if (isLandscape) {
			mNameView.setVisibility(View.INVISIBLE);
			//mPictureLayout.setVisibility(View.INVISIBLE);
			
			//pictureLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			pictureLayoutParams.setMargins(3, 3, 3, 3);
		} else {
			mNameView.setVisibility(View.VISIBLE);
			
			/*
			android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:padding="3dip"
	        android:layout_alignParentLeft="true"
	        android:background="@drawable/gprofile_pic_box"
	        android:layout_marginRight="5dip">
				 */
				
			//pictureLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			pictureLayoutParams.setMargins(3, 3, 5, 3);
		}

		mPictureLayout.setLayoutParams(pictureLayoutParams);
	}

	private void applySizes() {
	}

	private boolean isLandscape() {
		int rotation = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation();
		if ((rotation % 2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	private void loadApplications(boolean isLaunching) {
		if (isLaunching && mApplications != null) {
			return;
		}

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		final List<ResolveInfo> pkgAppsList = mContext.getPackageManager().queryIntentActivities(mainIntent, 0);
		Collections.sort(pkgAppsList, new ResolveInfo.DisplayNameComparator(mContext.getPackageManager()));

		if(pkgAppsList != null){
			ArrayList<ApplicationInfo> applications = new ArrayList<ApplicationInfo>();
			//have to change
			int i = 0;
			
			for(ResolveInfo info : pkgAppsList){
				//Package (dk.aau.cs.giraf)
				if(info.toString().toLowerCase().contains("dk.aau.cs.giraf") && 
						!info.toString().toLowerCase().contains("launcher")) {
					ApplicationInfo appInfo = new ApplicationInfo();

					appInfo.title = info.loadLabel(getPackageManager());
					if(appInfo.title.length() > 6){
						appInfo.title = appInfo.title.subSequence(0, 5) + "...";
					}
					appInfo.icon = info.activityInfo.loadIcon(getPackageManager());
					appInfo.packageName = info.activityInfo.applicationInfo.packageName;
					appInfo.activityName = info.activityInfo.name;
					appInfo.guardian = mCurrentUser.getId();
					appInfo.color = AppColor(i);

					applications.add(appInfo);
					
					//have to change
					i++;
				}
			}
			
			mGrid = (GridView)this.findViewById(R.id.GridViewHome);
			mGrid.setAdapter(new AppAdapter(this,applications));
			mGrid.setOnItemClickListener(new ProfileLauncher());
		}
	}
	
	private int AppColor(int position) {
		int[] c = getResources().getIntArray(R.array.appcolors);
		return c[position];
	}
}