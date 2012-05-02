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
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.view.WindowManager;


public class HomeActivity extends Activity {

	private static Context mContext;
	
	private Profile mCurrentUser; 
	private Setting<String,String,String> mSettings;
	private Helper mHelper;
	
	private GridView mGrid;
	private TextView mNameView;
	private ImageView mProfilePictureView;
	
	private int mProfilePictureWidthLandscape;
	private int mProfilePictureHeightLandscape;
	private int mProfilePictureWidthPortrait;
	private int mProfilePictureHeightPortrait;
	private int mLandscapeBarWidth;
	private int mNumberOfApps;

	private boolean mWidgetRunning = false;
		
	private GWidgetUpdater mWidgetTimer;
	private GWidgetCalendar mCalendarWidget;
	private GWidgetConnectivity mConnectivityWidget;
	private GWidgetLogout mLogoutWidget;
	
	private RelativeLayout mHomeDrawer;
	private RelativeLayout mHomeBarLayout;
	private LinearLayout mPictureLayout;	
	
	private RelativeLayout.LayoutParams mHomeBarParams;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		mLandscapeBarWidth = Tools.intToDP(this, Data.HOMEBAR_WIDTH_LANDSCAPE);

		HomeActivity.mContext = this;
		mHelper = new Helper(mContext);

		mCurrentUser = mHelper.profilesHelper.getProfileById(getIntent().getExtras().getLong(Data.GUARDIANID));

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

	/**
	 * Catch all clicks on the back key and do nothing, since we dont want people to be able to "log out" with the back key
	 */
	@Override
	public void onBackPressed() {
		
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		this.drawBar();
		this.drawGridView();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mWidgetTimer.sendEmptyMessage(GWidgetUpdater.MSG_STOP);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mWidgetTimer.sendEmptyMessage(GWidgetUpdater.MSG_START);
	}

	/**
	 * Repaint the Grid View
	 */
	private void drawGridView() {
		if (Tools.isLandscape(mContext)) {
			int columns = calculateNumOfColumns();
			int gridWidth = columns * Data.GRID_CELL_WIDTH;
			
			mGrid.setNumColumns(columns);
			LayoutParams gridParams = (LayoutParams) mGrid.getLayoutParams();
			gridParams.width = gridWidth;
			mGrid.setLayoutParams(gridParams);
		} else {
			/*
			 * Future todo: implement portrait mode
			 */
		}
	}
	
	/**
	 * Calculate the current number of columns to use, based on the current number of apps.
	 * @return number of columns to use, based on the current number of apps
	 */
	private int calculateNumOfColumns() {
		if (mNumberOfApps > Data.APPS_PER_PAGE) {
			// We use 3 here becasue there should be three rows if there are more than 9 elements
			return (int) Math.ceil((double)((double)mNumberOfApps) / ((double)Data.APPS_PER_PAGE));
		} else {
			return Data.MAX_COLUMNS_PER_PAGE;
		}
	}

	/**
	 * Repaints the bar
	 */
	private void drawBar() {
		GridView gridView = (GridView)this.findViewById(R.id.GridViewHome);
		RelativeLayout homebarLayout = (RelativeLayout)this.findViewById(R.id.HomeBarLayout);

		LayoutParams paramsGrid = (RelativeLayout.LayoutParams)gridView.getLayoutParams();
		RelativeLayout.LayoutParams homebarLayoutParams = (RelativeLayout.LayoutParams)homebarLayout.getLayoutParams();

		int barHeightLandscape = Tools.intToDP(mContext, 100);
		int barHeightPortrait = Tools.intToDP(mContext, 200);

		/* Get the size of the screen */
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		int screenHeight = size.y;

		if (Tools.isLandscape(mContext)) {
			homebarLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.homebar_back_land));
			homebarLayoutParams.height = LayoutParams.MATCH_PARENT;
			homebarLayoutParams.width = barHeightLandscape;

