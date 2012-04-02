package dk.aau.cs.giraf.TimerLib;

import dk.aau.cs.giraf.TimerLib.R;

public class Progressbar extends SubProfile {
	
	public String _name;
	public String _description;
	public String _direction;
	
	public Progressbar(int id, String name, String description, int size, String bgcolor, String timeLeftColor, String timeSpentColor, int totalTime, DigitalClock digital, boolean changeColor, String direction){
		super(id, name, description, size, bgcolor, timeLeftColor, timeSpentColor, totalTime,digital,changeColor);
		this._direction = direction;
	}
}
