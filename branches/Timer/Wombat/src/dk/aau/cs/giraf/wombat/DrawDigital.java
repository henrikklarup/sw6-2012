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

public class DrawDigital extends View {
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
	Rect r;
	ColorDrawable col;

	int width;
	int height;
	int left;
	int top;

	public DrawDigital(Context context, SubProfile sub) {
		super(context);
		if (sp.gradient) {
			timeleft2 = timespent;
			timespent = background;
		}
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);

		if (init) {
			width = (c.getWidth() / 8) * 5;
			height = width;
			init = false;
		}
		/* Fill the canvas with the background color */
		paint.setColor(background);
		c.drawPaint(paint);

		/* Draw the frame of the progressbar */
		paint.setAntiAlias(true);
		paint.setColor(timespent);

		left = (c.getWidth() - width) / 2;
		top = (c.getHeight() - height) / 2;
		
		Path p = new Path();
		p.moveTo(20, 20);
		p.lineTo(40, 40);
		p.lineTo(40, 60);
		p.close();
		c.drawPath(p, paint);

	}
}
