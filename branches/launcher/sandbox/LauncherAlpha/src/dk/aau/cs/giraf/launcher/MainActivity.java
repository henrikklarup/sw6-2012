package dk.aau.cs.giraf.launcher;

import giraffe.launcher.R;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class MainActivity extends Activity {

	private static Context context;
	private static ArrayList<ApplicationInfo> mApplications;
	GridView Grid;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		loadApplications(true);
		
	}
	private void loadApplications(boolean isLaunching) {
		if (isLaunching && mApplications != null) {
			return;
		}
		
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		MainActivity.context = getApplicationContext();
		final List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities( mainIntent, 0);

		if(pkgAppsList != null){
			ArrayList<ApplicationInfo> applications = new ArrayList<ApplicationInfo>();
			for(ResolveInfo info : pkgAppsList){
				//Package (dk.aau.cs.giraf)
				if(!info.toString().toLowerCase().contains("dk.aau.cs.giraf") && !info.toString().toLowerCase().contains("launcher")){
					ApplicationInfo appInfo = new ApplicationInfo();
					appInfo.title = info.loadLabel(getPackageManager());
					appInfo.icon = info.activityInfo.loadIcon(getPackageManager());

					appInfo.setActivity(new ComponentName(
	                        info.activityInfo.applicationInfo.packageName,
	                        info.activityInfo.name),
	                        Intent.FLAG_ACTIVITY_NEW_TASK
	                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					applications.add(appInfo);
				}
			}

			Grid = (GridView)findViewById(R.id.GridView01);
			Grid.setAdapter(new IconAdapter(this,applications));
			Grid.setOnItemClickListener(new ApplicationLaunch());
		}
	}
}