package sw6.autism.timer.TimerLib;

import java.util.ArrayList;

public class Child{
	private ArrayList<Profile> profileList;
	
	public String _name;
	public Child(String name){
		profileList = new ArrayList<Profile>();
		this._name = name;
	}
	
	public ArrayList<Profile> Profiles(){
		return profileList;
	}
	
}
