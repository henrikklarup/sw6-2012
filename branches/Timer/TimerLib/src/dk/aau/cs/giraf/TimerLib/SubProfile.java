package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;
import java.util.Comparator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import dk.aau.cs.giraf.TimerLib.R;

public class SubProfile implements Renderer, formInterface, Comparable<SubProfile>{
	
	Guardian guard = Guardian.getInstance();
	private int _id = -1;
	public String _name = "Default";
	public String _desc = "Default desc";
	public int _size = 100;
	public int _bgcolor = 0xffffffff;
	public int _timeLeftColor = 0xffffffff;
	public int _timeSpentColor = 0xffffffff;
	public int _frameColor = 0xffffffff;
	public int _totalTime = 100;
	public boolean _gradient = false;
	protected SubProfile _attachment = null;
	//constructor
	public SubProfile(String name, String description, int size, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		if(this._id == -1){
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
		if(this._id == -1){
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
	
	public SubProfile(){
		if(this._id == -1){
			this._id = guard.getId();
		}
	}
	
	public int getId(){
		return this._id;
	}
	
	protected void setId(int tempId){
		this._id = tempId;
	}
	
	public SubProfile getAttachment(){
		return _attachment;
	}
	
	public void setAttachment(SubProfile p){
		this._attachment = p;
		this._attachment._totalTime = this._totalTime;
	}
	
	public formFactor formType(){
		return formFactor.SubProfile;
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
	
	public void save(SubProfile oldProfile){
		START:
		for(Child c : guard.Children()){
			for(SubProfile p : c.SubProfiles()){
				if(p._id == oldProfile._id){
					c.save(this);
					c.SubProfiles().remove(p);
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

		public SubProfile toHourglass() {
			Hourglass form = new Hourglass(this._name, this._desc, this._size, this._bgcolor, this._timeLeftColor, this._timeSpentColor, this._frameColor, this._totalTime, this._gradient);
			form.setId(this.getId());
			return form;
		}

		public SubProfile toProgressBar() {
			ProgressBar form = new ProgressBar(this._name, this._desc, this._size, this._bgcolor, this._timeLeftColor, this._timeSpentColor, this._frameColor, this._totalTime, this._gradient);
			form.setId(this.getId());
			return form;
		}

		public SubProfile toTimeTimer() {
			TimeTimer form = new TimeTimer(this._name, this._desc, this._size, this._bgcolor, this._timeLeftColor, this._timeSpentColor, this._frameColor, this._totalTime, this._gradient);
			form.setId(this.getId());
			return form;
		}

		public SubProfile toDigitalClock() {
			DigitalClock form = new DigitalClock(this._name, this._desc, this._size, this._bgcolor, this._timeLeftColor, this._timeSpentColor, this._frameColor, this._totalTime, this._gradient);
			form.setId(this.getId());
			return form;
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
