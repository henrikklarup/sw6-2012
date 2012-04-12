package dk.aau.cs.giraf.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;


public class GIconButton extends GButton {

	protected Drawable icon = null;

	//Example of icon reference: this.getResources().getDrawable(R.drawable.ok_icon)
	
	private void setStyle() {
		this.setBackgroundResource(R.drawable.gbutton);
		this.setTextColor(Color.parseColor("#9E6435"));
		this.setCompoundDrawablePadding(20);

	}
	
	private Drawable resizeIcon(Drawable icon, int height){
		Drawable result = (BitmapDrawable) icon;
		Bitmap tempIcon = ((BitmapDrawable)icon).getBitmap();
		
		final int oldWidth = tempIcon.getWidth();
		final int oldHeight = tempIcon.getHeight();
		Log.i("magnus",height + " height");
		Log.i("magnus",tempIcon.getHeight() + " tempIcon.getHeight()");
		
		float scale = ((float) height) / ((float)tempIcon.getHeight());		
		Log.i("magnus",scale + " =scale");
		
		final Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		tempIcon = Bitmap.createBitmap(tempIcon, 0, 0, oldWidth, oldHeight, matrix, true);
		
		result = new BitmapDrawable(tempIcon);
		
		return result;
	}
	
	@Override
	 public void onWindowFocusChanged(boolean hasFocus) {
	  super.onWindowFocusChanged(hasFocus);
	  int trueHeight = this.getHeight() - (this.getPaddingTop()+this.getPaddingBottom());
	  this.setCompoundDrawablesWithIntrinsicBounds(resizeIcon(icon, trueHeight), null, null, null);
	  Log.i("magnus",this.getHeight() + " getHeight()");
	  Log.i("magnus",this.getTextSize() + " getTextSize()");
	  
	 }
	
	public GIconButton(Context context) {
		super(context);
		this.setStyle();
		// TODO Auto-generated constructor stub
	}

	public GIconButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setStyle();
		// TODO Auto-generated constructor stub
	}

	public GIconButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setStyle();
		// TODO Auto-generated constructor stub
	}
	
	protected void setIcon(Drawable d){
		this.icon = d;
	}
	
}
