package giraffe.launcher;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ArrayList<giraffe.launcher.ApplicationInfo> applications;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //loadApplications
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        
        PackageManager manager = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        
        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
        
        if (apps != null) {
        	final int count = apps.size();
        	
        	ArrayList<giraffe.launcher.ApplicationInfo> applications = new ArrayList<giraffe.launcher.ApplicationInfo>(count);
        	
        	for (ResolveInfo info : apps) {
        		giraffe.launcher.ApplicationInfo appInfo = new giraffe.launcher.ApplicationInfo();
        		appInfo.title = info.loadLabel(manager);
        		appInfo.icon = info.activityInfo.loadIcon(manager);
        		appInfo.setActivity(new ComponentName(
                        info.activityInfo.applicationInfo.packageName,
                        info.activityInfo.name),
                        Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        		
        		 applications.add(appInfo);
        		 
        		 Log.i("appInfo", "appInfo.title" + appInfo.title);
        		 Log.i("applications", "applications" + applications);
        	}
        	IconAdapter iconAdapter = new IconAdapter(this, applications);
            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(iconAdapter);
            iconAdapter.getView(position, convertView, parent);
            
            
            gridview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}