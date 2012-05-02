package dk.aau.cs.giraf.launcher;

import android.view.View;
import android.widget.AdapterView;
import android.app.Activity;
import android.content.Intent;

	class ProfileLauncher extends Activity implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            AppInfo app = (AppInfo) parent.getItemAtPosition(position);
            
            Intent profileSelectIntent = new Intent(v.getContext(),ProfileSelectActivity.class);
            profileSelectIntent.putExtra(Data.APP_PACKAGENAME, app.getaPackage());
            profileSelectIntent.putExtra(Data.APP_ACTIVITYNAME, app.getActivity());
            profileSelectIntent.putExtra(Data.GUARDIANID, app.getGuardianID());
            
			v.getContext().startActivity(profileSelectIntent);
    }  
}