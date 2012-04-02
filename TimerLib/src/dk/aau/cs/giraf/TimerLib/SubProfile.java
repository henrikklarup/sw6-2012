package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;
import java.util.Comparator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import dk.aau.cs.giraf.TimerLib.R;

public class SubProfile implements Renderer, Comparable<SubProfile>{

	public int _id;
	public String _name;
	public int _size;
	public String _bgcolor;
	public String _timeLeftColor;
	public String _timeSpentColor;
	public int _totalTime;
	public DigitalClock _digital;
	public boolean _changeColor;
	//constructor
	public SubProfile(int id, String name, String description, int size, String bgcolor, String timeLeftColor, String timeSpentColor, int totalTime, DigitalClock digital, boolean changeColor){
		this._id = id;
		this._name = name;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._totalTime = totalTime;
		this._digital = digital;
		this._changeColor = changeColor;
	}
	
	public SubProfile(int id, String name, String description, int size, String bgcolor, String timeLeftColor, String timeSpentColor, int totalTime, boolean changeColor){
		this._id = id;
		this._name = name;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._totalTime = totalTime;
		this._changeColor = changeColor;
	}
	
	public SubProfile(int id, String name, String description, int size, String bgcolor, String timeLeftColor, String timeSpentColor, boolean changeColor){
		this._id = id;
		this._name = name;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._changeColor = changeColor;
	}
	
	public void load(ArrayList<SubProfile> sett){
		for (SubProfile s : sett)
		{
			s.save();
		}
	}
	
	public void save(){
		
	}
	
	public void loadSettigns(){
		
	}

	public void onDrawFrame(GL10 arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}

	 public boolean equals(Object o) {
	        if (!(o instanceof SubProfile))
	            return false;
	        SubProfile n = (SubProfile) o;
	        return n._name.equals(_name) && n._name.equals(_name);
	    }

	    public int hashCode() {
	        return 31*_name.hashCode() + _name.hashCode();
	    }

	    public String toString() {
		return _name + " " + _name;
	    }

	    public int compareTo(SubProfile n) {
	        int lastCmp = _name.compareTo(n._name);
	        return (lastCmp != 0 ? lastCmp : _name.compareTo(n._name));
	    }
}
