package dk.aau.cs.giraf.gui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ListView;

public class GList extends ListView {

	private void setStyle() {
		this.setBackgroundResource(R.drawable.grow);
	}
	
	public GList(Context context) {
		
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GList(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

}
