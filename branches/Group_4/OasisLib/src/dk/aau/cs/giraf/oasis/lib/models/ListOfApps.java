package dk.aau.cs.giraf.oasis.lib.models;

public class ListOfApps {

	private long idApp;
	private long idProfile;
	private Setting<String, String, String> settings;
	private Stat<String, String, String> stats;
	private static String _output = "{0}, {1}, {2}, {3}";

	/**
	 * Constructor with arguments
	 * 
	 * @param settings the settings to set
	 * @param stats the stats to set
	 */
	public ListOfApps(Setting<String, String, String> settings, Stat<String, String, String> stats) {
		this.settings = settings;
		this.stats = stats;
	}

	/**
	 * Empty constructor
	 */
	public ListOfApps() {
	}
	
	/**
	 * @return the appId
	 */
	public long getIdApp() {
		return idApp;
	}
	/**
	 * @param idApp the appId to set
	 */
	public void setIdApp(long idApp) {
		this.idApp = idApp;
	}
	/**
	 * @return the profileId
	 */
	public long getIdProfile() {
		return idProfile;
	}
	/**
	 * @param profileId the profileId to set
	 */
	public void setIdProfile(long idProfile) {
		this.idProfile = idProfile;
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
	 *  {1} = IdApp
	 *  {2} = IdProfile
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
		localOutput = localOutput.replace("{0}", String.valueOf(getIdApp()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdProfile()));
		localOutput = localOutput.replace("{2}", String.valueOf(getSettings()));
		localOutput = localOutput.replace("{3}", String.valueOf(getStats()));

		return localOutput;
	}
}
