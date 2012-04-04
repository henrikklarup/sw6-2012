package dk.aau.cs.giraf.TimerLib;


public class TimeTimer extends SubProfile {
	
	public TimeTimer(String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		super(name, description, size, bgcolor, timeLeftColor, timeSpentColor, frameColor, totalTime, changeColor);
	}
	
	public TimeTimer(TimeTimer obj){
		super(obj._name, obj._desc,obj._size,obj._bgcolor, obj._timeLeftColor,obj._timeSpentColor,obj._frameColor,obj._totalTime,obj._gradient);
	}
	
	public TimeTimer copy(){
		TimeTimer copyP = new TimeTimer(this);
		return copyP;
	}
	
	public formFactor formType(){
		return formFactor.TimeTimer;
	}
}
