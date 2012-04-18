package dk.aau.cs.giraf.launcher;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppAdapter extends ArrayAdapter<ApplicationInfo> {

	Context context;
	ArrayList<ApplicationInfo> apps;

	public AppAdapter(Context context, ArrayList<ApplicationInfo> apps) {
		super(context, 0, apps);

		this.context = context;
		this.apps = apps;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ApplicationInfo app = apps.get(position);

		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.apps, parent, false);
		}

		ImageView iv = (ImageView) convertView.findViewById(R.id.app_icon);
		TextView tv = (TextView) convertView.findViewById(R.id.app_text);

		tv.setText(app.title.toString());
		iv.setImageDrawable(app.icon);
		setAppBackgroundColor(convertView, app.bgcolor);

		return convertView;
	}

	private void setAppBackgroundColor(View convertView, int bgColor) {    
		LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.app_bg);
		RectF rectf = new RectF();
		RoundRectShape rect = new RoundRectShape( new float[] {15,15, 15,15, 15,15, 15,15}, rectf, null); // 15,15, 15,15, 15,15, 15,15 // 30,30, 30,30, 30,30, 30,30
		ShapeDrawable bg = new ShapeDrawable(rect);
		bg.getPaint().setColor(bgColor);
		ll.setBackgroundDrawable(bg);
	}
}