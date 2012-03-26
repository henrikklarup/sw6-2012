package sw6.autism.timer.TimerLib;

import java.util.ArrayList;

public class Profile extends Child{

	public int _size;
	public String _bgcolor;
	String _timeLeftColor;
	String _timeSpentColor;
	int _totalTime;
	boolean _digital;
	boolean _changeColor;
	//constructor
	public Profile(int size, String bgcolor, String timeLeftColor, String timeSpentColor, int totalTime, boolean digital, boolean changeColor){
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._totalTime = totalTime;
		this._digital = digital;
		this._changeColor = changeColor;
	}
	
	public void load(ArrayList<Profile> sett){
		for (Profile s : sett)
		{
			s.save();
		}
	}
	
	public void save(){
		
	}
	
	public void loadSettigns(){
		
	}
	
}
