package dk.aau.cs.giraf.wombat.drawlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import dk.aau.cs.giraf.TimerLib.SubProfile;

public class DrawHourglass extends View {
	SubProfile sp;

	private int background;
	private int frame;
	private int timeleft;
	private int timeleft2;
	private int timespent;
	private int totalTime;
	private double endTime;

	Paint paint = new Paint();
	Rect rtop, rbot, rleft, rright;
	Rect timeTop, timeBot;
	ColorDrawable col;

	int width, height;
	int top, left;
	int frameHeight, indent, glassLeft, glassRight, windowWidth, 
		windowHeight, glassThickness, glassThicknessBot, glassThicknessTop, glassBendHeight;
	int sandTopHeight, sandBotHeight;
	int triTopX, triTopY, triBotMidY, triBotX, triBotY;
	

	public DrawHourglass(Context context, SubProfile sub) {
		super(context);
		windowWidth = DrawLibActivity.frameWidth;
		windowHeight = DrawLibActivity.frameHeight;
		
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
		width = 300;
		height = 500;
		frameHeight = height / 20;
		indent = frameHeight;
		glassThickness = 3;
		glassThicknessBot = glassThickness - 1;
		glassThicknessTop = glassThickness - 1;
		glassBendHeight = 135;

	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		
		/* Function to calculate percentage */
		if (endTime >= SystemClock.currentThreadTimeMillis()) {
			double timenow = endTime - SystemClock.currentThreadTimeMillis();
			double percent = timenow / totalTime;
			
			if(percent>0.4){
				sandTopHeight = (int) (120*percent)-40;
				triBotMidY = (int) (0 - (40*percent));
			}
			if(percent<=0.4){
				sandBotHeight = (int) (0 - (percent*100));
				triTopX = (int) (106 - (100*percent));
				triTopY = triTopX;
				triBotY = sandBotHeight;
			}
			
			 // Do not change!
		}
		
		/* Fill the canvas with the background color */
		paint.setColor(background);
		c.drawPaint(paint);

		/* Draw the frame of the hourglass */
		paint.setAntiAlias(true);
		paint.setColor(frame);
		
		left = (windowWidth - width) / 2;
		top = (windowHeight - height) / 2;

		rtop = new Rect(left, top, left + width, top + frameHeight);
		rbot = new Rect(left, top + height - frameHeight, left + width, top + height);
		rleft = new Rect(left + indent, top, left - (frameHeight/2) + indent, top + height);
		rright = new Rect(left + width - indent, top, left + width - indent + (frameHeight/2), top + height);
		
		c.drawRect(rtop, paint);
		c.drawRect(rbot, paint);
		c.drawRect(rleft, paint);
		c.drawRect(rright, paint);
		
		/* Draw the "glass" top*/
		Path p = new Path();
		paint.setColor(frame);
		p.moveTo(left + (indent/2) + frameHeight, top + frameHeight);
		p.lineTo(left + width - (indent/2) - frameHeight, top + frameHeight);
		p.lineTo(left + width - (indent/2) - frameHeight, top + glassBendHeight);
		p.lineTo(left + width - (indent/2) - frameHeight - 105, top + 240);
		p.lineTo(left + (indent/2) + frameHeight + 105, top + 240);
		p.lineTo(left + (indent/2) + frameHeight, top + glassBendHeight);
		c.drawPath(p, paint);
		
		/* Color the "glass" top*/
		p = new Path();
		p.moveTo(left + (indent/2) + frameHeight + glassThickness, top + frameHeight);
		p.lineTo(left + width - (indent/2) - frameHeight - glassThickness, top + frameHeight);
		p.lineTo(left + width - (indent/2) - frameHeight - glassThickness, top + glassBendHeight - glassThicknessTop);
		p.lineTo(left + width - (indent/2) - frameHeight - 105 - glassThickness, top + 240 - glassThicknessTop);
		p.lineTo(left + (indent/2) + frameHeight + 105 + glassThickness, top + 240 - glassThicknessTop);
		p.lineTo(left + (indent/2) + frameHeight + glassThickness, top + glassBendHeight - glassThicknessTop);
		paint.setColor(timespent);
		c.drawPath(p, paint);
		
		/* Draw the "glass" bottom*/
		p = new Path();
		paint.setColor(frame);
		p.moveTo(left + (indent/2) + frameHeight,top - frameHeight + height);
		p.lineTo(left + width - (indent/2) - frameHeight, top - frameHeight + height);
		p.lineTo(left + width - (indent/2) - frameHeight, top - glassBendHeight + height);
		p.lineTo(left + width - (indent/2) - frameHeight - 105, top - 240 + height);
		p.lineTo(left + (indent/2) + frameHeight + 105, top - 240 + height);
		p.lineTo(left + (indent/2) + frameHeight, top - glassBendHeight + height);
		c.drawPath(p, paint);
		
		/* Color the "glass" bottom*/
		p = new Path();
		paint.setColor(timespent);
		p.moveTo(left + (indent/2) + frameHeight + glassThickness,top - frameHeight + height);
		p.lineTo(left + width - (indent/2) - frameHeight - glassThickness, top - frameHeight + height);
		p.lineTo(left + width - (indent/2) - frameHeight - glassThickness, top - glassBendHeight + height + glassThicknessBot);
		p.lineTo(left + width - (indent/2) - frameHeight - 105 - glassThickness, top - 240 + height + glassThicknessBot);
		p.lineTo(left + (indent/2) + frameHeight + 105 + glassThickness, top - 240 + height + glassThicknessBot);
		p.lineTo(left + (indent/2) + frameHeight + glassThickness, top - glassBendHeight + height + glassThicknessBot);
		c.drawPath(p, paint);
		
		/* Draw the "glass" middle piece*/
		p = new Path();
		paint.setColor(frame);
		p.moveTo(left + width - (indent/2) - frameHeight - 105, top + 240);
		p.lineTo(left + width - (indent/2) - frameHeight - 105, top + 260);
		p.lineTo(left + (indent/2) + frameHeight + 105, top + 260);
		p.lineTo(left + (indent/2) + frameHeight + 105, top + 240);
		c.drawPath(p, paint);
		
		/* Color the "glass" middle piece*/
		p = new Path();
		paint.setColor(timespent);
		p.moveTo(left + width - (indent/2) - frameHeight - 105 - glassThickness, top + 240 - glassThickness);
		p.lineTo(left + width - (indent/2) - frameHeight - 105 - glassThickness, top + 260 + glassThickness);
		p.lineTo(left + (indent/2) + frameHeight + 105 + glassThickness, top + 260 + glassThickness);
		p.lineTo(left + (indent/2) + frameHeight + 105 + glassThickness, top + 240 - glassThickness);
		c.drawPath(p, paint);
		
		/* Draw the "sand" in the hourglass */
		timeTop = new Rect(left + (indent/2) + frameHeight + glassThickness, top + glassBendHeight - glassThicknessTop,
				left + width - (indent/2) - frameHeight - glassThickness ,top + glassBendHeight - glassThicknessTop - sandTopHeight + 8); //+8 to get it to fit
		timeBot = new Rect(left + (indent/2) + frameHeight + glassThickness, top + height - frameHeight,
				left + width - (indent/2) - frameHeight - glassThickness, top + height - frameHeight - sandBotHeight);
		
		paint.setColor(timeleft);
		c.drawRect(timeTop, paint);
		c.drawRect(timeBot, paint);
		
		/* Draw the the sand in the triangle in the top of the glass*/
		Path tp = new Path();
		paint.setColor(timeleft);
		tp.moveTo((left + (indent/2) + frameHeight + glassThickness + triTopX), top + glassBendHeight - glassThicknessTop + triTopY); // change as time progress: + on bot x and y
		tp.lineTo(left + (indent/2) + frameHeight + 105 + glassThickness, top + 240 - glassThicknessTop);
		tp.lineTo(left + width - (indent/2) - frameHeight - 105 - glassThickness, top + 240 - glassThicknessTop);
		tp.lineTo((left + width - (indent/2) - frameHeight - glassThickness - triTopX), top + glassBendHeight - glassThicknessTop + triTopY); // change as time progress: - on x, + on y
		c.drawPath(tp, paint);
		
		
		/* Draw the the sand in the triangle in the bottom of the glass*/
		tp = new Path();
		paint.setColor(timeleft);
		tp.moveTo(left + (indent/2) + frameHeight + glassThickness, top + height - frameHeight - sandBotHeight);
		tp.lineTo(left + width - (indent/2) - frameHeight - glassThickness, top + height - frameHeight - sandBotHeight);
		tp.lineTo(left + (width/2), top + height - frameHeight - sandBotHeight - triBotMidY);
		c.drawPath(tp, paint);
		
		invalidate();
	}
}
