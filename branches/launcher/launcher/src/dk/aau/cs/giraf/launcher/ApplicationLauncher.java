package dk.aau.cs.giraf.launcher;

import dk.aau.cs.giraf.oasis.lib.models.Profile;

import android.view.View;
import android.widget.AdapterView;
import android.app.Activity;
import android.content.Intent;

	class ApplicationLauncher extends Activity implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            final Profile person = (Profile) parent.getItemAtPosition(position);
            final String appComponentName = getIntent().getExtras().getString("appComponentName");
            
            Intent intent = new Intent(appComponentName);
            intent.putExtra("currentProfileId", person.getId());
            
			v.getContext().startActivity(intent);
    }  
}