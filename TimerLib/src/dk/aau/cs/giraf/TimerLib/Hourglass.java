package dk.aau.cs.giraf.TimerLib;

public class Hourglass extends SubProfile {
	
	public Hourglass(String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		super(name, description, size, bgcolor, timeLeftColor, timeSpentColor, frameColor, totalTime, changeColor);
	}

	public Hourglass(Hourglass obj){
		super(obj._name, obj._desc,obj._size,obj._bgcolor, obj._timeLeftColor,obj._timeSpentColor,obj._frameColor,obj._totalTime,obj._gradient);
	}
	
	public Hourglass copy(){
		Hourglass copyP = new Hourglass(this);
		if(this._attachment != null){
			copyP.setAttachment(this._attachment);
		}
		return copyP;
	}
	
	public formFactor formType(){
		return formFactor.Hourglass;
	}
}
