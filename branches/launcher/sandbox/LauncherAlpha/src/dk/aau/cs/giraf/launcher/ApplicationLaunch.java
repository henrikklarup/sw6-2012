package dk.aau.cs.giraf.launcher;

import android.view.View;
import android.widget.AdapterView;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

	class ApplicationLaunch extends Activity implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            ApplicationInfo app = (ApplicationInfo) parent.getItemAtPosition(position);
            
			v.getContext().startActivity(app.intent);
    }
        
}