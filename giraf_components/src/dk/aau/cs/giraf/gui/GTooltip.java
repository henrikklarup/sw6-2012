package dk.aau.cs.giraf.gui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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
		
	}
}
