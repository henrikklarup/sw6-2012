package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;
import java.util.Comparator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import dk.aau.cs.giraf.TimerLib.R;

public class SubProfile implements Renderer, Comparable<SubProfile>{
	
	Guardian guard = Guardian.getInstance();
	private int _id = -1;
	public String _name;
	public String _desc;
	public int _size;
	public int _bgcolor;
	public int _timeLeftColor;
	public int _timeSpentColor;
	public int _frameColor;
	public int _totalTime;
	public boolean _gradient;
	protected SubProfile _attachment;
	//constructor
	public SubProfile(String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		if(_id == -1){
			this._id = guard.getId();
		}
		this._name = name;
		this._desc = description;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._frameColor = frameColor;
		this._totalTime = totalTime;
		this._gradient = changeColor;
	}
		
	public SubProfile(String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, boolean changeColor){
		if(_id == -1){
			this._id = guard.getId();
		}
		this._name = name;
		this._desc = description;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._frameColor = frameColor;
		this._gradient = changeColor;
	}
	
	public int getId(){
		return this._id;
	}
	
	public SubProfile getAttachment(){
		return _attachment;
	}
	
	public void setAttachment(SubProfile p){
		this._attachment = p;
		this._attachment._totalTime = this._totalTime;
	}
	
	public int formType(){
		return formFactor.SubProfile.ordinal();
	}
	
	public SubProfile copy(){
		return this;
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
	
	public void save(){
		boolean save = false;
		START:
		for(Child c : guard.Children()){
			for(SubProfile p : c.SubProfiles()){
				if(p._id == this._id){
					c.SubProfiles().remove(p);
					c.save(this);
					save = true;
					break START;
				}
			}
		}
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
