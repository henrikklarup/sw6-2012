package dk.aau.cs.giraf.TimerLib;


public class ProgressBar extends SubProfile {
	
	public String _direction;
	
	public ProgressBar(String name, String description, int bgcolor, int timeLeftColor, int timeSpentColor, int frameColor, int totalTime, boolean changeColor){
		super(name, description, bgcolor, timeLeftColor, timeSpentColor, frameColor, totalTime, changeColor);
	}
	
	public ProgressBar(ProgressBar obj){
		super(obj.name, obj.desc,obj.bgcolor, obj.timeLeftColor,obj.timeSpentColor,obj.frameColor,obj.get_totalTime(),obj.gradient);
	}
	
	public ProgressBar copy(){
		ProgressBar copyP = new ProgressBar(this);
		
		Breakhere:
		for (Child c : guard.Children()) {
			for (SubProfile sp : c.SubProfiles()) {
				if(this.getId() == sp.getId()){
					copyP.setDB_id(c.getNewId());
					break Breakhere;
				}
			}
		}
		
		if(this._attachment != null){
			copyP.setAttachment(this._attachment);
		}
		return copyP;
	}
	
	public formFactor formType(){
		return formFactor.ProgressBar;
	}
}
