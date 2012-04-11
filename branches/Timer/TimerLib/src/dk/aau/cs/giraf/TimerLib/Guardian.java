package dk.aau.cs.giraf.TimerLib;

import dk.aau.cs.giraf.TimerLib.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import java.util.Collections;

public class Guardian {
	
	private static Guardian instance = null;
	
	private ArrayList<Child> guard = null;
	
	private static ArrayList<SubProfile> lastUsed = null;
	
	private static ArrayList<SubProfile> predef = null;
	
	private ArrayList<Child> sortedList = null;
	
	private Child selectedChild = null;
	
	public static String _name = null;
	
	private int id = -1;
	
	TimerHelper help = new TimerHelper();

	
	private Guardian(){	
	}
	
	public static Guardian getInstance(String name){
		if(instance == null){
			instance = new Guardian();
		}
		if(_name == null){
			_name = name;
		}
		
		initLastUsedPredef();
		return instance;
	}
	
	public int getId(){
		id++;
		return id;
	}
	
	public static Guardian getInstance(){
		if(instance == null){
			instance = new Guardian();
		}
		return instance;
	}
	
	public ArrayList<Child> Children(){
		if(guard == null){
		guard = new ArrayList<Child>();
		}
		return guard;
	}
	
	public ArrayList<SubProfile> lastUsed(){
		if(lastUsed == null){
		lastUsed = new ArrayList<SubProfile>();
		}
		return lastUsed;
	}
	
	public static void initLastUsedPredef(){
		if(lastUsed == null){
			lastUsed = new ArrayList<SubProfile>();
		}
		if(predef == null){
			predef = new ArrayList<SubProfile>();
		}
	}
	
	public Child selected(){
		return selectedChild;
	}
	
	public void setSelected(Child c){
		selectedChild = c;
	}
	
	//Waiting for admin
	private void initLastUsed(ArrayList<SubProfile> profile){
		for(SubProfile p : profile){
			lastUsed.add(p);
		}
	}
	
	private ArrayList reverse(ArrayList list){
		ArrayList value = new ArrayList();
		for(int i = list.size()-1; i > -1; i--){
			value.add(list.get(i));
		}
		return value;
	}
	
	public void addLastUsed(SubProfile profile){
		lastUsed();
		
		boolean exists = true;
		for(int i = 0; i < lastUsed.size(); i++){
			if(lastUsed.get(i).getId() == profile.getId()){
				lastUsed.remove(i);
				lastUsed.add(profile);
				exists = false;
				break;
			}
		}
		
		if(exists){
			lastUsed.add(profile);
		}
	}
	
	public ArrayList<SubProfile> predefined(){
		if(predef == null){
		predef = new ArrayList<SubProfile>();
		}
		return predef;
	}
	
	public ArrayList<Child> publishList(){
		if(sortedList == null){
			sortedList = new ArrayList<Child>();
		}
		
		sortedList.clear();
		Child lastUsedChild = new Child("Last Used");
		lastUsedChild.SubProfiles().addAll(reverse(lastUsed()));
		
		sortedList.add(lastUsedChild);
		
		Child predefChild = new Child("Predefined Profiles");
		help.loadPredef();
		Collections.sort(predefined());
		predefChild.SubProfiles().addAll(predefined());
		
		sortedList.add(predefChild);
		
		Collections.sort(guard);
		for(Child p : guard){
			Collections.sort(p.SubProfiles());
		}
		
		sortedList.addAll(guard);
		
		return sortedList;
	}


}