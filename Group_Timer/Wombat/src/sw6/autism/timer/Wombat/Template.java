package sw6.autism.timer.Wombat;

public class Template {
	private long m_id;
	private String m_name;
	
	Template(long id, String name){
		m_id = id;
		m_name = name;
	}
	
	public long getId(){
		return m_id;
	}
	
	public String getName(){
		return m_name;
	}
	
	public void setName (String name){
		m_name = name;
	}
	
	public String getInfo(){
		// TODO: Change this to correct info
		return m_name;
	}
}
