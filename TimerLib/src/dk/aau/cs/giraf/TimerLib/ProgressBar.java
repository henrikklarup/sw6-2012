package dk.aau.cs.giraf.TimerLib;

import dk.aau.cs.giraf.TimerLib.R;

public class ProgressBar extends SubProfile {
	
	public String _direction;
	
	public ProgressBar(String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		super(name, description, size, bgcolor, timeLeftColor, timeSpentColor, frameColor, totalTime, changeColor);
	}
	
	public ProgressBar(ProgressBar obj){
		super(obj._name, obj._desc,obj._size,obj._bgcolor, obj._timeLeftColor,obj._timeSpentColor,obj._frameColor,obj._totalTime,obj._gradient);
	}
	
	public ProgressBar copy(){
		ProgressBar copyP = new ProgressBar(this);
		if(this._attachment != null){
			copyP.setAttachment(this._attachment);
		}
		return copyP;
	}
	
	public formFactor formType(){
		return formFactor.ProgressBar;
	}
}
