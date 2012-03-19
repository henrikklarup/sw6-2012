package dk.sw6.admin.views;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MainActivity extends FragmentActivity {
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        View main_layout = super.findViewById(android.R.id.content).getRootView();
        main_layout.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);

		super.setContentView(R.layout.main);
		this.initialisePaging();
	}

	/**
	 * Initialize the fragments to be paged
	 */
	private void initialisePaging() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, ViewApps.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewDepartments.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewMedia.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewProfiles.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewSettings.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewStats.class.getName()));
		this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
		pager.setAdapter(this.mPagerAdapter);
	}
}