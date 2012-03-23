package sw6.oasis.viewmodels;

public class Certificate {
	private long id;
	private String authkey;
	private String output;
	
	public Certificate(String authKey) {
		this.authkey = authKey;
	}
	
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
	
	/**
	 * @param output the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	
	@Override
	public String toString() {
		String localOutput = output;
		localOutput.replace("{0}", String.valueOf(getId()));
		localOutput.replace("{1}", getAuthkey());
		
		return localOutput;
	}
}
