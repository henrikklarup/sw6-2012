package dk.aau.cs.giraf.TimerLib;


public class TimeTimer extends SubProfile {
	
	public TimeTimer(String name, String description, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		super(name, description, bgcolor, timeLeftColor, timeSpentColor, frameColor, totalTime, changeColor);
	}
	
	public TimeTimer(TimeTimer obj){
		super(obj.name, obj.desc,obj.bgcolor, obj.timeLeftColor,obj.timeSpentColor,obj.frameColor,obj.totalTime,obj.gradient);
	}
	
	public TimeTimer copy(){
		TimeTimer copyP = new TimeTimer(this);
		if(this._attachment != null){
			copyP.setAttachment(this._attachment);
		}
		
		return copyP;
	}
	
	public formFactor formType(){
		return formFactor.TimeTimer;
	}
}
