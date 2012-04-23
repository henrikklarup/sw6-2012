package dk.aau.cs.giraf.oasis.lib.models;

public class HasLink {

	private long idMedia;
	private long idSubMedia;
	private static String _output = "{0}, {1}";
	
	/**
	 * @return the idMedia
	 */
	public long getIdMedia() {
		return idMedia;
	}

	/**
	 * @param idMedia the idMedia to set
	 */
	public void setIdMedia(long idMedia) {
		this.idMedia = idMedia;
	}

	/**
	 * @return the idSubMedia
	 */
	public long getIdSubMedia() {
		return idSubMedia;
	}

	/**
	 * @param idSubMedia the idSubMedia to set
	 */
	public void setIdSubMedia(long idSubMedia) {
		this.idSubMedia = idSubMedia;
	}

	/**
	 * @param _output the _output to set
	 */
	public static void set_output(String _output) {
		HasLink._output = _output;
	}
	
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getIdMedia()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdSubMedia()));
		
		return localOutput;
	}
}
