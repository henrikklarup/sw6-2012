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

	Context context;
	ArrayList<AppInfo> apps;

	/**
	 * Constructs a new adapter to handle the presentation of apps in the launcher.
	 * @param context Context the adapter is created in.
	 * @param apps The apps to show.
	 */
	public AppAdapter(Context context, ArrayList<AppInfo> apps) {
		super(context, 0, apps);

		this.context = context;
		this.apps = apps;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final AppInfo app = apps.get(position);

		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.apps, parent, false);
		}

		ImageView iv = (ImageView) convertView.findViewById(R.id.app_icon);
		TextView tv = (TextView) convertView.findViewById(R.id.app_text);
		
		tv.setText(app.getShortenedName());
		iv.setImageDrawable(app.getIconImage());
		setAppBackground(convertView, app.getBgColor());

		convertView.setOnDragListener(new GAppDragger());
		
		return convertView;
	}

	/**
	 * Sets the background of the app.
	 * @param convertView The view the app is located inside.
	 * @param color The color to use for the background.
	 */
	public static void setAppBackground(View convertView, int color) {    
		LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.app_bg);
		
		RoundRectShape rect = new RoundRectShape( new float[] {15,15, 15,15, 15,15, 15,15}, new RectF(), null);
		ShapeDrawable bg = new ShapeDrawable(rect);
		
		bg.getPaint().setColor(color);
		ll.setBackgroundDrawable(bg);
	}
}