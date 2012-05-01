package dk.aau.cs.giraf.launcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import dk.aau.cs.giraf.gui.GColorAdapter;
import dk.aau.cs.giraf.gui.GTooltip;
import dk.aau.cs.giraf.gui.GWidgetCalendar;
import dk.aau.cs.giraf.gui.GWidgetConnectivity;
import dk.aau.cs.giraf.gui.GWidgetLogout;
import dk.aau.cs.giraf.gui.GWidgetUpdater;
import dk.aau.cs.giraf.gui.GDialog;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.view.WindowManager;


public class HomeActivity extends Activity {

	private static Context mContext;
	private GridView mGrid;
	private Profile mCurrentUser; 
	private Setting<String,String,String> mSettings;
	private Helper mHelper;
	private TextView mNameView;
	private LinearLayout mPictureLayout;
	private ImageView mProfilePictureView;
	private RelativeLayout mHomeBarLayout;
	private int mProfilePictureWidthLandscape;
	private int mProfilePictureHeightLandscape;
	private int mProfilePictureWidthPortrait;
	private int mProfilePictureHeightPortrait;
	private GWidgetUpdater mWidgetTimer;
	private GWidgetCalendar mCalendarWidget;
	private GWidgetConnectivity mConnectivityWidget;
	private GWidgetLogout mLogoutWidget;
	private RelativeLayout.LayoutParams mHomeBarParams;
	private RelativeLayout mHomeDrawer;
	private final int DRAWER_WIDTH = 400;
	private Activity mActivity;
	private boolean mWidgetRunning = false;
	
	private int mLandscapeBarWidth;
	private int mNumberOfApps;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		mActivity = this;
		
		mLandscapeBarWidth = Tools.intToDP(this, 200);

		HomeActivity.mContext = this; //getApplicationContext();
		mHelper = new Helper(mContext);

		mCurrentUser = mHelper.profilesHelper.getProfileById(getIntent().getExtras().getLong(Tools.GUARDIANID));

		mNameView = (TextView)this.findViewById(R.id.nameView);
		mNameView.setText(mCurrentUser.getFirstname() + " " + mCurrentUser.getSurname());
		
		mPictureLayout = (LinearLayout)this.findViewById(R.id.profile_pic);
		mProfilePictureView = (ImageView)this.findViewById(R.id.imageview_profilepic);
		mHomeBarLayout = (RelativeLayout)this.findViewById(R.id.HomeBarLayout);
		
		mProfilePictureWidthLandscape = Tools.intToDP(mContext, 100);
		mProfilePictureHeightLandscape = Tools.intToDP(mContext, 100);
		mProfilePictureWidthPortrait = Tools.intToDP(mContext, 100);
		mProfilePictureHeightPortrait = Tools.intToDP(mContext, 100);

