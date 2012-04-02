package dk.aau.cs.giraf.TimerLib;

public class DigitalClock extends SubProfile {

	public DigitalClock(int id, String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		super(id, name, description, size, bgcolor, timeLeftColor, timeSpentColor, totalTime,frameColor,changeColor);
	}
	
	public DigitalClock(int id, String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, boolean changeColor){
		super(id, name, description, size, bgcolor, timeLeftColor, timeSpentColor,frameColor,changeColor);
	}
	
	
	
}
