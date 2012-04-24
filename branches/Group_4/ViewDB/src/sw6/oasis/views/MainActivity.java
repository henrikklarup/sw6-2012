package sw6.oasis.views;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import dk.aau.cs.giraf.oasis.lib.Helper;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	
	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, MainActivity.TabInfo>();
	private MyPagerAdapter mPagerAdapter;
	
	private class TabInfo {
		private String tag;
		private Class<?> _class;
		private Bundle args;
		private Fragment fragment;
		TabInfo(String tag, Class<?> _class, Bundle args) {
			this.tag = tag;
			this._class = _class;
			this.args = args;
		}
	}

	class TabFactory implements TabContentFactory {
		private final Context mContext;

		public TabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumHeight(0);
			v.setMinimumWidth(0);
			return v;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        View main_layout = super.findViewById(android.R.id.content).getRootView();
        main_layout.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);

		super.setContentView(R.layout.main);
		
		Helper helper = new Helper(this);
		helper.CreateDummyData();
		
		this.initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		
		this.initialisePaging();
	}
	
	protected void onSavedInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	/**
	 * Initialize the fragments to be paged
	 */
	private void initialisePaging() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, ViewAuthUsers.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewProfiles.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewHasGuardian.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewDepartments.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewHasSubDepartment.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewHasDepartment.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewApps.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewListOfApps.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewMedia.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewHasLink.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewMediaProfileAccess.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewMediaDepartmentAccess.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewTags.class.getName()));
		fragments.add(Fragment.instantiate(this, ViewHasTag.class.getName()));
		
		this.mPagerAdapter = new MyPagerAdapter(super.getSupportFragmentManager(), fragments);
		
		this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
		this.mViewPager.setAdapter(this.mPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);
	}
	
	private void initialiseTabHost(Bundle args) {
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab1").setIndicator("Tab01"), (tabInfo = new TabInfo("Tab1",
						ViewAuthUsers.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab2").setIndicator("Tab02"), (tabInfo = new TabInfo("Tab2",
						ViewProfiles.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab3").setIndicator("Tab03"), (tabInfo = new TabInfo("Tab3",
						ViewHasGuardian.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab4").setIndicator("Tab04"), (tabInfo = new TabInfo("Tab4",
						ViewDepartments.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab5").setIndicator("Tab05"), (tabInfo = new TabInfo("Tab5",
						ViewHasDepartment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab6").setIndicator("Tab06"), (tabInfo = new TabInfo("Tab6",
						ViewHasSubDepartment.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab7").setIndicator("Tab07"), (tabInfo = new TabInfo("Tab7",
						ViewApps.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab8").setIndicator("Tab08"), (tabInfo = new TabInfo("Tab8",
						ViewListOfApps.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab9").setIndicator("Tab09"), (tabInfo = new TabInfo("Tab9",
						ViewMedia.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab10").setIndicator("Tab10"), (tabInfo = new TabInfo("Tab10",
						ViewHasLink.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab11").setIndicator("Tab11"), (tabInfo = new TabInfo("Tab11",
						ViewMediaProfileAccess.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab12").setIndicator("Tab12"), (tabInfo = new TabInfo("Tab12",
						ViewMediaDepartmentAccess.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab13").setIndicator("Tab13"), (tabInfo = new TabInfo("Tab13",
						ViewTags.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		MainActivity.addTab(this, this.mTabHost, 
				this.mTabHost.newTabSpec("Tab14").setIndicator("Tab14"), (tabInfo = new TabInfo("Tab14",
						ViewHasTag.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		mTabHost.setOnTabChangedListener(this);
	}

	private static void addTab(MainActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	public void onTabChanged(String tag) {
		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);
	}
	
	@Override
	public void onPageSelected(int position) {
		this.mTabHost.setCurrentTab(position);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}
}