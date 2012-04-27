package dk.aau.cs.giraf.gui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;

public class GTooltip extends Dialog {

	public GTooltip(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GTooltip(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public GTooltip(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}
	
	public void setOwner(Activity activity) {
		setOwnerActivity(activity);
	}
	
	public void setStyle(View view, LayoutParams params) {
		this.addContentView(view, params);
	}
}
