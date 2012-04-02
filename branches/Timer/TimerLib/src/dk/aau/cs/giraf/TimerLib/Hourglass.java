package dk.aau.cs.giraf.TimerLib;

public class Hourglass extends SubProfile {
	
	public String _name;
	public String _description;
	public int _sand;
	
	
	public Hourglass(int id, String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor, int sand){
		super(id, name, description, size, bgcolor, timeLeftColor, timeSpentColor, frameColor, totalTime, changeColor);
		this._sand = sand;
	}
}
