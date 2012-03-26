package giraffe.launcher;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class IconAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<giraffe.launcher.ApplicationInfo> applications = new ArrayList<giraffe.launcher.ApplicationInfo>();
	
	public IconAdapter(Context c, ArrayList<ApplicationInfo> apps) {
	        mContext = c;
	        applications = apps;
	    }	
	
	@Override
	public int getCount() {
		return applications.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		
		if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

		imageView.setImageDrawable(applications.get(position).icon);
        return imageView;
        
        
	}

}
