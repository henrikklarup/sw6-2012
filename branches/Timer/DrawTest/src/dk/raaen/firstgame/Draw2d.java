package dk.raaen.firstgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class Draw2d extends View {
	SubProfile sp = new SubProfile("", "", 10, 0xFF000000, 0xFFFF0000,
			0xFFFFFFFF, 0xFFFF0000, 10, false);
	int background = sp._bgcolor;
	int frame = sp._frameColor;
	int timeleft = sp._timeLeftColor;
	int timespent = sp._timeSpentColor;

	double totalTime = (sp._totalTime - 1) * 1000;
	double endTime = SystemClock.currentThreadTimeMillis() + totalTime;
	Paint paint = new Paint();
	Rect r;

	int width = 300;
	int height = 20;
	int left;
	int top;

	public Draw2d(Context context) {
		super(context);
		
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);

		/* Fill the canvas with the background color */
		paint.setColor(background);
		c.drawPaint(paint);

		/* Draw a semicircle 0-90 degrees */
//		paint.setColor(frame);
//		RectF rf = new RectF( 10, 270,  70, 330);
//		c.drawArc(rf, 0, 90, true, paint);

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

		/* Draw the timeleft color (on the left side) */
		paint.setColor(timeleft);
		r.set(left + 3, top + 3, left + width - 3, top + height - 3);
		c.drawRect(r, paint);

		if (endTime >= SystemClock.currentThreadTimeMillis()) {
			paint.setStyle(Paint.Style.FILL);
			double percent = (endTime - SystemClock.currentThreadTimeMillis())
					/ totalTime;

			Log.e("Test", "" + percent);

			/* Draw the timespent color (on the right) on top of the timeleft */
			paint.setColor(timespent);
			r.set((left + 3) + (int) (width * percent), top + 3, left - 3
					+ width, top + height - 3);
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