			paramsGrid.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			paramsGrid.width = screenWidth - barHeightLandscape;
		} else {
			homebarLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.homebar_back_port));
			homebarLayoutParams.height = barHeightPortrait;
			homebarLayoutParams.width = LayoutParams.MATCH_PARENT;

			paramsGrid.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			paramsGrid.height = screenHeight - barHeightPortrait;
		}

		gridView.setLayoutParams(paramsGrid);
		homebarLayout.setLayoutParams(homebarLayoutParams);

		this.drawWidgets();
	}
	
	/**
	 * Repaints the widgets
	 */
	public void drawWidgets() {
		ViewGroup.LayoutParams profilePictureViewParams = mProfilePictureView.getLayoutParams();
		RelativeLayout.LayoutParams connectivityWidgetParams = (LayoutParams) mConnectivityWidget.getLayoutParams();
		RelativeLayout.LayoutParams calendarWidgetParams = (LayoutParams) mCalendarWidget.getLayoutParams();
		RelativeLayout.LayoutParams logoutWidgetParams = (LayoutParams) mLogoutWidget.getLayoutParams();

		if (Tools.isLandscape(mContext)) {
			mNameView.setVisibility(View.INVISIBLE);

			profilePictureViewParams.width = Tools.intToDP(mContext, Data.PROFILEPIC_WIDTH_LANDSCAPE);
			profilePictureViewParams.height = Tools.intToDP(mContext, Data.PROFILEPIC_HEIGHT_LANDSCAPE);
			mHomeBarLayout.setPadding(Tools.intToDP(mContext, Data.HOMEBAR_PADDING_LANDSCAPE), Tools.intToDP(mContext, Data.HOMEBAR_PADDING_LANDSCAPE), Tools.intToDP(mContext, Data.HOMEBAR_PADDING_LANDSCAPE), Tools.intToDP(mContext, Data.HOMEBAR_PADDING_LANDSCAPE));

			connectivityWidgetParams.setMargins(Tools.intToDP(mContext, Data.WIDGET_CONNECTIVITY_MARGIN_LEFT), Tools.intToDP(mContext, Data.WIDGET_CONNECTIVITY_MARGIN_TOP), Tools.intToDP(mContext, Data.WIDGET_CONNECTIVITY_MARGIN_RIGHT), Tools.intToDP(mContext, Data.WIDGET_CONNECTIVITY_MARGIN_BOTTOM));
			calendarWidgetParams.setMargins(Tools.intToDP(mContext, Data.WIDGET_CALENDAR_MARGIN_LEFT), Tools.intToDP(mContext, Data.WIDGET_CALENDAR_MARGIN_TOP), Tools.intToDP(mContext, Data.WIDGET_CALENDAR_MARGIN_RIGHT), Tools.intToDP(mContext, Data.WIDGET_CALENDAR_MARGIN_BOTTOM));
			calendarWidgetParams.addRule(RelativeLayout.BELOW, mConnectivityWidget.getId());
			//calendarWidgetParams.addRule(RelativeLayout.LEFT_OF, 0);

			logoutWidgetParams.setMargins(0, Tools.intToDP(mContext, 390), 0, 0);
			logoutWidgetParams.addRule(RelativeLayout.BELOW, mCalendarWidget.getId());
			//logoutWidgetParams.addRule(RelativeLayout.LEFT_OF, 0);
		} else {
			/**
			 * future todo: implement portrait mode and fix the below code
			 */
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
		List<App> girafAppsList = Tools.getVisibleGirafApps(mContext, mCurrentUser);

		if (girafAppsList != null) {
			ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
			for (App app : girafAppsList) {
				AppInfo appInfo = new AppInfo(app);

				appInfo.load(mContext, mCurrentUser);
				appInfo.setBgColor(appBgColor(appInfo.getId()));
				appInfos.add(appInfo);
			}

			mGrid = (GridView)this.findViewById(R.id.GridViewHome);
			mGrid.setAdapter(new AppAdapter(this, appInfos));
			mGrid.setOnItemClickListener(new ProfileLauncher());
			mNumberOfApps = appInfos.size();
		} else {
			Log.e(Data.ERRORTAG, "App list is null");
		}
	}

	/**
	 * Load the user's paintgrid in the drawer.
	 */
	private void loadPaintGrid(){
		GridView AppColors = (GridView) findViewById(R.id.appcolors);
		// Removes blue highlight and scroll on AppColors grid
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
					} else if (margin > (Data.DRAWER_WIDTH - snaplength)) {
						margin = Data.DRAWER_WIDTH;
					} else if (margin > Data.DRAWER_WIDTH) {
						margin = Data.DRAWER_WIDTH;
					}

					mHomeBarParams.setMargins(margin, 0, 0, 0);
					v.setLayoutParams(mHomeBarParams);

					View v2 = findViewById(R.id.HomeDrawer);
					RelativeLayout.LayoutParams v2Params = (RelativeLayout.LayoutParams) v2.getLayoutParams();
					v2Params.setMargins((margin-800), 0, 0, 0);
					v2.setLayoutParams(v2Params);

					result = true;
					
					// Setting width of the horizontalscrollview
					HorizontalScrollView hScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
					LayoutParams scrollParams = (LayoutParams) hScrollView.getLayoutParams();
					Display display = getWindowManager().getDefaultDisplay();
					Point size = new Point();
					display.getSize(size);
					// removing 50 here to accomodate for "4 columns behaviour"
					scrollParams.width = (size.x - (margin + 100));
					hScrollView.setLayoutParams(scrollParams);

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
		mHomeDrawer = (RelativeLayout) findViewById(R.id.HomeDrawer);

		mWidgetTimer = new GWidgetUpdater();
		mWidgetTimer.addWidget(mCalendarWidget);
		mWidgetTimer.addWidget(mConnectivityWidget);


		mLogoutWidget.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				View.OnClickListener task = new View.OnClickListener() {
					public void onClick(View v) {
						startActivity(Tools.logOutIntent(mContext));
						((Activity) mContext).finish();
					}
				};
				
				if(!mWidgetRunning){
					mWidgetRunning = true;
					GDialog g = new GDialog(mContext, R.drawable.large_switch_profile, "Log ud", "Du er på vej til at logge ud", task);
					g.setOwnerActivity((Activity)mContext);
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
		App launcherApp = mHelper.appsHelper.getAppByPackageNameAndProfileId(mCurrentUser.getId());
		Setting<String, String, String> launchSetting = launcherApp.getSettings();
		
		if (launchSetting != null && launchSetting.containsKey(String.valueOf(appID))) {
			HashMap<String, String> appSetting = launchSetting.get(String.valueOf(appID));
			
			if (appSetting != null && appSetting.containsKey(Data.COLOR_BG)) {
				return Integer.parseInt(appSetting.get(Data.COLOR_BG));
			}
		}

		int position = (new Random()).nextInt(colors.length);

		if (launchSetting == null) {
			launchSetting = new Setting<String, String, String>();
		}
		
		if (!launchSetting.containsKey(String.valueOf(appID))) {
			launchSetting.addValue(String.valueOf(appID), Data.COLOR_BG, String.valueOf(colors[position]));
		} else if (!launchSetting.get(String.valueOf(appID)).containsKey(Data.COLOR_BG)) {
			launchSetting.get(String.valueOf(appID)).put(Data.COLOR_BG, String.valueOf(colors[position]));
		}

		launcherApp.setSettings(launchSetting);
		mHelper.appsHelper.modifyAppByProfile(launcherApp, mCurrentUser);
		
		return colors[position];
	}
}