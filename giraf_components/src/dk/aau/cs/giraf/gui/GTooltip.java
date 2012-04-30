package dk.aau.cs.giraf.gui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout.LayoutParams;

public class GTooltip extends Dialog {
	
	private Context mContext;

	public GTooltip(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		mContext = context;
		this.setStyle();
		// TODO Auto-generated constructor stub
	}

	private GTooltip(Context context, int theme) {
		super(context, theme);
		mContext = context;
		this.setStyle();
		// TODO Auto-generated constructor stub
	}

	private GTooltip(Context context, boolean cancelable,
			OnCancelListener cancelListener, View v) {
		super(context, cancelable, cancelListener);
		mContext = context;
		this.setStyle();
		// TODO Auto-generated constructor stub
	}
	
	public void setStyle() {
		this.setContentView(R.layout.gtooltip_layout);
	}
	
	public void setLeftOf(View v) {
		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		int centerTop = v.getTop() + (v.getHeight() / 2);
		int centerLeft = v.getLeft() + (v.getWidth() / 2);
		
		
		int newLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (centerLeft - (params.width / 2)), mContext.getResources().getDisplayMetrics());
		int newTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (centerTop - (params.height / 2)), mContext.getResources().getDisplayMetrics());
		
		Log.i("magnus", "x : " + (centerLeft - (params.width / 2)));
		Log.i("magnus", "y : " + (centerTop - (params.height / 2)));
		
		ViewGroup target = (ViewGroup) this.findViewById(R.id.tooltip_content);
		
		android.widget.FrameLayout.LayoutParams p = (android.widget.FrameLayout.LayoutParams) target.getLayoutParams();
		p.setMargins(newLeft, newTop, 0, 0);
		target.setLayoutParams(p);
	}

	public void addView(View v){
		ViewGroup target = (ViewGroup) this.findViewById(R.id.tooltip_content);
		target.addView(v);
	}
}
