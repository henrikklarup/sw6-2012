package dk.aau.cs.giraf.oasis.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabManagerChild extends Fragment implements OnTabChangeListener {

	public static final String TAB_CHILDPROFILE = "ChildProfile";
	public static final String TAB_CHILDGUARDIANS = "ChildGuardians";
	public static final String TAB_CHILDMEDIA = "ChildMedia";
	public static final String TAB_CHILDAPPS = "ChildApps";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.fourtabsview, null);
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

		updateTab(TAB_CHILDPROFILE, R.id.fourtab_1);

		Button b = (Button) getView().findViewById(R.id.b4Tab);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
	}

	private void setupTabs() {
		mTabHost.setup();
		mTabHost.addTab(newTab(TAB_CHILDPROFILE, R.string.tab_childprofile, R.id.fourtab_1));
		mTabHost.addTab(newTab(TAB_CHILDGUARDIANS, R.string.tab_childguardians, R.id.fourtab_2));
		mTabHost.addTab(newTab(TAB_CHILDMEDIA, R.string.tab_childmedia, R.id.fourtab_3));
		mTabHost.addTab(newTab(TAB_CHILDAPPS, R.string.tab_childapps, R.id.fourtab_4));
	}

	private TabSpec newTab(String tag, int labelId, int tabContentId) {

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
		if (TAB_CHILDPROFILE.equals(tabId)) {
			updateTab(tabId, R.id.fourtab_1);
			mCurrentTab = 0;
			return;
		}
		if (TAB_CHILDGUARDIANS.equals(tabId)) {
			updateTab(tabId, R.id.fourtab_2);
			mCurrentTab = 1;
			return;
		}
		if (TAB_CHILDMEDIA.equals(tabId)) {
			updateTab(tabId, R.id.fourtab_3);
			mCurrentTab = 2;
			return;
		}
		if (TAB_CHILDAPPS.equals(tabId)) {
			updateTab(tabId, R.id.fourtab_4);
			mCurrentTab = 3;
			return;
		}
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = getFragmentManager();

		if (TAB_CHILDPROFILE.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new MyProfileFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_CHILDGUARDIANS.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new MyChildrenFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_CHILDMEDIA.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new MyDepartmentsFrag(), tabId)
			.commit();
			return;
		}
		if (TAB_CHILDAPPS.equals(tabId)) {
			fm.beginTransaction()
			.replace(placeholder, new MyDepChildrenFrag(), tabId)
			.commit();
			return;
		}
	}
}