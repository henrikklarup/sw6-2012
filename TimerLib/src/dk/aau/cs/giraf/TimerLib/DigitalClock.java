package dk.aau.cs.giraf.TimerLib;

public class DigitalClock extends SubProfile {

	public DigitalClock(String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		super(name, description, size, bgcolor, timeLeftColor, timeSpentColor, totalTime,frameColor,changeColor);
	}
	
	public DigitalClock(DigitalClock obj){
		super(obj._name, obj._desc,obj._size,obj._bgcolor, obj._timeLeftColor,obj._timeSpentColor,obj._frameColor,obj._totalTime,obj._gradient);
	}
	
	public DigitalClock copy(){
		DigitalClock copyP = new DigitalClock(this);
		return copyP;
	}	
	
	public formFactor formType(){
		return formFactor.DigitalClock;
	}
	
}
