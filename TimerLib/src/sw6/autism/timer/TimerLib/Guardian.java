package sw6.autism.timer.TimerLib;

import java.util.ArrayList;

public class Guardian {
	
	private static Guardian instance = null;
	
	private static ArrayList<Child> guard = null;
	
	public Guardian(){
	}
	
	public static Guardian getInstance(){
		if(instance == null){
			instance = new Guardian();
		}
		return instance;
	}
	
	public static ArrayList<Child> init(){
		if(guard == null){
			guard = new ArrayList<Child>();
		}
		return guard;
	}
	
	public ArrayList<Child> Children(){
		return guard;
	}

	
	

}
