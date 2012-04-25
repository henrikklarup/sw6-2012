package dk.aau.cs.giraf.wombat;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import dk.aau.cs.giraf.TimerLib.CRUD;
import dk.aau.cs.giraf.TimerLib.Child;
import dk.aau.cs.giraf.TimerLib.Guardian;
import dk.aau.cs.giraf.TimerLib.SubProfile;
import dk.aau.cs.giraf.TimerLib.TimerHelper;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class TimerLoader {
	// Oasis Helper
	private static Helper oHelp;
	// Default Wombat IDs
	private static App m_App = null;
	private static Profile m_oGuard;
	private static Context m_context;
	
	public static CRUD crud;
	
	public static long appID;
	public static long guardianID;
	public static long profileID;
	
	public static int profilePosition;
	public static int subProfileID; 	// Stores the id of the saved subprofile
	public static boolean subProfileFirstClick;	// When a subprofile is saved, this is set to true
										// When something is clicked in the subprofile list, set to false
	public static boolean profileFirstClick;
	
	public static void load(Context c){
		Guardian guard = Guardian.getInstance();
		m_context = c;
		oHelp = new Helper(c);
		
		TimerHelper help = new TimerHelper();
		
		appID = findAppId();
		
		guardianID = findGuardianId();
		
		createChildren();
		
		crud = new CRUD(m_oGuard.getId(), m_App.getId(), m_context);
		help.loadPredef();
		guard.publishList();
	}

	private static void createChildren() {
		//TODO: Skal ændres til getChildrenbyGuardian i stedet for getprofiles
		if(oHelp.profilesHelper.getChildrenByGuardian(m_oGuard).isEmpty()){
			List<String> names = new ArrayList<String>();
			names.add("Sigurd");
			names.add("Marcus");
			names.add("Emil");
			names.add("Lukas");
			names.add("Mads");
			names.add("Nikolaj");
			
			for (String s : names) {
				Profile newProf = new Profile(s, " ", null, 3, 99999999, null, null);
				newProf.setId(oHelp.profilesHelper.insertProfile(newProf));
				oHelp.profilesHelper.attachChildToGuardian(newProf, m_oGuard);
			}
		}
	}

	/**
	 * Find the app specified by the mainActivity, if non is avalible a default one is created
	 * @return
	 */
	private static long findAppId() {

		// Find the app which has the same package name as this one
		for (App a : oHelp.appsHelper.getApps()) {
			String cname = m_context.getPackageName();
			if(a.getaPackage().equalsIgnoreCase(cname)){
				m_App = a;
				break;
			}
		}
			
		if(m_App == null){
			// If no app has been found, generate one and insert it in Oasis
			m_App = new App("Wombat", "1", "", m_context.getPackageName(), "MainActivity");
			m_App.setId(oHelp.appsHelper.insertApp(m_App));
		}
				
		return m_App.getId();
	}

	/**
	 * Search for the guard specified by the mainActivity, if non is existing a default one is created
	 * @return
	 */
	private static long findGuardianId() {
		
		if(guardianID != -1){
			// Does the original guard exist
			m_oGuard = oHelp.profilesHelper.getProfileById(guardianID);
		} else {
			m_oGuard = null;
		}
	
		// If not, try the default guard
		if(m_oGuard == null){
			for (Profile p : oHelp.profilesHelper.getProfiles()) {
				if(p.getFirstname().equals("John") && p.getSurname().equals("Doe")){
					m_oGuard = p;
				}
			}
			
			// If thats not valid either, make the default guard
			if(m_oGuard == null){
				m_oGuard = new Profile("John", "Doe", null, 1, 88888888, null, null);				
				m_oGuard.setId(oHelp.profilesHelper.insertProfile(m_oGuard));
			}
		} 
		
		return m_oGuard.getId();
	}

	/**
	 * Stores the subprofile on the guardian in Oasis
	 * @param sp
	 * 		The subprofile which is to be stored
	 * @return
	 * 		Returns true if it completed, else returns false
	 */
	public static void saveGuardian(SubProfile currSubP) {
		crud.saveGuardian(guardianID, currSubP);
	}	
	
	/**
	 * Stores the subprofile on the child in Oasis
	 * @param c
	 * 		The Child where the subprofile is supposed to be stored
	 * @param sp
	 * 		The subprofiel which is to be stored
	 * @return
	 * 		Returns true if it completed, else returns false
	 */
	public static void saveChild(Child c, SubProfile sp){
		crud.saveChild(c, sp);
	}
}
