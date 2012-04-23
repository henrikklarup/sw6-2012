package dk.aau.cs.giraf.launcher;

import android.view.View;
import android.widget.AdapterView;
import android.app.Activity;
import android.content.Intent;

	class ProfileLauncher extends Activity implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            ApplicationInfo app = (ApplicationInfo) parent.getItemAtPosition(position);
            
            Intent profileSelectIntent = new Intent(v.getContext(),ProfileSelectActivity.class);
            profileSelectIntent.putExtra(Tools.APP_PACKAGENAME, app.packageName);
            profileSelectIntent.putExtra(Tools.APP_ACTIVITYNAME, app.activityName);
            profileSelectIntent.putExtra(Tools.GUARDIANID, app.guardian);
            profileSelectIntent.putExtra(Tools.CHILDID, app.app);
            
			v.getContext().startActivity(profileSelectIntent);
    }  
}