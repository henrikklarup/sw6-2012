package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

public class CRUD {
	Context context;
	Helper oHelp;
	Guardian guard = Guardian.getInstance();
	private long appId; 
	private String _lastUsed = "lastUsed";
	private String _subprofile = "SUBPROFILE";
	private ArrayList<String> lastUsedList;
	
	public CRUD(long appID, Context context){
		this.context = context;
		oHelp = new Helper(context);
		this.appId = appID;
	}
	
	void removeLastUsed(Child c, SubProfile p, long profileId){
		// Find the profile by Id
		Profile prof = oHelp.profilesHelper.getProfileById(profileId);
		
		// Find the Wombat App
		App app = oHelp.appsHelper.getAppByIds(appId, profileId);
		
		// Get the settings from the profile and update
		Setting<String, String, String> settings = app.getSettings();
		
		settings.get(_lastUsed).remove(String.valueOf(c.getProfileId() + ";" + p.getDB_id()));
		app.setSettings(settings);
		
		// Update the app
		oHelp.appsHelper.modifyAppByProfile(app, prof);
	}
	
	void addLastUsed(Child c, SubProfile p, long profileId){
		// Find the profile by Id
		Profile prof = oHelp.profilesHelper.getProfileById(profileId);
			
		// Find the Wombat App
		App app = oHelp.appsHelper.getAppByIds(appId, profileId);
				
		// Get the settings from the profile and update
		Setting<String, String, String> settings = app.getSettings();
		Calendar cal = Calendar.getInstance();
		String sec = String.valueOf(cal.get(Calendar.SECOND));
		String min = String.valueOf(cal.get(Calendar.MINUTE));
		String hour = String.valueOf(cal.get(Calendar.SECOND));
		String day = String.valueOf(cal.get(Calendar.DAY_OF_YEAR));
		String year = String.valueOf(cal.get(Calendar.YEAR));
		
		String timeKey = year+""+day+""+hour+""+min+""+sec;
		
		settings.get(_lastUsed).put(String.valueOf(c.getProfileId() + ";" + p.getDB_id()),timeKey);
		app.setSettings(settings);
		
		// Update the app
		oHelp.appsHelper.modifyAppByProfile(app, prof);
		
	}
	
	void retrieveLastUsed(long profileId){
		// Find the guardian by Id
		Profile prof = oHelp.profilesHelper.getProfileById(profileId);
			
		//retrieve all last used :D:D:
		App app = oHelp.appsHelper.getAppByIds(appId, profileId);
		
		if(app.getSettings() != null){
			lastUsedList = new ArrayList<String>();
			Setting<String, String, String> settings = app.getSettings();
			
			Set<Entry<String, String>> keys = settings.get(_lastUsed).entrySet();
	
			for (Map.Entry<String, String> key : keys) {
				initLastUsed(key.getKey(), key.getValue());
			}
			
		} else {
			Setting<String,String,String> newSet = new Setting<String,String,String>();
			newSet.put(_lastUsed, null);
			app.setSettings(newSet);
			oHelp.appsHelper.modifyAppByProfile(app, prof);
		}
	}
	
	void initLastUsed(String key, String value){
		
	}
	
	//Waiting for admin, we can insert media but not retrieve them :D
//	public void LoadPictures(){
//		MediaHelper mp = new MediaHelper(guard.m_context);
//		
//		Media m = mp.getMediaById(id)
//	}
	
	public void loadGuardian(long guardianID){
		// Load the guardian form Oasis
		Profile mGuardian = oHelp.profilesHelper.getProfileById(guardianID);
		
		// Find all subprofiles of the child and save it on the child
		ArrayList<SubProfile> mGuardSubPs = findProfileSettings(mGuardian.getId());
		
		// Load the children from Oasis of the guardian
		List<Profile> mChildren = oHelp.profilesHelper.getChildrenByGuardian(mGuardian);
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
			
			// Get the biggest ID from the database
			long id = -1;
			App app = oHelp.appsHelper.getAppByIds(appId, c.getId());
			if(app.getSettings() != null){
				Setting<String, String, String> settings = app.getSettings();
				Set<String> keys = settings.keySet();
		
				for (String key : keys) {
					if(id < Long.valueOf(key)){
						id = Long.valueOf(key);
					}					
				}	
			}			
			mC.setSubProfileId(id);
			
			guard.Children().add(mC);
		}
		
		//Init lastUsed skal laves 
		//guard.clearLastUsed();
		//guard.initLastUsed(mGuardSubPs);
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
		HashMap<String, String> hm = sp.getHashMap();
		
