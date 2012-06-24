package dk.aau.cs.giraf.TimerLib;

import java.util.HashMap;

import dk.aau.cs.giraf.oasis.lib.models.Media;
/**
 * 
 * This class represent a timer as an attachment
 * 
 * Layer: Tools
 *
 */

public class Timer extends Attachment {
	
	private formFactor _form;
	private int _bgColor = -1;
	private int _frameColor = -1;
	private int _timeLeftColor = -1;
	private int _timeSpentColor = -1;
	private boolean _gradient = false;
	private int _time;
/**
 * Timer constructor
 * @param p SubProfile which you want to use as an attachment
 */
	public Timer(SubProfile p){
		this._form = p.formType();
		this._bgColor = p.bgcolor;
		this._frameColor = p.frameColor;
		this._timeLeftColor = p.timeLeftColor;
		this._timeSpentColor = p.timeSpentColor;
		this._gradient = p.gradient;
		this._time = p.get_totalTime();
	}
	/**
	 * Timer constructor
	 * @param form fitting formFactor
	 * @param bgColor background color
	 * @param timeLeftColor time left color
	 * @param timeSpentColor time consumed color
	 * @param frameColor frame color of the timer
	 * @param time the total time of the timer, need to match the main timer
	 * @param gradient true = on, false = off. Makes a gradient effect between timeLeftColor and timeSpentColor
	 */
	public Timer(formFactor form, int bgColor, int timeLeftColor, int timeSpentColor, int frameColor, int time, boolean gradient){
		this._form = form;
		this._bgColor = bgColor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._frameColor = frameColor;
		this._time = time;
		this._gradient = gradient;
	}
	
	/**
	 * Converts this Timer attachment into a SubProfile object, makes it easier to save in the OasisLocalDatabase
	 * @return returns the converted subprofile
	 */
	public SubProfile genSub(){
		
		SubProfile sp = null;
		switch(this._form){
		case DigitalClock:
			sp = new DigitalClock("undefined", "undefined", this._bgColor, this._timeLeftColor, this._timeSpentColor, this._frameColor, this._time, this._gradient);
			break;
		case TimeTimer:
			sp = new TimeTimer("undefined", "undefined", this._bgColor, this._timeLeftColor, this._timeSpentColor, this._frameColor, this._time, this._gradient);
			break;
		case Hourglass:
			sp = new Hourglass("undefined", "undefined", this._bgColor, this._timeLeftColor, this._timeSpentColor, this._frameColor, this._time, this._gradient);
			break;
		case ProgressBar:
			sp = new ProgressBar("undefined", "undefined", this._bgColor, this._timeLeftColor, this._timeSpentColor, this._frameColor, this._time, this._gradient);
			break;
		}
		
		return sp;
	}
/**
 * @return this timer attachment
 */
	public Timer getAttachment(){
		return this;
	}
	/**
	 * @return fitting formFactor, Timer.
	 */
	public formFactor getForm(){
		return formFactor.Timer;
	}
	/**
	 * 
	 * @return the formFactor of the timer, TimeTimer, ProgressBar, Hourglass, DigitalClock
	 */
	public formFactor formType(){
		return this._form;
	}
	/**
	 * Changes the time on the timer attachment, to fit the main timer
	 */
	void changeTime(int t){
		this._time = t;
	}
	/**
	 * Generates a HashMap for saving in the the OasisLocalDatabase
	 * @return returns a map which fit the settings of this Timer attachment
	 */
	public HashMap getHashMap(HashMap map){
		//Defines what kind of attachment it is
		map.put("AttachmentForm", String.valueOf(this.getForm()));
		
		//Timer
		map.put("timerForm", String.valueOf(this._form));
		map.put("_bgColor", String.valueOf(this._bgColor));
		map.put("_frameColor", String.valueOf(this._frameColor));
		map.put("_timeLeftColor", String.valueOf(this._timeLeftColor));
		map.put("_timeSpentColor", String.valueOf(this._timeSpentColor));
		map.put("_gradient", String.valueOf(this._gradient));
		
		//SingleImg
		map.put("singleImgId", String.valueOf(-1));
		
		//SplitImg
		map.put("leftImgId", String.valueOf(-1));
		map.put("rightImgId", String.valueOf(-1));
		
		return map;
	}
}
