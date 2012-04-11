package dk.aau.cs.giraf.launcher;

import giraffe.launcher.R;
import giraffe.launcher.R.drawable;

import java.util.ArrayList;

import android.content.Context;
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
		LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.app_bg);
		ImageView iv = (ImageView) convertView.findViewById(R.id.app_icon);
		TextView tv = (TextView) convertView.findViewById(R.id.app_text);
		
        tv.setText(app.title.toString());
        iv.setImageDrawable(app.icon);
        

        return convertView;
	}
	
}
