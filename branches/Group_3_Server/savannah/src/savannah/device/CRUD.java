package savannah.device;

public enum CRUD {
	COMMIT(1, "COMMIT"), REQUEST(2, "REQUEST"), PING(3, "PING"), ERROR(4, "ERROR");
	
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
//		String s = String.valueOf(this.value);
//		s += this.value;
		return Integer.toString(this.value);
		
	}
	public String toString() {
		return this.toString;
	}
}
