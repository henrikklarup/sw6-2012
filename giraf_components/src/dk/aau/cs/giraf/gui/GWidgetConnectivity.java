package dk.aau.cs.giraf.gui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class GWidgetConnectivity extends ImageView {
	
	private void setStyle(int d) {
		this.setBackgroundDrawable(getResources().getDrawable(d));
	}
	
	private void setStyleOnline() {
		this.setStyle(R.drawable.ggiraficon_online);
	}
	
	private void setStyleSyncing() {
		this.setStyle(R.drawable.ggiraficon_syncing);
	}
	
	private void setStyleOffline() {
		this.setStyle(R.drawable.ggiraficon_offline);
	}
	
	private void setInitialStyle() {
		this.setStyleOffline();
	}

	public GWidgetConnectivity(Context context) {
		super(context);
		this.setInitialStyle();
	}

	public GWidgetConnectivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setInitialStyle();
	}

	public GWidgetConnectivity(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setInitialStyle();
	}

}
