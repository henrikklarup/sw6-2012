package dk.aau.cs.giraf.gui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout.LayoutParams;

public class GTooltip extends Dialog {

	public GTooltip(Context context, View v) {
		super(context);
		this.setStyle();
		this.setPosition(v);
		// TODO Auto-generated constructor stub
	}

	public GTooltip(Context context, int theme, View v) {
		super(context, theme);
		this.setStyle();
		this.setPosition(v);
		// TODO Auto-generated constructor stub
	}

	public GTooltip(Context context, boolean cancelable,
			OnCancelListener cancelListener, View v) {
		super(context, cancelable, cancelListener);
		this.setStyle();
		this.setPosition(v);
		// TODO Auto-generated constructor stub
	}
	
	public void setStyle() {
		this.setContentView(R.layout.gtooltip_layout);
	}
	
	public void setPosition(View v) {
		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		int centerTop = v.getTop() + (v.getHeight() / 2);
		int centerLeft = v.getLeft() + (v.getWidth() / 2);
		params.x = centerLeft - (params.width / 2);
		params.y = centerTop - (params.height / 2);
		this.getWindow().setAttributes(params);
	}
}
