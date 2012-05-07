package dk.aau.cs.giraf.oasis.app;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class FragmentParent extends Activity {

	static int view;
	static int menuView;
	FragmentTransaction t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {        	
			view = extras.getInt("view");
			menuView = 0;
		} else {
			view = -1;
		}

		setContentView(R.layout.fragments_view);

		createView(view);
	}

	private void createView(int viewID) {
		t = getFragmentManager().beginTransaction();

		MenuListMain menuFrag = new MenuListMain();
		t.add(R.id.fMenuList, menuFrag);
//		t.commit();

		switch (view) {
		case 0:
			MyProfileFrag pFrag = new MyProfileFrag();
			t.add(R.id.fDetails, pFrag);
			t.commit();
			break;
		case 1:
			MyChildrenFrag cFrag = new MyChildrenFrag();
			t.add(R.id.fDetails, cFrag);
			t.commit();
			break;
		case 2:
			MyDepartmentsFrag dFrag = new MyDepartmentsFrag();
			t.add(R.id.fDetails, dFrag);
			t.commit();
			break;
		case 3:
			MyDepChildrenFrag dcFrag = new MyDepChildrenFrag();
			t.add(R.id.fDetails, dcFrag);
			t.commit();
			break;
		default:
			Log.e("fragmentParent", "Error in fragmentManager");
		}
	}
}
