package savannah.io;

public enum CRUD {
	COMMIT(1, "COMMIT"), READ(2, "READ");
	
	private int value;
	private String toString;
	
	private CRUD(int value, String toString) {
		this.value = value;
		this.toString = toString;
	}
	
	public int getValue() {
		return this.value;
	}
	public String getValueString() {
		String s = "";
		s += this.value;
		return s;
		
	}
	public String toString() {
		return this.toString;
	}
}
