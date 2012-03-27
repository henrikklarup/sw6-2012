package sw6.autism.timer.TimerLib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import java.util.Collections;

public class Guardian {
	
	private static Guardian instance = null;
	
	private ArrayList<Child> guard = null;
	
	private Stack<Profile> lastUsed = null;
	
	private ArrayList<Profile> predef = null;
	
	private ArrayList<Child> sortedList = null;
	
	public String _name;
	
	private Guardian(String name){
		this._name = name;
	}
	
	private Guardian(){	
	}
	
	public static Guardian getInstance(String name){
		if(instance == null){
			instance = new Guardian(name);
		}
		return instance;
	}
	
	public static Guardian getInstance(){
		if(instance == null){
			instance = new Guardian();
		}
		return instance;
	}
	
	public ArrayList<Child> initChildren(){
		if(guard == null){
			guard = new ArrayList<Child>();
		}
		return guard;
	}
	
	public ArrayList<Child> Children(){
		if(guard == null){
		guard = new ArrayList<Child>();
		}
		return guard;
	}
	
	public Stack<Profile> lastUsed(){
		if(lastUsed == null){
		lastUsed = new Stack<Profile>();
		}
		return lastUsed;
	}
	
	
	//Waiting for admin
	private void initLastUsed(ArrayList<Profile> profile){
		for(Profile p : profile){
			lastUsed.add(p);
		}
	}
	
	public void addLastUsed(Profile profile){
		if(lastUsed.contains(profile)){
			lastUsed.push(profile);
		} else {
			lastUsed.add(profile);
		}
	}
	
	public ArrayList<Profile> predefined(){
		if(predef == null){
		predef = new ArrayList<Profile>();
		}
		return predef;
	}
	
	public ArrayList<Child> publishList(){
		if(sortedList == null){
			sortedList = new ArrayList<Child>();
		}
		Child lastUsedChild = new Child("Last Used");
		lastUsedChild.Profiles().addAll(lastUsed);
		
		sortedList.add(lastUsedChild);
		
		Child predefChild = new Child("Predefined Profiles");
		predefChild.Profiles().addAll(predef);
		
		sortedList.add(predefChild);
		
		
		
		return sortedList;
	}


}