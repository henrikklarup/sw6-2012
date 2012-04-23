package dk.aau.cs.giraf.wombat.drawlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class DrawDigital extends View {
	SubProfile sp;

	private int background;
	private int frame;
	private int timeleft;
	private int timeleft2;
	private int timespent;
	private int totalTime;
	private double endTime;

	Paint paint = new Paint();
	Rect r;
	ColorDrawable col;

	int frameHeight;
	int frameWidth;

	private int numHeight, numWidth, numSpace, lineSpace, lineWidth, halfLineWidt, halfNumHeight;

	private int mtimenow;
	

	public DrawDigital(Context context, SubProfile sub) {
		super(context);

		sp = sub;
		
		frameHeight = DrawLibActivity.frameHeight;
		frameWidth = DrawLibActivity.frameWidth;
		
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
		
		numWidth = frameWidth/8;
		numHeight = numWidth * 2;
		numSpace = numWidth/3;
		lineSpace = 2;
		lineWidth = numHeight / 16;
		
		halfLineWidt = lineWidth/2;
		halfNumHeight = numHeight / 2;
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		
		double timenow = (endTime - SystemClock.currentThreadTimeMillis());
		if(mtimenow == (int)timenow){
			timenow = (endTime - SystemClock.currentThreadTimeMillis());
			return;
		}
		mtimenow = (int) timenow;
		timenow = (endTime - SystemClock.currentThreadTimeMillis())/1000;
		
		/* Fill the canvas with the background color */
		paint.setColor(background);
		c.drawPaint(paint);
		
		int num, x, y;
		
		y = (frameHeight - numHeight) / 2;
		
		/* Draw first number */
		num = (int) (timenow / 60 / 10);
		x = numWidth/2 + numWidth;
		drawNumber(c, num, x, y);
		
		/* Draw second number */
		num = (int) (timenow / 60) - num * 10;
		x = numWidth/2 + numWidth * 2 + (numSpace);
		drawNumber(c, num, x, y);
		
		/* Draw third number */
		num = (int) (timenow % 60)/10;
		x =  numWidth/2 + numWidth * 3 + (numSpace * 3);
		drawNumber(c, num, x, y);
		
		/* Draw second number */
		num = (int) (timenow % 10);
		x = numWidth/2 + numWidth * 4 + (numSpace * 4);
		drawNumber(c, num, x, y);

		paint.setColor(frame);
		c.drawCircle((int) (numWidth * 4.25), (frameHeight / 2) - numSpace, lineWidth / 2, paint);
		c.drawCircle((int) (numWidth * 4.25), (frameHeight / 2) + numSpace, lineWidth / 2, paint);
		
		/*************** IMPORTANT ***************/
		/* Recalls Draw! */

		invalidate();

	}

	private void drawNumber(Canvas c, int num, int x, int y) {
		Path p = new Path();
		Paint paint = new Paint();
		paint.setColor(timeleft);
		
		/* Top left line */
		if(num == 4 || num == 5 || num == 6 || num == 8 || num == 9 || num == 0 ){
			p.moveTo(x - lineSpace + halfLineWidt, y + lineSpace); 							// Top point
			p.lineTo(x - lineSpace, y + halfLineWidt + lineSpace);								// Top left point start
			p.lineTo(x - lineSpace, y + halfNumHeight - halfLineWidt - lineSpace);				// Bottom left point start
			p.lineTo(x - lineSpace + halfLineWidt, y + halfNumHeight - lineSpace);			// Bottom point
			p.lineTo(x - lineSpace + lineWidth, y + halfNumHeight - halfLineWidt - lineSpace); // Bottom right point end
			p.lineTo(x - lineSpace + lineWidth, y + halfLineWidt + lineSpace); 				// Top right point end
			c.drawPath(p, paint);
		}
		
		/* Bottom left line */
		if(num == 2 || num == 6 || num == 8 || num == 0){
			p.moveTo(x - lineSpace + halfLineWidt, y + halfNumHeight + (lineSpace * 3)); 							// Top point
			p.lineTo(x - lineSpace, y + halfLineWidt + halfNumHeight + (lineSpace * 3));								// Top left point start
			p.lineTo(x - lineSpace, y + halfNumHeight - halfLineWidt + halfNumHeight + (lineSpace * 3));				// Bottom left point start
			p.lineTo(x - lineSpace + halfLineWidt, y + halfNumHeight + halfNumHeight + (lineSpace * 3));			// Bottom point
			p.lineTo(x - lineSpace + lineWidth, y + halfNumHeight - halfLineWidt + halfNumHeight + (lineSpace * 3)); // Bottom right point end
			p.lineTo(x - lineSpace + lineWidth, y + halfLineWidt + halfNumHeight + (lineSpace * 3)); 				// Top right point end
			c.drawPath(p, paint);
		}
		
		/* Top line */
		if(num == 2 || num == 3 || num == 5 || num == 6 || num == 7 || num == 8 || num == 9 || num == 0){
			p.moveTo(x + lineSpace + halfLineWidt , y - lineSpace);
			p.lineTo(x + lineSpace + lineWidth, y + halfLineWidt - lineSpace);
			p.lineTo(x - lineSpace + halfNumHeight, y + halfLineWidt - lineSpace);
			p.lineTo(x - lineSpace + halfNumHeight + halfLineWidt, y - lineSpace);
			p.lineTo(x - lineSpace + halfNumHeight, y - halfLineWidt - lineSpace);
			p.lineTo(x + lineSpace + lineWidth, y - halfLineWidt - lineSpace);
			c.drawPath(p, paint);
		}
		
		/* Middle line */
		if(num == 2 || num == 3 || num == 4 || num == 5 || num == 6 || num == 8 || num == 9){
			p.moveTo(x + lineSpace + halfLineWidt, y + halfNumHeight + lineSpace);
			p.lineTo(x + lineSpace + lineWidth, y + halfLineWidt + halfNumHeight + lineSpace);
			p.lineTo(x - lineSpace + halfNumHeight, y + halfLineWidt + halfNumHeight + lineSpace);
			p.lineTo(x - lineSpace + halfNumHeight + halfLineWidt, y + halfNumHeight + lineSpace);
			p.lineTo(x - lineSpace + halfNumHeight, y - halfLineWidt + halfNumHeight + lineSpace);
			p.lineTo(x + lineSpace + lineWidth, y - halfLineWidt + halfNumHeight + lineSpace);
			c.drawPath(p, paint);
		}
		
		/* Bottom line */
		if(num == 2 || num == 3 || num == 5 || num == 6 || num == 8 || num == 9 || num == 0){
			p.moveTo(x + lineSpace + halfLineWidt, y + numHeight + (lineSpace * 5));
			p.lineTo(x + lineSpace + lineWidth, y + halfLineWidt + numHeight + (lineSpace * 5));
			p.lineTo(x - lineSpace + halfNumHeight, y + halfLineWidt + numHeight + (lineSpace * 5));
			p.lineTo(x - lineSpace + halfNumHeight + halfLineWidt, y + numHeight + (lineSpace * 5));
			p.lineTo(x - lineSpace + halfNumHeight, y - halfLineWidt + numHeight + (lineSpace * 5));
			p.lineTo(x + lineSpace + lineWidth, y - halfLineWidt + numHeight + (lineSpace * 5));
			c.drawPath(p, paint);
		}
		
		/* Top right line */
		if(num == 1 || num == 2 || num == 3 || num == 4 || num == 7 || num == 8 || num == 9 || num == 0){
			p.moveTo(x + lineSpace + halfNumHeight + halfLineWidt, y + lineSpace); 							// Top point
			p.lineTo(x + lineSpace + halfNumHeight, y + halfLineWidt + lineSpace);								// Top left point start
			p.lineTo(x + lineSpace + halfNumHeight, y + halfNumHeight - halfLineWidt - lineSpace);				// Bottom left point start
			p.lineTo(x + lineSpace + halfNumHeight + halfLineWidt, y + halfNumHeight - lineSpace);			// Bottom point
			p.lineTo(x + lineSpace + halfNumHeight + lineWidth, y + halfNumHeight - halfLineWidt - lineSpace); // Bottom right point end
			p.lineTo(x + lineSpace + halfNumHeight + lineWidth, y + halfLineWidt + lineSpace); 				// Top right point end
			c.drawPath(p, paint);
		}
		
		/* Bottom right line */
		if(num == 1 || num == 3 || num == 4 || num == 5 || num == 6 || num == 7 || num == 8 || num == 9 || num == 0){
			p.moveTo(x + lineSpace + halfNumHeight + halfLineWidt, y + halfNumHeight + (lineSpace * 3)); 							// Top point
			p.lineTo(x + lineSpace + halfNumHeight, y + halfLineWidt + halfNumHeight + (lineSpace * 3));								// Top left point start
			p.lineTo(x + lineSpace + halfNumHeight, y + halfNumHeight - halfLineWidt + halfNumHeight + (lineSpace * 3));				// Bottom left point start
			p.lineTo(x + lineSpace + halfNumHeight + halfLineWidt, y + halfNumHeight + halfNumHeight + (lineSpace * 3));			// Bottom point
			p.lineTo(x + lineSpace + halfNumHeight + lineWidth, y + halfNumHeight - halfLineWidt + halfNumHeight + (lineSpace * 3)); // Bottom right point end
			p.lineTo(x + lineSpace + halfNumHeight + lineWidth, y + halfLineWidt + halfNumHeight + (lineSpace * 3)); 				// Top right point end
			c.drawPath(p, paint);
		}
	}
}
