package sw6.oasis.viewmodels;

public class Media {

	private long id;
	private String path;
	private String name;
	private boolean _public;
	private String type;
	private String tags;
	private long ownerId;
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
	
	@Override
	public String toString() {
		return "ID: " + id 
				+ " Path: " + path 
				+ " Name: " + name 
				+ " Public: " + _public 
				+ " Type: " + type 
				+ " Tags: " + tags 
				+ " OwnerID: " +ownerId
				;
	}
}
