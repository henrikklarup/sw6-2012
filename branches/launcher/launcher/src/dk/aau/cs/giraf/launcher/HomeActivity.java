package dk.aau.cs.giraf.launcher;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.view.WindowManager;

public class HomeActivity extends Activity {

	private static Context context;
	private static ArrayList<ApplicationInfo> mApplications;
	GridView Grid;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
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
		
		if (isLandscape()) {
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
		
		HomeActivity.context = getApplicationContext();
		final List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities( mainIntent, 0);

		if(pkgAppsList != null){
			ArrayList<ApplicationInfo> applications = new ArrayList<ApplicationInfo>();
			for(ResolveInfo info : pkgAppsList){
				//Package (dk.aau.cs.giraf)
				if(info.toString().toLowerCase().contains("dk.aau.cs.giraf") && !info.toString().toLowerCase().contains("launcher")){
					ApplicationInfo appInfo = new ApplicationInfo();
					
					appInfo.title = info.loadLabel(getPackageManager());
					appInfo.icon = info.activityInfo.loadIcon(getPackageManager());
					appInfo.packageName = info.activityInfo.applicationInfo.packageName;
					appInfo.activityName = info.activityInfo.name;
					
					applications.add(appInfo);
				}
			}

			Grid = (GridView)this.findViewById(R.id.GridViewHome);
			Grid.setAdapter(new AppAdapter(this,applications));
			Grid.setOnItemClickListener(new ProfileLauncher());
		}
	}
}