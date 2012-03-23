package sw6.oasis.viewmodels;

public class ListOfApps {
	private long id;
	private long appId;
	private long profileId;
	private String settings;
	private String stats;
	private String output;
	
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
	 * 	{0} = AppId
	 *  {1} = ProfileId
	 *  {2} = Settings
	 *  {3} = Stats
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
		localOutput.replace("{0}", String.valueOf(getAppId()));
		localOutput.replace("{1}", String.valueOf(getProfileId()));
		localOutput.replace("{2}", String.valueOf(getSettings()));
		localOutput.replace("{3}", String.valueOf(getStats()));
		
		return localOutput;
	}
}
