package dk.aau.cs.giraf.oasis.lib.models;

public class App {

	private long id; 
	private String name;
	private String version;
	private Setting<String, String, String> settings;
	private Stat<String, String, String> stats;
	private String icon;
	private String aPackage;
	private String activity;
	private static String _output = "{0}, {1}, {2}, {3}, {4}, {5}";

	/**
	 * Constructor with arguments
	 * @param name the name to set
	 * @param version the version to set
	 */
	public App(String name, String version) {
		this.name = name;
		this.version = version;
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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the aPackage
	 */
	public String getaPackage() {
		return aPackage;
	}

	/**
	 * @param aPackage the aPackage to set
	 */
	public void setaPackage(String aPackage) {
		this.aPackage = aPackage;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * Set output
	 * {0} = Id
	 * {1} = Name
	 * {2} = Version
	 * {3} = Icon path
	 * {4} = package
	 * {5} = activity
	 * @param output the output to set
	 */
	public static void setOutput(String output) {
		_output = output;
	}

	/**
	 * toString for app
	 */
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getId()));
		localOutput = localOutput.replace("{1}", getName());
		localOutput = localOutput.replace("{2}", getVersion());
		localOutput = localOutput.replace("{3}", getIcon());
		localOutput = localOutput.replace("{4}", getaPackage());
		localOutput = localOutput.replace("{5}", getActivity());

		
		return localOutput;
	}
}