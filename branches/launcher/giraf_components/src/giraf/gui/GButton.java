/**
 * 
 */
package giraf.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.*;

/**
 * @author thomaskobberpanum aka asshole
 *
 */
public class GButton extends Button {

	private void setBackground() {
		this.setBackgroundResource(R.drawable.gbutton);
	}
	
	/**
	 * @param context
	 */
	public GButton(Context context) {
		super(context);
		// TODO Auto-generated constructor
		this.setBackground();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setBackground();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setBackground();
	}

}
