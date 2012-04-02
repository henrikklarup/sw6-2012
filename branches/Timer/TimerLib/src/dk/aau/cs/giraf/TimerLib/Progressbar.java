package dk.aau.cs.giraf.TimerLib;

import dk.aau.cs.giraf.TimerLib.R;

public class Progressbar extends SubProfile {
	
	public String _name;
	public String _description;
	public String _direction;
	
	public Progressbar(int id, String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor, String direction){
		super(id, name, description, size, bgcolor, timeLeftColor, timeSpentColor, frameColor, totalTime, changeColor);
		this._direction = direction;
	}
}
