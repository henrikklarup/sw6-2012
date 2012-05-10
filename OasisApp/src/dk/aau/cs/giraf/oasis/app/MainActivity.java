package dk.aau.cs.giraf.oasis.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class MainActivity extends Activity implements OnClickListener {

	private Button bMyProfile, bMyChildren, bMyDepartments, bMyDepChildren;
	private Intent direct;
	private long guardianId;
	public Helper helper;
	public static Profile guardian;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        View main_layout = findViewById(android.R.id.content).getRootView();
        main_layout.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
		
		helper = new Helper(this);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {        	   
			guardianId = extras.getLong(("currentGuardianID"));
			guardian = helper.profilesHelper.getProfileById(guardianId);
		} else {
			guardianId = -1;
		}

		setContentView(R.layout.main);

		initializeViews();
	}

	private void initializeViews() {
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
		direct = new Intent(this, FragParentTab.class);
		direct.putExtra("tabView", 0);
		
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