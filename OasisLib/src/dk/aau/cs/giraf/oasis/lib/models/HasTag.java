package dk.aau.cs.giraf.oasis.lib.models;

public class HasTag {

	private long idMedia;
	private long idTag;
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
	 * @return the idTag
	 */
	public long getIdTag() {
		return idTag;
	}

	/**
	 * @param idTag the idTag to set
	 */
	public void setIdTag(long idTag) {
		this.idTag = idTag;
	}

	/**
	 * @param _output the _output to set
	 */
	public static void set_output(String _output) {
		HasTag._output = _output;
	}
	
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getIdMedia()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdTag()));
		
		return localOutput;
	}
}