		loadDrawer();
		loadWidgets();
		loadPaintGrid();
		loadApplications();
	}
	
	@Override
	public void onBackPressed() {
		// To stop the device from going back to the logo activity on back press.
		if (!getIntent().getBooleanExtra(Tools.SKIP, false)) {
			super.onBackPressed();
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		this.resizeBar();
		this.rotateGridView();
	}
	
	@Override
    protected void onPause()
    {
        super.onPause();
        mWidgetTimer.sendEmptyMessage(GWidgetUpdater.MSG_STOP);
    }
	
	@Override
    protected void onResume()
    {
        super.onResume();
        mWidgetTimer.sendEmptyMessage(GWidgetUpdater.MSG_START);
    }
	
	private void rotateGridView() {
		final boolean isLandscape = Tools.isLandscape(mContext);
		if (isLandscape) {
			mGrid.setNumColumns((int) Math.ceil(mNumberOfApps / 3));
		}
	}

	private void resizeBar() {
		GridView homeGridView = (GridView)this.findViewById(R.id.GridViewHome);
		RelativeLayout homebar = (RelativeLayout)this.findViewById(R.id.HomeBarLayout);

		LayoutParams paramsGrid = (RelativeLayout.LayoutParams)homeGridView.getLayoutParams();
		RelativeLayout.LayoutParams paramsBar = (RelativeLayout.LayoutParams)homebar.getLayoutParams();

		int barHeightLandscape = Tools.intToDP(mContext, 100);
		int barHeightPortrait = Tools.intToDP(mContext, 200);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;

		final boolean isLandscape = Tools.isLandscape(mContext);

		if (isLandscape) {
			homebar.setBackgroundDrawable(getResources().getDrawable(R.drawable.homebar_back_land));
			paramsBar.height = LayoutParams.MATCH_PARENT;
			paramsBar.width = barHeightLandscape;

			paramsGrid.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			paramsGrid.width = screenWidth - barHeightLandscape;
		} else {
			homebar.setBackgroundDrawable(getResources().getDrawable(R.drawable.homebar_back_port));
			paramsBar.height = barHeightPortrait;
			paramsBar.width = LayoutParams.MATCH_PARENT;

			paramsGrid.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			paramsGrid.height = screenHeight - barHeightPortrait;
		}

		homeGridView.setLayoutParams(paramsGrid);
		homebar.setLayoutParams(paramsBar);
		
		ViewGroup.LayoutParams profilePictureViewParams = mProfilePictureView.getLayoutParams();
		RelativeLayout.LayoutParams connectivityWidgetParams = (LayoutParams) mConnectivityWidget.getLayoutParams();
		RelativeLayout.LayoutParams calendarWidgetParams = (LayoutParams) mCalendarWidget.getLayoutParams();
		RelativeLayout.LayoutParams logoutWidgetParams = (LayoutParams) mLogoutWidget.getLayoutParams();
		
		if (isLandscape) {
			mNameView.setVisibility(View.INVISIBLE);

			profilePictureViewParams.width = Tools.intToDP(mContext, 70);
			profilePictureViewParams.height = Tools.intToDP(mContext, 91);
			mHomeBarLayout.setPadding(Tools.intToDP(mContext, 15), Tools.intToDP(mContext, 15), Tools.intToDP(mContext, 15), Tools.intToDP(mContext, 15));
			
			connectivityWidgetParams.setMargins(0, Tools.intToDP(mContext, 106), 0, 0);
			calendarWidgetParams.setMargins(0, Tools.intToDP(mContext, 15), 0,0);
			calendarWidgetParams.addRule(RelativeLayout.BELOW, mConnectivityWidget.getId());
			calendarWidgetParams.addRule(RelativeLayout.LEFT_OF, 0);
			
			logoutWidgetParams.setMargins(0, Tools.intToDP(mContext, 15), 0, 0);
			logoutWidgetParams.addRule(RelativeLayout.BELOW, mCalendarWidget.getId());
			logoutWidgetParams.addRule(RelativeLayout.LEFT_OF, 0);
		} else {
			
			connectivityWidgetParams.setMargins(0, 0, 0, 0);
			calendarWidgetParams.setMargins(0, 0, Tools.intToDP(mContext, 25),0);
			calendarWidgetParams.addRule(RelativeLayout.BELOW, 0);
			calendarWidgetParams.addRule(RelativeLayout.LEFT_OF, mConnectivityWidget.getId());
			
			logoutWidgetParams.setMargins(0, 0, Tools.intToDP(mContext, 25), 0);
			logoutWidgetParams.addRule(RelativeLayout.BELOW, 0);
			logoutWidgetParams.addRule(RelativeLayout.LEFT_OF, mCalendarWidget.getId());
			
			profilePictureViewParams.width = Tools.intToDP(mContext, 100);
			profilePictureViewParams.height = Tools.intToDP(mContext, 130);
			
			mHomeBarLayout.setPadding(Tools.intToDP(mContext, 15), Tools.intToDP(mContext, 15), Tools.intToDP(mContext, 15), Tools.intToDP(mContext, 15));
			mNameView.setVisibility(View.VISIBLE);
		}
		mProfilePictureView.setLayoutParams(profilePictureViewParams);	
		mConnectivityWidget.setLayoutParams(connectivityWidgetParams);
		mCalendarWidget.setLayoutParams(calendarWidgetParams);
		mLogoutWidget.setLayoutParams(logoutWidgetParams);
	}

	/**
	 * Load the user's applications into the grid.
	 */
	private void loadApplications() {		
		List<App> userApps = Tools.getVisibleGirafApps(mContext, mCurrentUser);

		// Give guardians all giraf apps on the device:
		Tools.attachAllDeviceGirafAppsToUser(mContext);
		userApps = Tools.getVisibleGirafApps(mContext, mCurrentUser);

		if (userApps != null) {
			ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();

			for (App app : userApps) {
				AppInfo appInfo = new AppInfo(app);

				appInfo.load(mContext, mCurrentUser);
				//appInfo.setBgColor(appBgColor(appInfo.getId()));

				appInfos.add(appInfo);
			}

			mGrid = (GridView)this.findViewById(R.id.GridViewHome);
			mGrid.setAdapter(new AppAdapter(this, appInfos));
			mGrid.setOnItemClickListener(new ProfileLauncher());
			mNumberOfApps = appInfos.size();
		} else {
			Log.e("launcher","App list is null");
		}
	}
	
	/**
	 * Load the user's paintgrid in the drawer.
	 */
	private void loadPaintGrid(){
		GridView AppColors = (GridView) findViewById(R.id.appcolors);
		// Removes blue highlight and scroll
		AppColors.setEnabled(false);
		AppColors.setAdapter(new GColorAdapter(this));
	}
	
	/**
	 * Load the drawer and its functionality.
	 */
	private void loadDrawer(){
		View main = findViewById(R.id.HomeWrapperLayout);
		
		main.layout(-600, 0, main.getWidth(), main.getHeight());
		
		// If result = true, the onTouchListner will be run again, if false it will not.
		findViewById(R.id.HomeBarLayout).setOnTouchListener(new View.OnTouchListener() {
			int offset = 0;
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				int margin = 0;
				
				boolean result = true;
				
				switch(e.getActionMasked()){
				case MotionEvent.ACTION_DOWN:
					offset = (int) e.getX();
					result = true;
					break;
				case MotionEvent.ACTION_MOVE:
					mHomeBarParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
					margin = mHomeBarParams.leftMargin + ((int) e.getX() - offset);
					int snaplength = 40;
					
					if (margin < snaplength) {
						margin = 0;
					} else if (margin > (DRAWER_WIDTH - snaplength)) {
						margin = DRAWER_WIDTH;
					} else if (margin > DRAWER_WIDTH) {
						margin = DRAWER_WIDTH;
					}
					
					mHomeBarParams.setMargins(margin, 0, 0, 0);
					v.setLayoutParams(mHomeBarParams);
					
					View v2 = findViewById(R.id.HomeDrawer);
					RelativeLayout.LayoutParams v2Params = (RelativeLayout.LayoutParams) v2.getLayoutParams();
					v2Params.setMargins((margin-800), 0, 0, 0);
					v2.setLayoutParams(v2Params);
					
					result = true;
					
					break;
				case MotionEvent.ACTION_UP:
					
					ViewGroup vg = (ViewGroup) v;
					int numChildren = vg.getChildCount();
					
					for(int i = 0; i < numChildren; i++){
						vg.getChildAt(i).invalidate();
					}
					
					result = false;
					break;
					
				}
				return result;
			}
		});
	}
	
	/**
	 * Load the widgets placed on the drawer.
	 */
	private void loadWidgets(){
		mCalendarWidget = (GWidgetCalendar) findViewById(R.id.calendarwidget);
		mConnectivityWidget = (GWidgetConnectivity) findViewById(R.id.connectivitywidget);
		mLogoutWidget = (GWidgetLogout) findViewById(R.id.logoutwidget);
		
		mWidgetTimer = new GWidgetUpdater();
		mWidgetTimer.addWidget(mCalendarWidget);
		mWidgetTimer.addWidget(mConnectivityWidget);
		
		mHomeDrawer = (RelativeLayout) findViewById(R.id.HomeDrawer);
		
		mLogoutWidget.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				View.OnClickListener task = new View.OnClickListener() {
					public void onClick(View v) {
						startActivity(Tools.logOutIntent(mContext));
						mActivity.finish();
					}
				};
				if(!mWidgetRunning){
					mWidgetRunning = true;
					GDialog g = new GDialog(mContext, R.drawable.large_switch_profile, "Log ud", "Du er på vej til at logge ud", task);
					g.setOwnerActivity(mActivity);
					g.show();
					mWidgetRunning = false;
				};
			}
		});
	}
	
	/**
	 * Finds the background color of a given app, and if no color exists for the app, it is given one.
	 * @param appID ID of the app to find the background color for.
	 * @return The background color of the app.
	 */
	private int appBgColor(Long appID) {
		int[] colors = getResources().getIntArray(R.array.appcolors);
		App launcher = mHelper.appsHelper.getAppByPackageName();
		Setting<String, String, String> launchSetting = launcher.getSettings();
		boolean saveNew = false;
		
		if (launchSetting != null && launchSetting.containsKey(appID)) {
			HashMap<String, String> appSetting = launchSetting.get(appID);
			
			if (appSetting != null && appSetting.containsKey(Tools.COLOR_BG)) {
				return Integer.parseInt(appSetting.get(Tools.COLOR_BG));
			} else {
				saveNew = true;
			}
		} else {
			saveNew = true;
		}
		
		Random rand = new Random();
		int position = rand.nextInt(colors.length);
		
		if (saveNew) {
			launchSetting.addValue(String.valueOf(appID), Tools.COLOR_BG, String.valueOf(colors[position]));
			
			launcher.setSettings(launchSetting);
			mHelper.appsHelper.modifyAppByProfile(launcher, mCurrentUser);
		}
		
		return colors[position];
	}
}