package dk.aau.cs.giraf.oasis.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
   
	Button bMyProfile, bMyChildren, bMyDepartments, bMyDepChildren;
	Intent direct;
	static long guardianId;
   
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
        if (extras != null) {        	
//        	MetaData.setGuardianId(extras.getLong(("currentGuardianID")));
        	guardianId = extras.getLong(("currentGuardianID"));
        } else {
//        	MetaData.setGuardianId(-1);
        	guardianId = -1;
        }
    	
		setContentView(R.layout.main);
		
		initialize();
	}

	private void initialize() {
		bMyProfile = (Button) findViewById(R.id.bMyProfile);
		bMyProfile.setOnClickListener(this);
		bMyChildren = (Button) findViewById(R.id.bMyChildren);
		bMyChildren.setOnClickListener(this);
		bMyDepartments = (Button) findViewById(R.id.bMyDepartments);
		bMyDepartments.setOnClickListener(this);
		bMyDepChildren = (Button) findViewById(R.id.bMyDepChildren);
		bMyDepChildren.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		direct = new Intent("dk.aau.cs.giraf.oasis.app.FRAGMENTPARENT");
		
		switch (v.getId()) {
		case R.id.bMyProfile:
			direct.putExtra("view", 0);
			startActivity(direct);
			break;
		case R.id.bMyChildren:
			direct.putExtra("view", 1);
			startActivity(direct);
			break;
		case R.id.bMyDepartments:
			direct.putExtra("view", 2);
			startActivity(direct);
			break;
		case R.id.bMyDepChildren:
			direct.putExtra("view", 3);
			startActivity(direct);
			break;
		}
	}
}