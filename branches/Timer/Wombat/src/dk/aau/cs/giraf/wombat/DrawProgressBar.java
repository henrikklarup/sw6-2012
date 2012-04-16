package dk.aau.cs.giraf.wombat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class DrawProgressBar extends View {
	private Guardian guard = Guardian.getInstance();
	SubProfile sp = guard.getSubProfile();
	int background = sp._bgcolor;
	int frame = sp._frameColor;
	int timeleft = sp._timeLeftColor;
	int timeleft2 = sp._timeLeftColor;
	int timespent = sp._timeSpentColor;
	boolean init = true;

	double totalTime = (sp._totalTime - 1) * 1000;
	double endTime = SystemClock.currentThreadTimeMillis() + totalTime;
	Paint paint = new Paint();
	Rect r;
	ColorDrawable col;

	int width;
	int height;
	int left;
	int top;

	public DrawProgressBar(Context context) {
		super(context);
		if(sp._gradient){
			timeleft2 = timespent;
			timespent = background;
		}
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);

		if(init){
			width = (c.getWidth()/8)*5;
			height = (int) (width*0.2);
			init = false;
		}
		/* Fill the canvas with the background color */
		paint.setColor(background);
		c.drawPaint(paint);

		/* Draw the frame of the progressbar */
		paint.setAntiAlias(true);
		paint.setColor(frame);
		
		left = (c.getWidth() - width) / 2;
		top = (c.getHeight() - height) / 2;

		r = new Rect(left, top, left + width, top + height);
		c.drawRect(r, paint);

		/* Draw the backgroundcolor inside the frame */
		paint.setColor(background);
		r.set(r.left + 2, r.top + 2, r.right - 2, r.bottom - 2);
		c.drawRect(r, paint);

		/* Draw the timespent color (on the right) on top of the timeleft */
		paint.setColor(timespent);
		r.set(left + 3, top + 3, left + width - 3, top + height - 3);
		c.drawRect(r, paint);

		if (endTime >= SystemClock.currentThreadTimeMillis()) {
			paint.setStyle(Paint.Style.FILL);
			double percent = (endTime - SystemClock.currentThreadTimeMillis())
					/ totalTime;

			paint.setColor(timeleft2);
			r.set((left + 3), top + 3, left + 3 + (int) (width * percent), top + height - 3);
			c.drawRect(r, paint);
			
			/* Draw the timeleft color (on the left side) */
			col = new ColorDrawable(timeleft);
			col.setAlpha((int) (255*percent));
			paint.setColor(col.getColor());
			r.set((left + 3), top + 3, left + 3 + (int) (width * percent), top + height - 3);
			c.drawRect(r, paint);
			
			/*************** IMPORTANT ***************/
			/* Recalls Draw! */
			invalidate();
		} else {
			paint.setColor(timespent);
			r.set(left + 3, top + 3, left + width - 3, top + height - 3);
			c.drawRect(r, paint);
		}
	}
}
