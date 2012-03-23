package sw6.oasis.viewmodels;

public class Media {

	private long id;
	private String path;
	private String name;
	private boolean _public;
	private String type;
	private String tags;
	private long ownerId;
	private String output;
	
	/**
	 * Constructor with arguments
	 * 
	 * @param name the name to set
	 * @param path the path to set
	 * @param _public the _public to set
	 * @param type the type to set
	 * @param tags the tags to set
	 * @param ownerId the ownerID to set
	 */
	public Media(String name, String path, boolean _public, String type, String tags, long ownerId) {
		this.name = name;
		this.path = path;
		this._public = _public;
		this.type = type;
		this.tags = tags;
		this.ownerId = ownerId;
	}
	
	/**
	 * Empty constructor
	 */
	public Media() {
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the _public
	 */
	public boolean is_public() {
		return _public;
	}
	/**
	 * @param _public the _public to set
	 */
	public void set_public(boolean _public) {
		this._public = _public;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * @return the ownerId
	 */
	public long getOwnerId() {
		return ownerId;
	}
	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	/**
	 *  Set output
	 *  {0} = ID
	 * 	{1} = Path
	 *  {2} = Name
	 *  {3} = Public
	 *  {4} = Type
	 *  {5} = Tags
	 *  {6} = OwnerId
	 * @param output
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	
	@Override
	/**
	 * toString for Media
	 */
	public String toString() {
		String localOutput = output;
		localOutput.replace("{0}", String.valueOf(getId()));
		localOutput.replace("{1}", getPath());
		localOutput.replace("{2}", getName());
		localOutput.replace("{3}", String.valueOf(is_public()));
		localOutput.replace("{4}", getType());
		localOutput.replace("{5}", getTags());
		localOutput.replace("{6}", String.valueOf(getOwnerId())); 
		
		return localOutput;
	}
}
