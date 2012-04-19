package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class SubProfile implements Comparable<SubProfile>{
	
	Guardian guard = Guardian.getInstance();
	private int _id = -1;
	public String name = "Default";
	public String desc = "Default desc";
	public int bgcolor = 0xffffffff;
	public int timeLeftColor = 0xffffffff;
	public int timeSpentColor = 0xffffffff;
	public int frameColor = 0xffffffff;
	public int totalTime = 100;
	public boolean gradient = false;
	public boolean save = true;
	public boolean saveAs = true;
	protected SubProfile _attachment = null;
	private long _attachmentId = -1;
	private long _appId = -1;

	//constructor
	public SubProfile(String name, String description, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		if(this._id == -1){
			this._id = guard.getId();
		}
		this.name = name;
		this.desc = description;
		this.bgcolor = bgcolor;
		this.timeLeftColor = timeLeftColor;
		this.timeSpentColor = timeSpentColor;
		this.frameColor = frameColor;
		this.totalTime = totalTime;
		this.gradient = changeColor;
	}
		
	public SubProfile(String name, String description, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, boolean changeColor){
		if(this._id == -1){
			this._id = guard.getId();
		}
		this.name = name;
		this.desc = description;
		this.bgcolor = bgcolor;
		this.timeLeftColor = timeLeftColor;
		this.timeSpentColor = timeSpentColor;
		this.frameColor = frameColor;
		this.gradient = changeColor;
	}
	
	
	void setAppId(long id){
		this._appId = id;
	}
	
	long getAppId(){
		return this._appId;
	}
	
	public void select(){
		guard.setSubProfile(this);
	}
	
	public void delete(){
		START:
			for(Child c : guard.Children()){
				for(SubProfile p : c.SubProfiles()){
					if(p._id == this._id){
						guard.delete(c, this);
						break START;
					}
				}
			}
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
	
	long getAttachmentId(){
		return this._attachmentId;
	}
	
	void setAttachmentId(long id){
		this._attachmentId = id; 
	}
	
	public void setAttachment(SubProfile p){
		this._attachmentId = p.getAppId();
		this._attachment = p;
		this._attachment.totalTime = this.totalTime;
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
	
	public SubProfile save(SubProfile oldProfile){
		START:
		for(Child c : guard.Children()){
			for(SubProfile p : c.SubProfiles()){
				if(p._id == oldProfile._id){
					if(oldProfile.getAttachment() != null){
						this.setAttachment(oldProfile.getAttachment());
					}
					c.save(this);
					c.SubProfiles().remove(p);
					break START;
				}
			}
		}
		return this;
		
	}
	
	public void addLastUsed(SubProfile oldProfile){
		if(oldProfile == null){
			guard.addLastUsed(this);
		} else {
		this._id = oldProfile._id;
		guard.addLastUsed(this);
		}
}

	 public boolean equals(Object o) {
	        if (!(o instanceof SubProfile))
	            return false;
	        SubProfile n = (SubProfile) o;
	        return n.name.equals(name) && n.name.equals(name);
	    }

	    public int hashCode() {
	        return 31*name.hashCode() + name.hashCode();
	    }

	    public String toString() {
		return name + " " + name;
	    }

	    public int compareTo(SubProfile n) {
	        int lastCmp = name.compareTo(n.name);
	        return (lastCmp != 0 ? lastCmp : name.compareTo(n.name));
	    }

		public SubProfile toHourglass() {
			Hourglass form = new Hourglass(this.name, this.desc, this.bgcolor, this.timeLeftColor, this.timeSpentColor, this.frameColor, this.totalTime, this.gradient);
			form.setId(this.getId());		
			if(this._attachment != null){
				form.setAttachment(this._attachment);
			}
			return form;
		}

		public SubProfile toProgressBar() {
			ProgressBar form = new ProgressBar(this.name, this.desc, this.bgcolor, this.timeLeftColor, this.timeSpentColor, this.frameColor, this.totalTime, this.gradient);
			form.setId(this.getId());
			if(this._attachment != null){
				form.setAttachment(this._attachment);
			}
			return form;
		}

		public SubProfile toTimeTimer() {
			TimeTimer form = new TimeTimer(this.name, this.desc, this.bgcolor, this.timeLeftColor, this.timeSpentColor, this.frameColor, this.totalTime, this.gradient);
			form.setId(this.getId());
			if(this._attachment != null){
				form.setAttachment(this._attachment);
			}
			return form;
		}

		public SubProfile toDigitalClock() {
			DigitalClock form = new DigitalClock(this.name, this.desc, this.bgcolor, this.timeLeftColor, this.timeSpentColor, this.frameColor, this.totalTime, this.gradient);
			form.setId(this.getId());
			if(this._attachment != null){
				form.setAttachment(this._attachment);
			}
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