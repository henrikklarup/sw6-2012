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
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends Activity {

	private static Context context;
	ArrayList<dk.aau.cs.giraf.launcher.ApplicationInfo> applications;
	GridView Grid;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		MainActivity.context = getApplicationContext();
		final List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities( mainIntent, 0);

		if(pkgAppsList != null){
			ArrayList<ApplicationInfo> applications = new ArrayList<ApplicationInfo>();
			for(ResolveInfo info : pkgAppsList){
				//giraf app? (dk.aau.cs.giraf)
				if(info.toString().toLowerCase().contains("giraf") && !info.toString().toLowerCase().contains("launcher")){
					ApplicationInfo appInfo = new ApplicationInfo();
					appInfo.title = info.loadLabel(getPackageManager());
					appInfo.icon = info.activityInfo.loadIcon(getPackageManager());

					//this should be checked, so that the intents is right etc.
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
		}
	}
}