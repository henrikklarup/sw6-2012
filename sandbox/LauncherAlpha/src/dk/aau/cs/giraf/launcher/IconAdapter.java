package dk.aau.cs.giraf.launcher;

import giraffe.launcher.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IconAdapter extends BaseAdapter {
	Context context;
	ArrayList<ApplicationInfo> appInfo;
	public IconAdapter(Context _context, ArrayList<ApplicationInfo> _appInfo){
		context = _context;
		appInfo = _appInfo;
	}	
	
	//Is called recursively on each element (position is the counter)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(convertView == null){
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = li.inflate(R.layout.grid, null);
			
			ImageView iv = (ImageView) row.findViewById(R.id.icon_image);
			TextView tv = (TextView) row.findViewById(R.id.icon_text);

            tv.setText(appInfo.get(position).title.toString());
            iv.setBackgroundDrawable(appInfo.get(position).icon);
                   
		}
			return row;	
	}

	@Override
	public int getCount() {
		int count = appInfo.size();
		return count;
	}

	@Override
	public Object getItem(int position) {
		if(appInfo == null){
			return null;
		} else{
			return appInfo.get(position);
		}
	}

	@Override
	public long getItemId(int postion) {
		return postion;
	}
}