package giraffe.launcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        PackageManager manager = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        
        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
        
        if (apps != null) {
        	final int count = apps.size();
        	
        	ArrayList<ApplicationInfo> applications = new ArrayList<ApplicationInfo>(count);
        	
        	for (ResolveInfo info : apps) {
        		ApplicationInfo app = new ApplicationInfo();
        		
        		app.name = info.loadLabel(manager).toString(); /// TODO FIXME
        		//app.icon = info.activityInfo.loadIcon
        	}
        }
    }
}