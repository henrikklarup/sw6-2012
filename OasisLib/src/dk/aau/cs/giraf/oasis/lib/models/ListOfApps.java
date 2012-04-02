package dk.aau.cs.giraf.oasis.lib.models;

public class ListOfApps {
	private long id;
	private long appId;
	private long profileId;
	private Setting<String, String, String> settings;
	private Stat<String, String, String> stats;
	private static String _output = "{0}, {1}, {2}, {3}, {4}";

	/**
	 * Constructor with arguments
	 * 
	 * @param appId the appId to set
	 * @param profileId the profileId to set
	 * @param settings the settings to set
	 * @param stats the stats to set
	 */
	public ListOfApps(long appId, long profileId, Setting<String, String, String> settings, Stat<String, String, String> stats) {
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
	public Setting<String, String, String> getSettings() {
		return settings;
	}
	/**
	 * @param settings the settings to set
	 */
	public void setSettings(Setting<String, String, String> settings) {
		this.settings = settings;
	}
	/**
	 * @return the stats
	 */
	public Stat<String, String, String> getStats() {
		return stats;
	}
	/**
	 * @param stats the stats to set
	 */
	public void setStats(Stat<String, String, String> stats) {
		this.stats = stats;
	}

	/**
	 *  Set output
	 *  {0} = ID
	 *  {1} = AppId
	 *  {2} = ProfileId
	 *  {3} = Settings
	 *  {4} = Stats
	 * @param output
	 */
	public static void setOutput(String output) {
		_output = output;
	}

	@Override
	/**
	 * toString for List Of apps
	 */
	public String toString() {
		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getId()));
		localOutput = localOutput.replace("{1}", String.valueOf(getAppId()));
		localOutput = localOutput.replace("{2}", String.valueOf(getProfileId()));
		localOutput = localOutput.replace("{3}", String.valueOf(getSettings()));
		localOutput = localOutput.replace("{4}", String.valueOf(getStats()));

		return localOutput;
	}
}
