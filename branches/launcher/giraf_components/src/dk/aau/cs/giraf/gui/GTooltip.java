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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GTooltip extends Dialog {
	
	private GTooltip mTip;
	private Context mContext;

	public GTooltip(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		mContext = context;
		this.setStyle();
		mTip = this;
		this.findViewById(R.id.tooltip_hitarea).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTip.cancel();
				
			}
		});
		
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
	
	public void setRightOf(View v, int padding) {
		int newLeft = intToDP(v.getLeft() + v.getWidth() + padding);
		int newTop = intToDP(v.getTop());

		ViewGroup target = (ViewGroup) this.findViewById(R.id.tooltip_wrapper);		
		android.widget.FrameLayout.LayoutParams p = (android.widget.FrameLayout.LayoutParams) target.getLayoutParams();
		p.setMargins(newLeft, newTop, 0, 0);
		target.setLayoutParams(p);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.RIGHT_OF, R.id.tooltip_arrow);
		
		RelativeLayout contentLayout = (RelativeLayout)this.findViewById(R.id.tooltip_content);
		contentLayout.setLayoutParams(params);
	}
	
	private int intToDP(int i) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, mContext.getResources().getDisplayMetrics());
	}

	public void addView(View v){
		ViewGroup target = (ViewGroup) this.findViewById(R.id.tooltip_content);
		target.addView(v);
	}
}
