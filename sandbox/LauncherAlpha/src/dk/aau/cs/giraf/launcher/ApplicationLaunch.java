package dk.aau.cs.giraf.launcher;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

	class ApplicationLaunch extends Activity implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            ApplicationInfo app = (ApplicationInfo) parent.getItemAtPosition(position);
			try {
				Log.d("GIRAF", ""+app.intent.getData());
				startActivity(app.intent);
			}
			catch(NullPointerException e){
				Log.i("GIRAF", " " +e.getMessage());	
			}
    }
        
}