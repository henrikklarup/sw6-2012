package dk.aau.cs.giraf.oasis.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabManagerMedia extends Fragment implements OnTabChangeListener {

	public static final String TAB_MYMEDIA = "MyMedia";
	public static final String TAB_MYMEDIA1 = "MyMedia1";
	public static final String TAB_MYMEDIA2 = "MyMedia2";
	public static final String TAB_MYMEDIA3 = "MyMedia3";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.tabmediaview, null);
		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
		setupTabs();
		return mRoot;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);

		updateTab(TAB_MYMEDIA, R.id.tabM_1);

		Button b = (Button) getView().findViewById(R.id.bTabMedia);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragParentTab.switchTab(FragParentTab.TABPROFILE);
			}
		});
	}

	private void setupTabs() {
		mTabHost.setup();
		mTabHost.addTab(newTab(TAB_MYMEDIA, R.string.app_name, R.id.tabM_1));
		mTabHost.addTab(newTab(TAB_MYMEDIA1, R.string.app_name, R.id.tabM_2));
		mTabHost.addTab(newTab(TAB_MYMEDIA2, R.string.app_name, R.id.tabM_3));
		mTabHost.addTab(newTab(TAB_MYMEDIA3, R.string.app_name, R.id.tabM_4));
	}

	private TabSpec newTab(String tag, int labelId, int tabContentId) {
		//		Log.d(TAG, "buildTab(): tag=" + tag);

		View indicator = LayoutInflater.from(getActivity()).inflate(
				R.layout.tab, (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
		((TextView) indicator.findViewById(R.id.tvtab)).setText(labelId);

		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		//		Log.d(TAG, "onTabChanged(): tabId=" + tabId);
		if (TAB_MYMEDIA.equals(tabId)) {
			updateTab(tabId, R.id.tabM_1);
			mCurrentTab = 0;
			return;
		}
		if (TAB_MYMEDIA1.equals(tabId)) {
			updateTab(tabId, R.id.tabM_2);
			mCurrentTab = 1;
			return;
		}
		if (TAB_MYMEDIA2.equals(tabId)) {
			updateTab(tabId, R.id.tabM_3);
			mCurrentTab = 2;
			return;
		}
		if (TAB_MYMEDIA3.equals(tabId)) {
			updateTab(tabId, R.id.tabM_4);
			mCurrentTab = 3;
			return;
		}
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = getFragmentManager();

		if (TAB_MYMEDIA.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new MyProfileFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_MYMEDIA1.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new MyChildrenFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_MYMEDIA2.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new MyDepartmentsFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_MYMEDIA3.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new MyDepChildrenFrag(), tabId)
			.commit();
			return;
		}
	}
}