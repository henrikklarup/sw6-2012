package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

public class CRUD {
	Context context;
	Helper helper;
	Guardian guard = Guardian.getInstance();
	private long appId; 
	
	public CRUD(long guardianID, long appID, Context context){
		this.context = context;
		helper = new Helper(context);
		this.appId = appID;
		// Load the guardian form Oasis
		Profile mGuardian = helper.profilesHelper.getProfileById(guardianID);
		
		// Find all subprofiles of the child and save it on the child
		List<SubProfile> mGuardSubPs = findProfileSettings(mGuardian.getId());
		guard.clearLastUsed();
		for (SubProfile subProfile : mGuardSubPs) {
			guard.addLastUsed(subProfile);
		}
		
		// Load the children from Oasis of the guardian
		List<Profile> mChildren = helper.profilesHelper.getChildrenByGuardian(mGuardian);
		guard.Children().clear();
		
		for (Profile c : mChildren) {
			Child mC;
			String mName;
			// Generate the name of the child
			mName = c.getFirstname();
			
			if(c.getMiddlename() != null){
				String[] midNames = c.getMiddlename().split(" ");
				for (String s : midNames) {
					mName += " " + s.charAt(0) + ".";
				}
			}
			mName += " " + c.getSurname();
			mC = new Child(mName);
			mC.setProfileId(c.getId());
		
			// Find all subprofiles of the child and save it on the child
			List<SubProfile> mSubPs = findProfileSettings(c.getId());
			for (SubProfile subProfile : mSubPs) {
				mC.SubProfiles().add(subProfile);
			}
			
			guard.Children().add(mC);
		}
	}
	
	/**
	 * Stores the subprofile on the child in Oasis
	 * @param c
	 * 		The Child where the subprofile is supposed to be stored
	 * @param sp
	 * 		The subprofile which is to be stored
	 * @return
	 * 		Returns true if it completed, else returns false
	 */
	public boolean saveChild(Child c, SubProfile sp){
		// Convert the subprofile to a hashmap
		HashMap<String, String> hm = setHashMap(sp);
		
		// Find the app settings on the profileID
		App app = helper.appsHelper.getAppByIds(appId, c.getProfileId());
		Setting<String, String, String> settings = app.getSettings();
		if(settings == null){
			settings = new Setting<String, String, String>();
		}
		// Insert the hashmap with the subprofile ID as key
		settings.put(String.valueOf(sp.getId()), hm);
		app.setSettings(settings);
		Profile newProf = helper.profilesHelper.getProfileById(c.getProfileId());
		helper.appsHelper.modifyAppByProfile(app, newProf);
		
		return true;	
	}
	
	/**
	 * Stores the subprofile on the guardian in Oasis
	 * @param guardianId
	 * 		The guardian ID found in the TimerLoader.guardianID
	 * @param sp
	 * 		The subprofile which is to be stored
	 * @return
	 * 		Returns true if it completed, else returns false
	 */
	public boolean saveGuardian(long guardianId, SubProfile sp){
		// Convert the subprofile to a hashmap
		HashMap<String, String> hm = setHashMap(sp);
		
		// Find the app settings on the profileID
		App app = helper.appsHelper.getAppByIds(appId, guardianId);
		Setting<String, String, String> settings = app.getSettings();
		if(settings == null){
			settings = new Setting<String, String, String>();
		}
		
		// Insert the hashmap with the subprofile ID as key
		settings.put(String.valueOf(sp.getId()), hm);
		app.setSettings(settings);
		Profile newProf = helper.profilesHelper.getProfileById(guardianId);
		helper.appsHelper.modifyAppByProfile(app, newProf);
		
		return true;	
	}
	
	
	/**
	 * Loads all subprofiles on the specific ID
	 * @param id
	 * 		The id of the profile holding subprofiles
	 * @return
	 * 		A list of subprofiles extracted from the settings
	 */
	private List<SubProfile> findProfileSettings(long id) {
		List<SubProfile> mSubs = new ArrayList<SubProfile>();
		
		App app = helper.appsHelper.getAppByIds(appId, id);
		if(app.getSettings() != null){
			Setting<String, String, String> settings = app.getSettings();
			Set<String> keys = settings.keySet();
	
			for (String key : keys) {
				SubProfile sub = getSubProfile(settings.get(key));
				mSubs.add(sub);
			}	
		}
		
		return mSubs;
	}

