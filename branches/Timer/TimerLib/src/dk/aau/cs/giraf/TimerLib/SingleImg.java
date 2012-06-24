package dk.aau.cs.giraf.TimerLib;

import java.util.HashMap;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;
/**
 * 
 * This class represent one pictogram as an attachment
 * 
 * Layer: Tools
 *
 */
public class SingleImg extends Attachment {
	
	Guardian guard = Guardian.getInstance();
	Helper help = new Helper(guard.m_context);
	
	private Art _art = null;
	/**
	 * Sets an Art object as the pictogram
	 * @param art The art object you which to use as pictogram.
	 */
	public SingleImg(Art art){
		this._art = art;
	}
	/**
	 * returns this attachment
	 */
	public Attachment getAttachment(){
		return this;
	}
	/**
	 * returns formFactor which represent SingleImg
	 */
	public formFactor getForm(){
		return formFactor.SingleImg;
	}
	/**
	 * @return the art object
	 */
	public Art getImg(){
		return this._art;
	}
	/**
	 * Generate a hashmap which represent a SingleImg
	 * @return HashMap used for the OasisLocalDatabase
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
		map.put("_gradient", String.valueOf(-1));
		
		//SingleImg
		map.put("singleImgId", String.valueOf(this._art.getId()));
		
		//SplitImg
		map.put("leftImgId", String.valueOf(-1));
		map.put("rightImgId", String.valueOf(-1));
		
		return map;
	}

}
