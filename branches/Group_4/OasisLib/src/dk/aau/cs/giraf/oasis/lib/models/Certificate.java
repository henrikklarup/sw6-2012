package dk.aau.cs.giraf.oasis.lib.models;

public class Certificate {
	private long id;
	private String authkey;
	private static String _output = "{0}, {1}";

	/**
	 * Constructor with arguments
	 * @param authKey is the authkey to set
	 */
	public Certificate(String authKey) {
		this.authkey = authKey;
	}

	/**
	 * Empty constructor
	 */
	public Certificate() {
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
	 * @return the authkey
	 */
	public String getAuthkey() {
		return authkey;
	}
	/**
	 * @param authkey the authkey to set
	 */
	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	/**Set output
	 *  {0} = Id
	 *  {1} = Authkey

	 * @param output the output to set
	 */
	public static void setOutput(String output) {
		_output = output;
	}

	/**
	 * toString for certificate
	 */
	@Override
	/**
	 * toString for Certificate
	 */
	public String toString() {
		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getId()));
		localOutput = localOutput.replace("{1}", getAuthkey());

		return localOutput;
	}
}