	/**
	 * Loads a subprofile from the hashmap
	 * @param hm
	 * 		The hashmap which holds the subprofile
	 * @return
	 * 		The subprofile extracted from the hashmap
	 */
	private SubProfile getSubProfile(HashMap<String, String> hm){		
		SubProfile p = new SubProfile();
		/* Load all settings from the hash table */
		p.setAttachmentId(Long.valueOf((String) hm.get("Attachment")));
		p.name = String.valueOf(hm.get("Name"));
		p.desc = String.valueOf(hm.get("desc"));	
		p.bgcolor = Integer.valueOf((String)hm.get("bgcolor"));
		p.timeLeftColor = Integer.valueOf((String)hm.get("timeLeftColor"));
		p.timeSpentColor = Integer.valueOf((String)hm.get("timeSpentColor"));
		p.frameColor = Integer.valueOf((String)hm.get("frameColor"));
		p.set_totalTime(Integer.valueOf((String)hm.get("totalTime")));
		p.gradient = Boolean.valueOf((String)hm.get("gradient"));
		p.save = Boolean.valueOf((String)hm.get("save"));
		p.saveAs = Boolean.valueOf((String)hm.get("saveAs"));
		

		/* Change the subprofile to the correct type */
		switch (formFactor.convert(hm.get("type"))) {
		case Hourglass:
			p = new Hourglass(p.name, p.desc, p.bgcolor, p.timeLeftColor, p.timeSpentColor, p.frameColor, p.get_totalTime(), p.gradient);
			break;
		case TimeTimer:
			p = new TimeTimer(p.name, p.desc, p.bgcolor, p.timeLeftColor, p.timeSpentColor, p.frameColor, p.get_totalTime(), p.gradient);
			break;
		case ProgressBar:
			p = new ProgressBar(p.name, p.desc, p.bgcolor, p.timeLeftColor, p.timeSpentColor, p.frameColor, p.get_totalTime(), p.gradient);
			break;
		case DigitalClock:
			p = new DigitalClock(p.name, p.desc, p.bgcolor, p.timeLeftColor, p.timeSpentColor, p.frameColor, p.get_totalTime(), p.gradient);
			break;
		default:
			p = new SubProfile();
			break;
		}

		return p;
	}
	
	/**
	 * Generates a hashmap with settings of the subprofile
	 * @param p
	 * 		The subprofile which is supposed to be stored to the hashmap
	 * @return
	 * 		A hashmap with the settings stored in
	 */
	private HashMap<String, String> setHashMap(SubProfile p){
		
		HashMap<String, String> map = new HashMap<String, String>();		
		map.put("type", p.formType().toString());		
		map.put("Attachment", String.valueOf(p.getAttachmentId()));		
		map.put("Name", p.name);		
		map.put("desc", p.desc);		
		map.put("bgcolor", String.valueOf(p.bgcolor));		
		map.put("timeLeftColor", String.valueOf(p.timeLeftColor));		
		map.put("timeSpentColor", String.valueOf(p.timeSpentColor));		
		map.put("frameColor", String.valueOf(p.frameColor));
		map.put("totalTime", String.valueOf(p.get_totalTime()));		
		map.put("gradient", String.valueOf(p.gradient));		
		map.put("save", String.valueOf(p.save));		
		map.put("saveAs", String.valueOf(p.saveAs));
		
		return map;
	}

}
