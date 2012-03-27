package sw6.autism.timer.TimerLib;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class Profile implements Renderer{

	public int _id;
	public int _size;
	public String _bgcolor;
	public String _timeLeftColor;
	public String _timeSpentColor;
	public int _totalTime;
	public DigitalClock _digital;
	public boolean _changeColor;
	//constructor
	public Profile(int id, String name, String description, int size, String bgcolor, String timeLeftColor, String timeSpentColor, int totalTime, DigitalClock digital, boolean changeColor){
		this._id = id;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._totalTime = totalTime;
		this._digital = digital;
		this._changeColor = changeColor;
	}
	
	public Profile(int id, String name, String description, int size, String bgcolor, String timeLeftColor, String timeSpentColor, int totalTime, boolean changeColor){
		this._id = id;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
		this._totalTime = totalTime;
		this._changeColor = changeColor;
	}
	
	public Profile(int id, String name, String description, int size, String bgcolor, String timeLeftColor, String timeSpentColor, boolean changeColor){
		this._id = id;
		this._size = size;
		this._bgcolor = bgcolor;
		this._timeLeftColor = timeLeftColor;
		this._timeSpentColor = timeSpentColor;
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

	public void onDrawFrame(GL10 arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}
	
}
