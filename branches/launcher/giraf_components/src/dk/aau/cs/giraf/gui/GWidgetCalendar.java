package dk.aau.cs.giraf.gui;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class GWidgetCalendar extends TextView {

	private Timer timer;
	
	private void setStyle(){
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.gcal_icon));
		this.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
		this.setTextSize(35);
		this.setTypeface(null, Typeface.BOLD);
		this.setTextColor(Color.rgb(102, 102, 102));
		this.setPadding(0, 16, 0, 0);
	}
	
	public GWidgetCalendar(Context context) {
		super(context);
		this.setStyle();
		// TODO Auto-generated constructor stub
	}

	public GWidgetCalendar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setStyle();
		// TODO Auto-generated constructor stub
	}

	public GWidgetCalendar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setStyle();
		// TODO Auto-generated constructor stub
	}
	
	public void updateDisplay(){
		int date = Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_MONTH);
		this.setText(Integer.toString(date));
	}

}
