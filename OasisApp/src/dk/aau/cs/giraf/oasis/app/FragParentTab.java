package dk.aau.cs.giraf.oasis.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class FragParentTab extends Activity {

	public static int view;
	private int tabView;
	public final static int TABPROFILE = 0;
	public final static int TABAPP = 1;
	public final static int TABMEDIA = 2;
	static FragmentManager t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		View main_layout = findViewById(android.R.id.content).getRootView();
		main_layout.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			view = extras.getInt("view");
			tabView = extras.getInt("tabView");
		} else {
			view = -1;
			tabView = -1;
		}

		setContentView(R.layout.fragments_view);

		t = getFragmentManager();
		t.beginTransaction().add(R.id.fDetails, new TabManagerProfile()).commit();

		/*switch(tabView) {
		case TABPROFILE:
			t.beginTransaction().add(R.id.fDetails, new TabManagerProfile()).commit();
			break;
		case TABMEDIA:
			t.beginTransaction().add(R.id.fDetails, new TabManagerMedia()).commit();
			break;
		}*/
	}

	public static void switchTab(int tabViewId) {

		switch(tabViewId) {
		case TABPROFILE:
			t.beginTransaction().replace(R.id.fDetails, new TabManagerProfile()).commit();
			break;
		case TABMEDIA:
			t.beginTransaction().replace(R.id.fDetails, new TabManagerMedia()).commit();
			break;
		case TABAPP:
			t.beginTransaction().replace(R.id.fDetails, new TabManagerApp()).commit();
			break;
		}
	}
}
