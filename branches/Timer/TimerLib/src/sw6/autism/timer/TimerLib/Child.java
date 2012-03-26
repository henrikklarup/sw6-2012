package sw6.autism.timer.TimerLib;

import java.util.ArrayList;

public class Child extends Guardian{
	private ArrayList<Profile> profileList;
	
	public Child(){
		profileList = new ArrayList<Profile>();
	}
	
	public ArrayList<Profile> Profiles(){
		return profileList;
	}
	
}
