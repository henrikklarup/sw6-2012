package dk.aau.cs.giraf.gui;

import android.content.Context;
import android.graphics.Color;

public class GVerifyButton extends GButton {
	
	private void setStyle() {
		this.setBackgroundResource(R.drawable.gbutton);
		this.setTextColor(Color.parseColor("#9E6435"));
	}

	public GVerifyButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

}
