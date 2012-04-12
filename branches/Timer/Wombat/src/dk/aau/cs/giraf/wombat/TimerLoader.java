package dk.aau.cs.giraf.wombat;


import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.TimerHelper;

public class TimerLoader {
	
	public static void load(){
		Guardian guard = Guardian.getInstance("John");
		TimerHelper help = new TimerHelper();
		help.LoadTestData();
		help.loadPredef();
		guard.publishList();
	}
	
	public static int profilePosition;
}
