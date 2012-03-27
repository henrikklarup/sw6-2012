/**
 * 
 */
package giraf.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @author thomaskobberpanum
 *
 */
public class GButton extends Button {

	/**
	 * @param context
	 */
	public GButton(Context context) {
		super(context);
		// TODO Auto-generated constructor
		this.setBackgroundResource(R.drawable.gbutton);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

}
