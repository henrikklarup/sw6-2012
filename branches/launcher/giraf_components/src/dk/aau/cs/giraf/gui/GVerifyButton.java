package dk.aau.cs.giraf.gui;

import android.content.Context;
<<<<<<< .mine
import android.graphics.Color;
import android.util.AttributeSet;
=======
import android.graphics.Color;
>>>>>>> .r1248

public class GVerifyButton extends GButton {
	
	private void setStyle() {
		this.setBackgroundResource(R.drawable.gbutton);
		this.setTextColor(Color.parseColor("#9E6435"));
	}

	private void setStyle() {
		this.setBackgroundResource(R.drawable.gbutton);
		this.setTextColor(Color.parseColor("#9E6435"));
	}
	
	/**
	 * @param context
	 */
	public GVerifyButton(Context context) {
		super(context);
		// TODO Auto-generated constructor
		this.setStyle();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GVerifyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setStyle();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GVerifyButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setStyle();
	}

}
