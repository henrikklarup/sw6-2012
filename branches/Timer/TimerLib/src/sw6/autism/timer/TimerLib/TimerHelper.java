package sw6.autism.timer.TimerLib;

import java.util.ArrayList;

public class TimerHelper {
	
	public void init()
	{
		//Admin call
		Guardian guard = Guardian.getInstance();
		populateList();
	}
	
	public void populateList(){
		
	}
	
	public ArrayList<Child> getFullList(){
		Guardian guard = Guardian.getInstance();
		return guard.Children();
	}
	
	public Child getProfileList(int id){
		Guardian guard = Guardian.getInstance();
		return guard.Children().get(id);
	}

}
