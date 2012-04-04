package dk.aau.cs.giraf.TimerLib;

public class Hourglass extends SubProfile {
	
	public int _sand;
	
	
	public Hourglass(String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor, int sand){
		super(name, description, size, bgcolor, timeLeftColor, timeSpentColor, frameColor, totalTime, changeColor);
		this._sand = sand;
	}

	public Hourglass(Hourglass obj){
		super(obj._name, obj._desc,obj._size,obj._bgcolor, obj._timeLeftColor,obj._timeSpentColor,obj._frameColor,obj._totalTime,obj._gradient);
		this._sand = obj._sand;
	}
	
	public Hourglass copy(){
		Hourglass copyP = new Hourglass(this);
		return copyP;
	}
	
	public int formType(){
		return formFactor.Hourglass.ordinal();
	}
}
