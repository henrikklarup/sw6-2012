package savannah.server;

import java.sql.*;

public class QueryHandler {
	private Connection con = null;
	private PreparedStatement pStat = null;
	private ResultSet result = null;
	public QueryHandler() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://localhost/testbase","admin","admin");
	}
	
	public int SendCommit(String s)
	{
		try {
			
		
		pStat = con.prepareStatement(s);
		pStat.execute();
		
		} 
		catch (SQLException e)
			{ e.printStackTrace(); }
		return 1;
	}
	
	public ResultSet SendRequest(String s)
	{
		try {
			pStat = con.prepareStatement(s);
			result = pStat.executeQuery();
		}
		catch (SQLException e)
			{ e.printStackTrace(); }
		
		return result;
	}
}
