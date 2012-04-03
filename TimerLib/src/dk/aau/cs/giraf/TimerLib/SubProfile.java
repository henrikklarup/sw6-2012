package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;
import java.util.Comparator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import dk.aau.cs.giraf.TimerLib.R;

public class SubProfile implements Renderer, Comparable<SubProfile>{

	public enum forms{Hourglass, TimeTimer, Progressbar, DigitalClock}
	
	public int _id;
	public String _name;
	public int _size;
	public int _bgcolor;
	public int _timeLeftColor;
	public int _timeSpentColor;
	public int _frameColor;
	public int _totalTime;
	public boolean _gradient;
	public SubProfile attachment;
	//constructor
	public SubProfile(int id, String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		this._id = id;
		this._name = name;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._frameColor = frameColor;
		this._totalTime = totalTime;
		this._gradient = changeColor;
	}
		
	public SubProfile(int id, String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, boolean changeColor){
		this._id = id;
		this._name = name;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._frameColor = frameColor;
		this._gradient = changeColor;
	}
	
	public void load(ArrayList<SubProfile> sett){
		for (SubProfile s : sett)
		{
			
		}
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
	    
	    public String formType(){
	    	return "sup";
	    }
	    
	    //convertTo methods
	    /*
	    public Hourglass toHourglass(){
	    	Hourglass glass = new Hourglass();
	    	return glass;
	    }
	    
	    public Hourglass toHourglass(){
	    	Hourglass glass = new Hourglass();
	    	return glass;
	    }*/
}
