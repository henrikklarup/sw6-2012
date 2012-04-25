package dk.aau.cs.giraf.gui;

import java.util.Random;

import dk.aau.cs.giraf.oasis.lib.Helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class GWidgetConnectivity extends ImageView implements IGWidget {
	
	private Helper helper;
	
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
		helper = new Helper(context);
	}

	public GWidgetConnectivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setInitialStyle();
		helper = new Helper(context);
	}

	public GWidgetConnectivity(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setInitialStyle();
		helper = new Helper(context);
	}

	@Override
	public void updateDisplay() {
		/*/// TODO implement library "ping" methods... talk to magnus
		switch ((new Random()).nextInt(3)) {
		case 0:
			setStyleOffline();
			break;
		case 1:
			setStyleOnline();
			break;
		case 2:
			setStyleSyncing();
			break;
		}*/
		
	}

}
