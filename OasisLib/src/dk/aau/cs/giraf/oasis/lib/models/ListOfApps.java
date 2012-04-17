package dk.aau.cs.giraf.oasis.lib.models;

public class ListOfApps {
	
	private long idApp;
	private long idProfile;
	private Setting<String, String, String> setting;
	private Setting<String, String, String> stat;
	private static String _output = "{0}, {1}, {2}, {3}";
	
	/**
	 * @return the idApp
	 */
	public long getIdApp() {
		return idApp;
	}

	/**
	 * @param idApp the idApp to set
	 */
	public void setIdApp(long idApp) {
		this.idApp = idApp;
	}

	/**
	 * @return the idProfile
	 */
	public long getIdProfile() {
		return idProfile;
	}

	/**
	 * @param idProfile the idProfile to set
	 */
	public void setIdProfile(long idProfile) {
		this.idProfile = idProfile;
	}

	/**
	 * @return the setting
	 */
	public Setting<String, String, String> getSetting() {
		return setting;
	}

	/**
	 * @param setting the setting to set
	 */
	public void setSetting(Setting<String, String, String> setting) {
		this.setting = setting;
	}

	/**
	 * @return the stat
	 */
	public Setting<String, String, String> getStat() {
		return stat;
	}

	/**
	 * @param stat the stat to set
	 */
	public void setStat(Setting<String, String, String> stat) {
		this.stat = stat;
	}

	/**
	 * @param _output the _output to set
	 */
	public static void set_output(String _output) {
		ListOfApps._output = _output;
	}
	
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getIdApp()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdProfile()));
		
		return localOutput;
	}
}
