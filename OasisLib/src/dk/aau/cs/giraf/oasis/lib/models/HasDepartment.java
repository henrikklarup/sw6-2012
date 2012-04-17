package dk.aau.cs.giraf.oasis.lib.models;

public class HasDepartment {
	
	private long idDepartment;
	private long idProfile;
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
	 * @param _output the _output to set
	 */
	public static void set_output(String _output) {
		HasDepartment._output = _output;
	}
	
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getIdDepartment()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdProfile()));
		
		return localOutput;
	}
}
