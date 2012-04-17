package dk.aau.cs.giraf.oasis.lib.models;

public class HasSubDepartment {
	
	private long idDepartment;
	private long idSubDepartment;
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
	 * @return the idSubDepartment
	 */
	public long getIdSubDepartment() {
		return idSubDepartment;
	}

	/**
	 * @param idSubDepartment the idSubDepartment to set
	 */
	public void setIdSubDepartment(long idSubDepartment) {
		this.idSubDepartment = idSubDepartment;
	}

	/**
	 * @param _output the _output to set
	 */
	public static void set_output(String _output) {
		HasSubDepartment._output = _output;
	}
	
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getIdDepartment()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdSubDepartment()));
		
		return localOutput;
	}
}
