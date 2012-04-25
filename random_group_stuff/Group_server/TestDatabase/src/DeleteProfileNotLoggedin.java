

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class DeleteProfileNotLoggedin
 */
public class DeleteProfileNotLoggedin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProfileNotLoggedin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		ArrayList<Profile> profilelist = new ArrayList<Profile>();
		String userID = (String) session.getAttribute("PROFILEIDTODELETE");
		//out.println(userID);
		
		
		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");

			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from Profile where idProfile = 3;");
			// displaying records
			while(rs.next()){
				int idProfile = rs.getInt("idProfile");
				String firstname = rs.getString("firstname");
				String surname = rs.getString("surname");
				String middlename = rs.getString("middlename");
				int pRole = rs.getInt("pRole");
				int phone = rs.getInt("phone");
				String picture = rs.getString("picture");
				Profile p = new Profile(idProfile, firstname, surname, middlename, pRole, phone, picture);
				profilelist.add(p);
			}

		} catch (SQLException e) {
			throw new ServletException("Servlet Could not display records.", e);
		} 	catch (ClassNotFoundException e) {
			throw new ServletException("JDBC Driver not found.", e);
		} finally 
		{
			try 
			{
				if(rs != null) 
				{
					rs.close();
					rs = null;
				}
				if(stmt != null) 
				{
					stmt.close();
					stmt = null;
				}
				if(con != null) 
				{
					con.close();
					con = null;
				}
			} 
			catch (SQLException e) 
			{

			}
		}
		
		out.println("<html>" +
					"<head>" +
					"<title>Savannah 1.0 - Slet profil</title>" +
					"<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />" +
					"<script src=\"javascript/popup.js\"></script>" +
					"</head>" +
					"<body onLoad=\"getFocusQuick();\">" +
					"<div id=\"mainBackground\">" +
					"<script type='text/javascript'>"+
						
						"function deleteContent() {" +
						"document.DasForm.submit();" +
						"}" +
						"</script>");
				out.println("<body>");
				out.println("Er du sikker på at du vil slette:");
				out.println("<form method='POST' name='DasForm' action='DeleteProfileNotLoggedin'>");
				out.println("<table borders='0'>");
				out.println("<tr>");
				for (Profile p : profilelist){
				
					//out.println("<td onmouseover=\"ChangeColor(this, true);\" onmouseout=\"ChangeColor(this, false);\"" + 
					out.println("onclick=\"setID('"+p.getID()+"'); submitform();\">");
					out.println("<td>" + p.getID() + "</td><td>"+p.getName()+"</td>");
					out.println("</tr>");
				}
				out.println("<tr><td></td><td><input type='button' onClick=\"deleteContent();\" value='Slet'/><input type='button' onClick=\"\" value='Fortryd'/></tr>");
				out.println("</table>");
				out.println("<hr>");
				out.println("</form>");
				out.println("</div>");
				out.println("</body>");
				out.println("</html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String userID = (String) session.getAttribute("PROFILEIDTODELETE");
		//out.println(userID);
		
		/*Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");

			stmt = con.createStatement();
			rs = stmt.executeQuery("delete from AuthUsers where idUser = 3;");
			
		}
			catch (SQLException e) {
				throw new ServletException("Servlet Could not display records.", e);
			} 	catch (ClassNotFoundException e) {
				throw new ServletException("JDBC Driver not found.", e);
			} finally 
			{
				try 
				{
					if(rs != null) 
					{
						rs.close();
						rs = null;
					}
					if(stmt != null) 
					{
						stmt.close();
						stmt = null;
					}
					if(con != null) 
					{
						con.close();
						con = null;
					}
				} 
				catch (SQLException e) 
				{

				}
			}
		//out.println("Hello der sker altså noget");*/
		
		Connection con;
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
			con = DriverManager.getConnection("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
			PreparedStatement ps = (PreparedStatement) con.prepareStatement("delete from AuthUsers where idUser = " + userID +";");
			//this if-statement is not working properly 
			int i = ps.executeUpdate();
			if (i == 0){
				out.println(userID + " has been deleted");
				
			}
			else{
				//out.println(userID + " has been deleted");
				response.sendRedirect("SelectProfileToDelete");
			}
		}
		catch(Exception e){
			out.println("The execution is " + e);
		}
		}
}