		// Find the app settings on the profileID
		App app = oHelp.appsHelper.getAppByIds(appId, c.getProfileId());
		Setting<String, String, String> settings = app.getSettings();
		if(settings == null){
			settings = new Setting<String, String, String>();
		}
		// Insert the hashmap with the subprofile ID as key
		settings.put(String.valueOf(sp.getDB_id()), hm);
		app.setSettings(settings);
		Profile newProf = oHelp.profilesHelper.getProfileById(c.getProfileId());
		oHelp.appsHelper.modifyAppByProfile(app, newProf);
		
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
		HashMap<String, String> hm = sp.getHashMap();
		
		// Find the app settings on the profileID
		App app = oHelp.appsHelper.getAppByIds(appId, guardianId);
		Setting<String, String, String> settings = app.getSettings();
		if(settings == null){
			settings = new Setting<String, String, String>();
		}
		
		// Insert the hashmap with the subprofile ID as key
		settings.put(String.valueOf(sp.getDB_id()), hm);
		app.setSettings(settings);
		Profile newProf = oHelp.profilesHelper.getProfileById(guardianId);
		oHelp.appsHelper.modifyAppByProfile(app, newProf);
		
		return true;	
	}
	
	
	/**
	 * Loads all subprofiles on the specific ID
	 * @param id
	 * 		The id of the profile holding subprofiles
	 * @return
	 * 		A list of subprofiles extracted from the settings
	 */
	private ArrayList<SubProfile> findProfileSettings(long id) {
		ArrayList<SubProfile> mSubs = new ArrayList<SubProfile>();
		
		App app = oHelp.appsHelper.getAppByIds(appId, id);
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
		p.name = String.valueOf(hm.get("Name"));
		p.desc = String.valueOf(hm.get("desc"));	
		p.bgcolor = Integer.valueOf((String)hm.get("bgcolor"));
		p.timeLeftColor = Integer.valueOf((String)hm.get("timeLeftColor"));
		p.timeSpentColor = Integer.valueOf((String)hm.get("timeSpentColor"));
		p.frameColor = Integer.valueOf((String)hm.get("frameColor"));
		p.set_totalTime(Integer.valueOf((String)hm.get("totalTime")));
		p.gradient = Boolean.valueOf((String)hm.get("gradient"));
		formFactor factor = formFactor.convert(hm.get("type"));

		/* Change the subprofile to the correct type */
		p = convertType(p,factor);
		
		p.setDB_id(Long.valueOf(hm.get("db_id")));
		p.save = Boolean.valueOf((String)hm.get("save"));
		p.saveAs = Boolean.valueOf((String)hm.get("saveAs"));
		
		p._AttaBool = (Boolean.valueOf((String) hm.get("Attachment")));
		
		if(p._AttaBool){
			//Attachment attachP = new Attachment();
			formFactor aFactor = formFactor.convert(hm.get("AttachmentForm"));
			formFactor tFactor = formFactor.convert(hm.get("timerForm"));
			
			//Add method to Attachment.java that will return the correct processed object based on the HashMap :D
			//attachP.
			
			/*
			//Attachment form
			map.put("AttachmentForm", String.valueOf(this.getForm()));
		
			//Timer
			map.put("timerForm", String.valueOf(this.getForm()));
			map.put("_bgColor", String.valueOf(-1));
			map.put("_frameColor", String.valueOf(-1));
			map.put("_timeLeftColor", String.valueOf(-1));
			map.put("_timeSpentColor", String.valueOf(-1));
			map.put("_gradient", String.valueOf(false));
			
			//SingleImg
			map.put("singleImgId", String.valueOf(-1));
			
			//SplitImg
			map.put("leftImgId", String.valueOf(-1));
			map.put("rightImgId", String.valueOf(-1));
			 */
			
//			attachP = convertType(attachP, aFactor);
//			
//			p.setAttachment(attachP);
			
		}	
		
		return p;
	}
	
	SubProfile convertType(SubProfile p, formFactor factor){
		switch (factor) {
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
	 * Remove the specific subprofile from the profile (Child/Guardian)
	 * @param p
	 * 		The subprofile which is supposed to be removed
	 * @param profileId
	 * 		The profile which the subprofile is going to be removed from
	 */
	public void removeSubprofileFromProfileId(SubProfile p, long profileId) {
		// Find the profile by Id
		Profile prof = oHelp.profilesHelper.getProfileById(profileId);
		
		// Find the Wombat App
		App app = oHelp.appsHelper.getAppByIds(appId, profileId);
		
		// Get the settings from the profile and update
		Setting<String, String, String> settings = app.getSettings();
		settings.remove(String.valueOf(p.getDB_id()));
		app.setSettings(settings);
		
		// Update the app
		oHelp.appsHelper.modifyAppByProfile(app, prof);
		
	}

}
