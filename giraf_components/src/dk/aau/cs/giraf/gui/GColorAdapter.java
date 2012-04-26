package dk.aau.cs.giraf.gui;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.content.ClipData;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.LayoutInflater;

public class GColorAdapter extends BaseAdapter {
	
	private Integer[] mAppColors = {0xFFFFDC4A, 0xFFA47AE2, 0xFF4986E7, 0xFFFFDC4A, 0xFFA47AE2, 0xFF4986E7, 0xFFFFDC4A, 0xFFA47AE2, 0xFF4986E7};
	private Context mContext;
	private static LayoutInflater inflater=null;
	
	public GColorAdapter(Context context) {
		super();
		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAppColors.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mAppColors[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int color = (Integer) this.getItem(position);
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.colorpicker, null);
		}

		ImageView iv = (ImageView) vi.findViewById(R.id.color_bg);
		
		GradientDrawable bck = (GradientDrawable) mContext.getResources().getDrawable(R.drawable.colorborder);
		bck.setColor(color);
		
		iv.setBackgroundDrawable(bck);
		
		vi.setOnTouchListener(new ColorDragger(color));
		return vi;
	}
	
	private final class ColorDragger implements OnTouchListener{
	private int color;
	
	public ColorDragger(int c){
		color = c;
		
	}
		
	@Override
	public boolean onTouch(View v, MotionEvent e) {
		boolean result = false;
		switch(e.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
			ClipData data = ClipData.newPlainText("color", Integer.toString(color));
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
			v.startDrag(data, shadowBuilder, v, 0);
			v.setVisibility(View.INVISIBLE);
			result = true;
			break;
		default:
			result = false;
		}
		return result;
	}
	
	}
	

}
