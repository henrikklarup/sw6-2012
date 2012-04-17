package dk.aau.cs.giraf.wombat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class DrawHourglass extends View {
	private Guardian guard = Guardian.getInstance();
	SubProfile sp = guard.getSubProfile();
	int background = sp.bgcolor;
	int frame = sp.frameColor;
	int timeleft = sp.timeLeftColor;
	int timeleft2 = sp.timeLeftColor;
	int timespent = sp.timeSpentColor;
	boolean init = true;

	double totalTime = (sp.totalTime - 1) * 1000;
	double endTime = SystemClock.currentThreadTimeMillis() + totalTime;
	Paint paint = new Paint();
	Rect rtop, rbot, rleft, rright;
	ColorDrawable col;

	int width, height;
	int top, left;
	int frameHeight, indent, glassLeft, glassRight;

	public DrawHourglass(Context context) {
		super(context);
		if(sp.gradient){
			timeleft2 = timespent;
			timespent = background;
		}
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);

		if(init){
			width = 300;
			height = 500;
			frameHeight = height / 20;
			indent = (int) (frameHeight * 1.5);
			init = false;
		}
		/* Fill the canvas with the background color */
		paint.setColor(background);
		c.drawPaint(paint);

		/* Draw the frame of the hourglass */
		paint.setAntiAlias(true);
		paint.setColor(frame);
		
		left = (c.getWidth() - width) / 2;
		top = (c.getHeight() - height) / 2;

		rtop = new Rect(left, top, left + width, top + frameHeight);
		rbot = new Rect(left, top + height - frameHeight, left + width, top + height);
		rleft = new Rect(left + indent, top, left - frameHeight + indent, top + height);
		rright = new Rect(left + width - indent, top, left + width - indent + frameHeight, top + height);
		
		c.drawRect(rtop, paint);
		c.drawRect(rbot, paint);
		c.drawRect(rleft, paint);
		c.drawRect(rright, paint);
		
		/* Draw the "glass" */
		Path p = new Path();
		p.moveTo(left + indent + frameHeight, top + frameHeight);
		p.lineTo(left + width - indent - frameHeight, top + frameHeight);
		p.lineTo(left + width - indent - frameHeight, top + 200);
		p.lineTo(left + width - indent - frameHeight - 50, top + 225);
		p.lineTo(left + indent + frameHeight + 50, top + 225);
		p.lineTo(left + indent + frameHeight, top + 200);
		
//		p.close();
		c.drawPath(p, paint);
		

		/* Draw the backgroundcolor inside the frame */
//		paint.setColor(background);
//		rtop.set(r.left + 2, r.top + 2, r.right - 2, r.bottom - 2);
//		c.drawRect(r, paint);

//		/* Draw the timespent color (on the right) on top of the timeleft */
//		paint.setColor(timespent);
//		r.set(left + 3, top + 3, left + width - 3, top + height - 3);
//		c.drawRect(r, paint);
//
//		if (endTime >= SystemClock.currentThreadTimeMillis()) {
//			paint.setStyle(Paint.Style.FILL);
//			double percent = (endTime - SystemClock.currentThreadTimeMillis())
//					/ totalTime;
//
//			paint.setColor(timeleft2);
//			r.set((left + 3), top + 3, left + 3 + (int) (width * percent), top + height - 3);
//			c.drawRect(r, paint);
//			
//			/* Draw the timeleft color (on the left side) */
//			col = new ColorDrawable(timeleft);
//			col.setAlpha((int) (255*percent));
//			paint.setColor(col.getColor());
//			r.set((left + 3), top + 3, left + 3 + (int) (width * percent), top + height - 3);
//			c.drawRect(r, paint);
//			
//			/*************** IMPORTANT ***************/
//			/* Recalls Draw! */
//			invalidate();
//		} else {
//			paint.setColor(timespent);
//			r.set(left + 3, top + 3, left + width - 3, top + height - 3);
//			rb.set(left + 3, top + 3, left + width - 3, top + height -3);
//			c.drawRect(r, paint);
//			c.drawRect(rb, paint);
		}
	}

