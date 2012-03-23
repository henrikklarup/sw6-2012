package sw6.oasis.viewmodels;

public class App {
	
	private long id; 
	private String name;
	private String versionNumber;
	private String output;
	
	/**
	 * Constructor with arguments
	 * 
	 * @param name the name to set
	 * @param versionNumber the versionNumber to set
	 */
	public App(String name, String versionNumber) {
		this.name = name;
		this.versionNumber = versionNumber;
	}
	
	/**
	 * Empty constructor
	 */
	public App() {
		
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
	 * @return the versionNumber
	 */
	public String getVersionNumber() {
		return versionNumber;
	}

	/**
	 * @param versionNumber the versionNumber to set
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	
	/**
	 * 	{0} = Id
	 *  {1} = Name
	 *  {2} = Version
	 * @param output the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	
	/**
	 * toString for app
	 */
	@Override
	public String toString() {
		String localOutput = output;
		localOutput.replace("{0}", String.valueOf(getId()));
		localOutput.replace("{1}", getName());
		localOutput.replace("{2}", getVersionNumber());
		
		return localOutput;
	}
}
