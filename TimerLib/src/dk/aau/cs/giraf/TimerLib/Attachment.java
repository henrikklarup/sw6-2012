package dk.aau.cs.giraf.TimerLib;

import java.util.HashMap;
/**
 * 
 * Attachment is a super class, and is used for attaching an extra timer, a pictogram next to a timer, or 
 * replacing the default pictogram on done screen
 *
 */
public class Attachment {
	
	/**
	 * Default constructor, only used internal in TimerLib
	 */
	Attachment(){
		
	}
	
	/**
	 * 
	 * @return this attachment
	 */
	public Attachment getAttachment(){
		return this;
	}
	
	/**
	 * super method which is only implemented so inherited class can use it.
	 * @return "undefined" formFactor
	 */
	public formFactor getForm(){
		return formFactor.undefined;
	}
	
	//May only be used on Timer
	/**
	 * Is only used on the Timer Attachment
	 * @param t The new time
	 */
	void changeTime(int t){
		
	}
	/**
	 * super method which is only implemented so inherited class can use it.
	 * @return null
	 */
	public Art getImg(){
		return null;
	}
	/**
	 * super method which is only implemented so inherited class can use it.
	 * @return null
	 */
	public Art getLeftImg(){
		return null;
	}
	/**
	 * super method which is only implemented so inherited class can use it.
	 * @return null
	 */
	public Art getRightImg(){
		return null;
	}
	/**
	 * super method which is only implemented so inherited class can use it.
	 * @return null
	 */
	public SubProfile genSub(){
		SubProfile asd = null;
		return asd;
	}
	/**
	 * super method which is only implemented so inherited class can use it.
	 * @return null
	 */
	public int getColor(){
		return 0;
	}
	/**
	 * super method which is only implemented so inherited class can use it.
	 * @return null
	 */
	public Attachment generateAttArt(HashMap map){
		Attachment att = null;
		
		return att;
	}
	/**
	 * Generates a hashmap with default information
	 * @return HashMap used to save in OasisLocalDatabase
	 */
	public HashMap getHashMap(HashMap map){
		//Defines what kind of attachment it is
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
		
		return map;
	}
}
