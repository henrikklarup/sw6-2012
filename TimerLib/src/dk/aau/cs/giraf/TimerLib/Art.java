package dk.aau.cs.giraf.TimerLib;

public class Art {
	
	private int _path;
	private String _caption;
	private int _id = -1;
	Guardian guard = Guardian.getInstance();
	/**
	 * Art constructor
	 * @param path Resource path to the pictogram (R class)
	 * @param caption Caption associated with the pictogram
	 * @param id Id for selected a certain art object. The id need to be the same as its position in the ArtList in the Guardian class
	 * @see Guardian#getInstance
	 */
	public Art(int path, String caption, int id){
		this._path = path;
		this._caption = caption;
		this._id = id;
	}
	/**
	 * 
	 * @return Art caption string
	 */
	public String getName(){
		return this._caption;
	}
	/**
	 * 
	 * @return "undefined" formFactor
	 */
	public formFactor getForm(){
		return formFactor.undefined;
	}
	
	/**
	 * 
	 * @return Resource class path to the pictogram
	 */
	public int getPath(){
		return this._path;
	}
	
	/**
	 * Used to retrieve a specific art object
	 * @return Id
	 */
	int getId(){
		return this._id;
	}
}
