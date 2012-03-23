package sw6.oasis.viewmodels;

public class ListOfApps {
	private long id;
	private long appId;
	private long profileId;
	private String settings;
	private String stats;
	private String output;

	/**
	 * Constructor with arguments
	 * 
	 * @param appId the appId to set
	 * @param profileId the profileId to set
	 * @param settings the settings to set
	 * @param stats the stats to set
	 */
	public ListOfApps(long appId, long profileId, String settings, String stats) {
		this.appId = appId;
		this.profileId = profileId;
		this.settings = settings;
		this.stats = stats;
	}
	
	/**
	 * Empty constructor
	 */
	public ListOfApps() {
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
	 * @return the appId
	 */
	public long getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(long appId) {
		this.appId = appId;
	}
	/**
	 * @return the profileId
	 */
	public long getProfileId() {
		return profileId;
	}
	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	/**
	 * @return the settings
	 */
	public String getSettings() {
		return settings;
	}
	/**
	 * @param settings the settings to set
	 */
	public void setSettings(String settings) {
		this.settings = settings;
	}
	/**
	 * @return the stats
	 */
	public String getStats() {
		return stats;
	}
	/**
	 * @param stats the stats to set
	 */
	public void setStats(String stats) {
		this.stats = stats;
	}
	
	/**
	 * 	/**
	 *  Set output
	 *  {0} = ID
	 * 	{1} = AppId
	 *  {2} = ProfileId
	 *  {3} = Settings
	 *  {4} = Stats
	 * @param output
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	@Override
	/**
	 * toString for List Of apps
	 */
	public String toString() {
		String localOutput = output;
		localOutput.replace("{0}", String.valueOf(getId()));
		localOutput.replace("{1}", String.valueOf(getAppId()));
		localOutput.replace("{2}", String.valueOf(getProfileId()));
		localOutput.replace("{3}", getSettings());
		localOutput.replace("{4}", getStats());
		
		return localOutput;
	}
}
