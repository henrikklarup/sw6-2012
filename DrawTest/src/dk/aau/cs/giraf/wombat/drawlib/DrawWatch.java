package dk.aau.cs.giraf.wombat.drawlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class DrawWatch extends View {
	private SubProfile sp;

	private int background;
	private int frame;
	private int timeleft;
	private int timeleft2;
	private int timespent;
	private int totalTime;
	private double endTime;

	private double rotation;

	private Paint paint = new Paint();
	private Rect r;
	private ColorDrawable col;

	private int width;
	private int height;
	private int left;
	private int right;
	private int top;
	private int bottom;

	public DrawWatch(Context context, SubProfile sub) {
		super(context);

		if (DrawLibActivity.frameWidth > DrawLibActivity.frameHeight)
			width = (int) (DrawLibActivity.frameHeight / 1.5);
		else
			width = (int) (DrawLibActivity.frameWidth / 1.5);
		height = width;

		sp = sub;

		background = sp.bgcolor;
		frame = sp.frameColor;
		timeleft = sp.timeLeftColor;
		timeleft2 = sp.timeLeftColor;
		timespent = sp.timeSpentColor;
		totalTime = (sp.totalTime - 1) * 1000;
		endTime = SystemClock.currentThreadTimeMillis() + totalTime;

		if (sp.gradient) {
			timeleft2 = timespent;
			timespent = background;
		}
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		/* Fill the canvas with the background color */
		paint.setColor(background);
		c.drawPaint(paint);

		/* Draw the frame of the progressbar */
		paint.setAntiAlias(true);
		paint.setColor(frame);

		left = DrawLibActivity.frameWidth / 2;
		top = DrawLibActivity.frameHeight / 2;

		c.drawCircle(left, top, width / 2, paint);

		paint.setColor(timespent);
		c.drawCircle(left, top, (width / 2) - 3, paint);

		left = ((DrawLibActivity.frameWidth - width) / 2) + 3;
		right = (((DrawLibActivity.frameWidth - width) / 2) + width) - 3;
		top = ((DrawLibActivity.frameHeight - height) / 2) + 3;
		bottom = (((DrawLibActivity.frameHeight - height) / 2) + height) - 3;

		if (endTime >= SystemClock.currentThreadTimeMillis()) {
			double timenow = endTime - SystemClock.currentThreadTimeMillis();
			double percent = (timenow) / totalTime;
			
			// 0.1 is what 1 second corresponds to in degrees
			rotation = (0.1 * (endTime - SystemClock.currentThreadTimeMillis()) / 1000) + 1;
			Log.e("Test", rotation + "");

			paint.setColor(timeleft2);
			RectF rf = new RectF(left, top, right, bottom);
			c.drawArc(rf, 270 - (int) rotation, (int) rotation, true, paint);
			
			col = new ColorDrawable(timeleft);
			col.setAlpha((int) (255 * percent));
			paint.setColor(col.getColor());
			c.drawArc(rf, 270 - (int) rotation, (int) rotation, true, paint);

			/*************** IMPORTANT ***************/
			/* Recalls Draw! */
			invalidate();
		}
	}
}
