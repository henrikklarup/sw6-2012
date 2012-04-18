package dk.aau.cs.giraf.TimerLib;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

public class CRUD {
	private String _mainHashKey = "WOMBATHASHKEY";
	private String _lastUsedKey = "WOMBATLASTUSEDKEY";
	Context p;
	Helper helper = new Helper(p);
	Guardian guard = Guardian.getInstance();
	
	void create(SubProfile p , Child c){
		
	}
	
	void retrieve(long guardianId){
		Profile guardian = helper.profilesHelper.getProfileById(guardianId);

		List<Profile> children = helper.profilesHelper.getChildrenByGuardian(guardian);
		
		for(Profile c : children){
			List<App> cApp = helper.appsHelper.getAppsByProfile(c);
			Child child = new Child(c.getFirstname());
			for(App a : cApp){
				child.SubProfiles().add(getSubProfile(a));
			}
			guard.Children().add(child);
		}
		lastUsed(guardian);
	}
	
	void update(SubProfile p){
		save(p);
	}
	
	void delete(SubProfile p){
		App subP = helper.appsHelper.getAppById(p.getAppId());
		subP.getSettings().remove(_mainHashKey);
		
		helper.appsHelper.modifyApp(subP);
	} 
	
	private HashMap lastUsed(Profile guardian){
		HashMap lastUsed = null;
		List<App> listOfLastUsed = helper.appsHelper.getAppsByProfile(guardian);
		boolean noLastUsed = true;
		
		//Check if there already exists an lastused app setting on guardian
		for(App a: listOfLastUsed){
			if(a.getSettings().containsKey(_lastUsedKey)){
				lastUsed = a.getSettings().get(_lastUsedKey);
				noLastUsed = false;
			}
		}
		
		if(noLastUsed){
			App newLastUsed = new App();
			Setting settings = new Setting<String, String, String>();
			HashMap map = new HashMap();
			settings.put(_lastUsedKey, map);
			newLastUsed.setSettings(settings);
			helper.appsHelper.attachAppToProfile(newLastUsed, guardian);
			lastUsed(guardian);
		}
		//Add profiles to LastUsed

		for(Object asd : lastUsed.keySet()){
			Object value = lastUsed.get(asd);
			String attacment = String.valueOf(value);
			String[] att = attacment.split(";");
			SubProfile setA = null;
//KÆFT DET HER ER NOGET LORTE KODE :D:D:D:D::D:D:D:D GG ADMIN
		}
		
		HashMap asd = new HashMap();
		return asd;
	}
	
	private void save(SubProfile p){
		App subP = helper.appsHelper.getAppById(p.getAppId());
		subP.getSettings().remove(_mainHashKey);

		Setting<String,String,String> set = new Setting<String,String,String>();
		set.put(_mainHashKey, setHashMap(p, subP.getId()));
		subP.setSettings(set);
		
		helper.appsHelper.modifyApp(subP);
	}
	
	private SubProfile getSubProfile(App a){
		
		HashMap subHash = a.getSettings().get(_mainHashKey);
		
		long appId = Long.valueOf((String) subHash.get("appId"));
		formFactor type = formFactor.convert(subHash.get("type"));
		long attachmentId = Long.valueOf((String) subHash.get("Attachment"));
		String name = String.valueOf(subHash.get("Name"));
		String desc = String.valueOf(subHash.get("desc"));	
		int bgcolor = Integer.valueOf((String)subHash.get("bgcolor"));
		int timeLeftColor = Integer.valueOf((String)subHash.get("timeLeftColor"));
		int timeSpentColor = Integer.valueOf((String)subHash.get("timeSpentColor"));
		int frameColor = Integer.valueOf((String)subHash.get("frameColor"));
		int totalTime = Integer.valueOf((String)subHash.get("totalTime"));
		boolean gradient = Boolean.valueOf((String)subHash.get("gradient"));
		boolean save = Boolean.valueOf((String)subHash.get("save"));
		boolean saveAs = Boolean.valueOf((String)subHash.get("saveAs"));
		
		SubProfile rProfile = null;
		
		if(type == formFactor.Hourglass){
			rProfile = new Hourglass(name,desc,bgcolor,timeLeftColor,timeSpentColor,frameColor, totalTime,gradient);
		} else if(type == formFactor.TimeTimer){
			rProfile = new TimeTimer(name,desc,bgcolor,timeLeftColor,timeSpentColor,frameColor, totalTime,gradient);
		} else if(type == formFactor.ProgressBar){
			rProfile = new ProgressBar(name,desc,bgcolor,timeLeftColor,timeSpentColor,frameColor, totalTime,gradient);
		} else if(type == formFactor.DigitalClock){
			rProfile = new DigitalClock(name,desc,bgcolor,timeLeftColor,timeSpentColor,frameColor, totalTime,gradient);
		} else {
			rProfile = new SubProfile(name,desc,bgcolor,timeLeftColor,timeSpentColor,frameColor, totalTime,gradient);
		}
		
		rProfile.setAppId(appId);
		rProfile.setAttachmentId(attachmentId);
		rProfile.save = save;
		rProfile.saveAs = saveAs;

		return rProfile;
	}
	
	private HashMap setHashMap(SubProfile p, long appId){
		
		HashMap map = new HashMap();
		
		map.put("appId", String.valueOf(appId));
		
		map.put("type", p.formType().toString());
		
		map.put("Attachment", String.valueOf(p.getAttachmentId()));
		
		map.put("Name", p.name);
		
		map.put("desc", p.desc);
		
		map.put("bgcolor", String.valueOf(p.bgcolor));
		
		map.put("timeLeftColor", String.valueOf(p.timeLeftColor));
		
		map.put("timeSpentColor", String.valueOf(p.timeSpentColor));
		
		map.put("frameColor", String.valueOf(p.frameColor));
		
		map.put("totalTime", String.valueOf(p.totalTime));
		
		map.put("gradient", String.valueOf(p.gradient));
		
		map.put("save", String.valueOf(p.save));
		
		map.put("saveAs", String.valueOf(p.saveAs));

		return map;
	}
	
	void test(){
		
		Setting settings = new Setting<String, String, String>();
		
		Setting settings1 = new Setting<String, String, String>();

		HashMap map = new HashMap();
		map.put("Settings", "Favorite Color,Favorite Food,Favorite Animal");
		map.put("Favorite Color", "Blue");
		map.put("Favorite Food", "Carrot");
		map.put("Favorite Animal", "Rabbit");
		settings.put("asdas", map);
		
		HashMap map1 = new HashMap();
		map1.put("Settings", "Favorite Color,Favorite Food,Favorite Animal");
		map1.put("Favorite Color", "Blue");
		map1.put("Favorite Food", "Carrot");
		map1.put("Favorite Animal", "Rabbit");
		settings1.put("supbro", map1);
		

		
		Profile profile = new Profile("FEDTE", "FEDTE","FEDTE", 0, 12345678, "FEDTE", null);
		
		
		App app = new App();
		
		app.setSettings(settings);
		
		App app1 = new App();
		app1.setSettings(settings1);
		
	
		
		
		helper.appsHelper.attachAppToProfile(app, profile);
		helper.appsHelper.attachAppToProfile(app1, profile);
		
		List<App> asd = helper.appsHelper.getAppsByProfile(profile);
		asd.get(0).getId();
		asd.get(0).getSettings().get("asdas").remove("Favore cOlor");
		asd.get(0).getSettings().get("asdad").put("asd", "sad");
		
		
		
		helper.profilesHelper.insertProfile(profile);
		
		List<Profile>values = helper.profilesHelper.getProfiles();
	}

}
