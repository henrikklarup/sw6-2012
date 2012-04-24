package dk.aau.cs.giraf.wombat;


import dk.aau.cs.giraf.TimerLib.CRUD;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.TimerHelper;

public class TimerLoader {
	
	public static void load(){
		Guardian guard = Guardian.getInstance();
		crud = new CRUD(guardianID, appID);
		TimerHelper help = new TimerHelper();
		help.loadPredef();
		guard.publishList();
	}
	
	public static CRUD crud;
	
	public static long appID;
	public static long guardianID;
	public static long profileID;
	
	public static int profilePosition;
	public static int subProfileID; 	// Stores the id of the saved subprofile
	public static boolean subProfileFirstClick;	// When a subprofile is saved, this is set to true
										// When something is clicked in the subprofile list, set to false
	public static boolean profileFirstClick;
}
