package sw6.autism.timer.TimerLib;

public class DigitalClock extends Profile {

	public DigitalClock(int id, String name, String description, int size, String bgcolor, String timeLeftColor, String timeSpentColor, int totalTime, boolean changeColor){
		super(id, name, description, size, bgcolor, timeLeftColor, timeSpentColor, totalTime,changeColor);
	}
	
	public DigitalClock(int id, String name, String description, int size, String bgcolor, String timeLeftColor, String timeSpentColor, boolean changeColor){
		super(id, name, description, size, bgcolor, timeLeftColor, timeSpentColor,changeColor);
	}
	
	
	
}
