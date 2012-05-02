package dk.aau.cs.giraf.launcher;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppAdapter extends ArrayAdapter<AppInfo> {

	Context mContext;
	ArrayList<AppInfo> mApps;

	/**
	 * Constructs a new adapter to handle the presentation of apps in the launcher.
	 * @param context Context the adapter is created in.
	 * @param apps The apps to show.
	 */
	public AppAdapter(Context context, ArrayList<AppInfo> apps) {
		super(context, 0, apps);

		this.mContext = context;
		this.mApps = apps;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final AppInfo appInfo = mApps.get(position);

		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.apps, parent, false);
		}

		ImageView appIconImageView = (ImageView) convertView.findViewById(R.id.app_icon);
		TextView appTextTextView = (TextView) convertView.findViewById(R.id.app_text);
		
		appTextTextView.setText(appInfo.getShortenedName());
		appIconImageView.setImageDrawable(appInfo.getIconImage());
		setAppBackground(convertView, appInfo.getBgColor());

		convertView.setOnDragListener(new GAppDragger());
		
		return convertView;
	}

	/**
	 * Sets the background of the app.
	 * @param wrapperView The view the app is located inside.
	 * @param backgroundColor The color to use for the background.
	 */
	public static void setAppBackground(View wrapperView, int backgroundColor) {    
		LinearLayout convertViewLayout = (LinearLayout) wrapperView.findViewById(R.id.app_bg);
		
		RoundRectShape roundRect = new RoundRectShape( new float[] {15,15, 15,15, 15,15, 15,15}, new RectF(), null);
		ShapeDrawable shapeDrawable = new ShapeDrawable(roundRect);
		
		shapeDrawable.getPaint().setColor(backgroundColor);
		convertViewLayout.setBackgroundDrawable(shapeDrawable);
	}
}