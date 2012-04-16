
public class Profile {

	private int id;
	private String name;
	private String guardian;
	private String parent;
	private String username;

	public Profile(int id2, String name, String guardian, String parent, String username)
	{
		this.id = id2;
		this.name = name;
		this.guardian = guardian;
		this.parent = parent;
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public String getGuardian()
	{
		return this.guardian;
	}

	public String getParent()
	{
		return this.parent;
	}

}
