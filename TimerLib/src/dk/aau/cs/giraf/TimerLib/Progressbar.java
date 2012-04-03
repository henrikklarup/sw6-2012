package dk.aau.cs.giraf.TimerLib;

import dk.aau.cs.giraf.TimerLib.R;

public class Progressbar extends SubProfile {
	
	public String _direction;
	
	public Progressbar(String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor, String direction){
		super(name, description, size, bgcolor, timeLeftColor, timeSpentColor, frameColor, totalTime, changeColor);
		this._direction = direction;
	}
	
	public Progressbar(Progressbar obj){
		super(obj._name, obj._desc,obj._size,obj._bgcolor, obj._timeLeftColor,obj._timeSpentColor,obj._frameColor,obj._totalTime,obj._gradient);
		this._direction = obj._direction;
	}
	
	public Progressbar copy(){
		Progressbar copyP = new Progressbar(this);
		return copyP;
	}
}
