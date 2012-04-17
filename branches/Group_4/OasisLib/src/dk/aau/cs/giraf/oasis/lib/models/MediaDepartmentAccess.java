package dk.aau.cs.giraf.oasis.lib.models;

public class MediaDepartmentAccess {

	private long idDepartment;
	private long idMedia;
	private static String _output = "{0}, {1}";
	
	/**
	 * @return the idDepartment
	 */
	public long getIdDepartment() {
		return idDepartment;
	}

	/**
	 * @param idDepartment the idDepartment to set
	 */
	public void setIdDepartment(long idDepartment) {
		this.idDepartment = idDepartment;
	}

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
	 * @param _output the _output to set
	 */
	public static void set_output(String _output) {
		MediaDepartmentAccess._output = _output;
	}
	
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getIdDepartment()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdMedia()));
		
		return localOutput;
	}
}
