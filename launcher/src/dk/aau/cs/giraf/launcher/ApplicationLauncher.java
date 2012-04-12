package dk.aau.cs.giraf.launcher;

import java.util.List;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.app.Activity;

	class ApplicationLauncher extends Activity implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            ApplicationInfo app = (ApplicationInfo) parent.getItemAtPosition(position);
            
            Helper helper = new Helper(this);
            List<Profile> profiles = helper.profilesHelper.getProfiles();
            app.intent.putExtra("currentProfileId", profiles.get(0).getId());
            
            Log.i("GIRAF", "" + app.intent.getExtras().get("currentProfileId"));
			v.getContext().startActivity(app.intent);
    }  
}