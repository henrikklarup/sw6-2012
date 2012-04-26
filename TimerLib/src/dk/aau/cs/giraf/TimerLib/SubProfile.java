package dk.aau.cs.giraf.TimerLib;

import java.util.HashMap;

public class SubProfile implements Comparable<SubProfile>{
	
	Guardian guard = Guardian.getInstance();
	private long _id = -1;
	public String name = "Default";
	public String desc = "Default desc";
	public int bgcolor = 0xffffffff;
	public int timeLeftColor = 0xffffffff;
	public int timeSpentColor = 0xffffffff;
	public int frameColor = 0xffffffff;
	private int _totalTime = 100;
	public boolean gradient = false;
	public boolean save = true;
	public boolean saveAs = true;
	protected SubProfile _attachment = null;
	private long _attachmentId = -1;
	private long DB_id = -1;

	//constructor
	public SubProfile(String name, String description, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		if(this._id == -1){
			this._id = guard.getId();
		}		this.name = name;
		this.desc = description;
		this.bgcolor = bgcolor;
		this.timeLeftColor = timeLeftColor;
		this.timeSpentColor = timeSpentColor;
		this.frameColor = frameColor;
		this._totalTime = totalTime;
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
	
	public SubProfile(SubProfile obj){
		if(this._id == -1){
			this._id = guard.getId();
		}
		this.name = obj.name;
		this.desc = obj.desc;
		this.bgcolor = obj.bgcolor;
		this.timeLeftColor = obj.timeLeftColor;
		this.timeSpentColor = obj.timeSpentColor;
		this.frameColor = obj.frameColor;
		this._totalTime = obj._totalTime;
		this.gradient = obj.gradient;
	}
	
	/**
	 * Generates a hashmap with settings of the subprofile
	 * @param p
	 * 		The subprofile which is supposed to be stored to the hashmap
	 * @return
	 * 		A hashmap with the settings stored in
	 */
	public HashMap<String, String> getHashMap(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("db_id", String.valueOf(this.getDB_id()));
		map.put("type", this.formType().toString());		
		map.put("Attachment", String.valueOf(this.getAttachmentId()));		
		map.put("Name", this.name);		
		map.put("desc", this.desc);		
		map.put("bgcolor", String.valueOf(this.bgcolor));		
		map.put("timeLeftColor", String.valueOf(this.timeLeftColor));		
		map.put("timeSpentColor", String.valueOf(this.timeSpentColor));		
		map.put("frameColor", String.valueOf(this.frameColor));
		map.put("totalTime", String.valueOf(this.get_totalTime()));		
		map.put("gradient", String.valueOf(this.gradient));		
		map.put("save", String.valueOf(this.save));		
		map.put("saveAs", String.valueOf(this.saveAs));
		
		return map;
	}
	
	public int get_totalTime() {
		return _totalTime;
	}
	
	/**
	 * Sets the time of the subprofile and its attachment
	 * @param time
	 * 		Total runtime of the subprofile (in seconds)
	 */
	public void set_totalTime(int _totalTime) {
		this._totalTime = _totalTime;
		// Syncronizes the time of the profile and its attachment
		if(this._attachment != null){
			this._attachment._totalTime = _totalTime;
		}
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
						Guardian.crud.removeSubprofileFromProfileId(this, c.getProfileId());
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
	
	public long getId(){
		return this._id;
	}
	
	protected void setId(long l){
		this._id = l;
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
		if(p != null){
			this._attachmentId = p.getAppId();
			this._attachment = p;
			this._attachment._totalTime = this._totalTime;
		} else {
			this._attachment = null;
			this._attachmentId = -1;
		}
	}
	
	public formFactor formType(){
		return formFactor.SubProfile;
	}
	
	public SubProfile copy(){
		return this;
	}
	
	public SubProfile save(SubProfile oldProfile){
		START:
		for(Child c : guard.Children()){
			for(SubProfile p : c.SubProfiles()){
				if(p._id == oldProfile._id){
					if(oldProfile.getAttachment() != null){
						this.setAttachment(oldProfile.getAttachment());
					}
					c.remove(p);
					c.save(this);
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
		Hourglass form = new Hourglass(this.name, this.desc, this.bgcolor, this.timeLeftColor, this.timeSpentColor, this.frameColor, this._totalTime, this.gradient);
		form.setId(this.getId());		
		if(this._attachment != null){
			form.setAttachment(this._attachment);
		}
		return form;
	}

	public SubProfile toProgressBar() {
		ProgressBar form = new ProgressBar(this.name, this.desc, this.bgcolor, this.timeLeftColor, this.timeSpentColor, this.frameColor, this._totalTime, this.gradient);
		form.setId(this.getId());
		if(this._attachment != null){
			form.setAttachment(this._attachment);
		}
		return form;
	}

	public SubProfile toTimeTimer() {
		TimeTimer form = new TimeTimer(this.name, this.desc, this.bgcolor, this.timeLeftColor, this.timeSpentColor, this.frameColor, this._totalTime, this.gradient);
		form.setId(this.getId());
		if(this._attachment != null){
			form.setAttachment(this._attachment);
		}
		return form;
	}

	public SubProfile toDigitalClock() {
		DigitalClock form = new DigitalClock(this.name, this.desc, this.bgcolor, this.timeLeftColor, this.timeSpentColor, this.frameColor, this._totalTime, this.gradient);
		form.setId(this.getId());
		if(this._attachment != null){
			form.setAttachment(this._attachment);
		}
		return form;
	}

	public long getDB_id() {
		return DB_id;
	}

	public void setDB_id(long dB_id) {
		if(this.DB_id == -1){
			this.DB_id = dB_id;
		}
	}
}
