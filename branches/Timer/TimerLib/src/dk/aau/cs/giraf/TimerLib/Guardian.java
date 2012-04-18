package dk.aau.cs.giraf.TimerLib;

import java.util.ArrayList;
import java.util.Collections;

public class Guardian {
	
	private static Guardian _instance = null;
	
	private ArrayList<Child> _guard = null;
	
	private static ArrayList<SubProfile> _lastUsed = null;
	
	private static ArrayList<SubProfile> _predef = null;
	
	private ArrayList<Child> _sortedList = null;
	
	private Child _selectedChild = null;
	
	private SubProfile _selectedSubProfile = null;
	
	public static String _name = null;
	
	private int id = -1;
	
	/**
	 * Default constructor for Guardian
	 */
	private Guardian(){	
	}
	
	/**
	 * Generate a new id
	 * @return id
	 */
	int getId(){
		id++;
		return id;
	}
	
	/**
	 * Singleton constructor for guardian.
	 * Always use this instead of the default constructor.
	 * 
	 * Example:
	 * Guardian guard = Guadian.getInstance();
	 * @return Guardian instance
	 */
	public static Guardian getInstance(){
		if(_instance == null){
			_instance = new Guardian();
		}
		return _instance;
	}
	
	/**
	 * ArrayList Children of Child object type
	 * @return ArrayList<Child>
	 */
	public ArrayList<Child> Children(){
		if(_guard == null){
		_guard = new ArrayList<Child>();
		}
		return _guard;
	}
	
	/**
	 * ArrayList lastUsed of SubProfile object type
	 * @return ArrayList<SubProfile> lastUsed
	 */
	private ArrayList<SubProfile> lastUsed(){
		if(_lastUsed == null){
		_lastUsed = new ArrayList<SubProfile>();
		}
		return _lastUsed;
	}
	
	/**
	 * Not in use atm
	 */
	private static void initLastUsedPredef(){
		if(_lastUsed == null){
			_lastUsed = new ArrayList<SubProfile>();
		}
		if(_predef == null){
			_predef = new ArrayList<SubProfile>();
		}
	}
	
	/**
	 * getChild is used to return a selected child
	 * @return selected child
	 */
	public Child getChild(){
		return _selectedChild;
	}
	
	/**
	 * Selects a specific child
	 * @param c The child you wish to select
	 */
	void setChild(Child c){
		_selectedChild = c;
	}
	/**
	 * getSubProfile is used to return a selected SubProfile
	 * @return selected subprofile
	 */
	public SubProfile getSubProfile(){
		return _selectedSubProfile;
	}
	/**
	 * Select a specific SubProfile
	 * @param p The SubProfile you wish to select
	 */
	void setSubProfile(SubProfile p){
		_selectedSubProfile = p;
	}
	/**
	 * Used to delete a specific SubProfile on a specific Child
	 * @param c Child who own the SubProfile
	 * @param p SubProfile you wish to delete
	 */
	void delete(Child c, SubProfile p){
		//Add admin delete
		c.SubProfiles().remove(p);
	}
	
	//Waiting for admin
	private void initLastUsed(ArrayList<SubProfile> profile){
		for(SubProfile p : profile){
			_lastUsed.add(p);
		}
	}
	
	private ArrayList reverse(ArrayList list){
		ArrayList value = new ArrayList();
		for(int i = list.size()-1; i > -1; i--){
			value.add(list.get(i));
		}
		return value;
	}
	
	/**
	 * Add a SubProfile to the list of lastUsed
	 * @param profile The SubProfile which you want to add to the lastUsed list
	 */
	public void addLastUsed(SubProfile profile){
		lastUsed();
		boolean exists = true;
		for(int i = 0; i < _lastUsed.size(); i++){
			//Checks if the SubProfile is already on the list.
			if(_lastUsed.get(i).getId() == profile.getId()){
				_lastUsed.remove(i);
				_lastUsed.add(profile);
				exists = false;
				break;
			}
		}
		
		if(exists){
			_lastUsed.add(profile);
		}
	}
	
	/**
	 * ArrayList of predefined of the SubProfile Object type
	 * @return ArrayList<SubProfile> predefined profiles
	 */
	ArrayList<SubProfile> predefined(){
		if(_predef == null){
		_predef = new ArrayList<SubProfile>();
		}
		return _predef;
	}
	/**
	 * This method is use to generate a list of lastUsed, predefined and all the children.
	 * ONLY use this method when you wish to generate a list of all the lists. Never use this list to execute methods on.
	 * @return A sorted list of lastUsed, predefined and all the children.
	 */
	public ArrayList<Child> publishList(){
		if(_sortedList == null){
			_sortedList = new ArrayList<Child>();
		}
		
		_sortedList.clear();
		Child lastUsedChild = new Child("Last Used");
		lastUsedChild.SubProfiles().addAll(reverse(lastUsed()));
		lastUsedChild.setLock();
		lastUsedChild.lockDelete();
		_sortedList.add(lastUsedChild);
		
		Child predefChild = new Child("Predefined Profiles");
		Collections.sort(predefined());
		predefChild.SubProfiles().addAll(predefined());
		predefChild.setLock();
		predefChild.lockDelete();
		_sortedList.add(predefChild);
		
		Collections.sort(_guard);
		for(Child p : _guard){
			Collections.sort(p.SubProfiles());
		}
		
		_sortedList.addAll(_guard);
		
		return _sortedList;
	}
	
	SubProfile findSubProfileByAppId(long id){
		SubProfile rP = null;
		ArrayList<Child> allLists = new ArrayList<Child>();
		allLists.addAll(Children());
		Child temp = new Child("predef");
		temp.SubProfiles().addAll(predefined());
		allLists.add(temp);
		for(Child c : allLists){
			for(SubProfile p : c.SubProfiles()){
				if(id == p.getAppId()){
					rP = p;
				}
			}
		}
		
		return rP;
	}


}