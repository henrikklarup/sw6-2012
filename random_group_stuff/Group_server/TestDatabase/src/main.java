

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

/**
 * Servlet implementation class main
 */
public class main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	
	String firstname = "";
	String middlename = "";
	String lastname = "";
	String phone ="";
	String deptname = "";
	String userType = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.setContentType("text/html");
		
		session = request.getSession();
		
		String user = (String) session.getAttribute("USER");
		String userId = (String) session.getAttribute("ID");
		if (user == null)
		{
			response.sendRedirect("TestDatabase");
		}
		
		// connecting to database
		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select type from AuthUsers where idUser = "+userId+";");
			// displaying records
			while(rs.next()){
				userType = rs.getString("type");
			}
			rs = stmt.executeQuery("SELECT firstname, middlename, surname, phone from Profile where idProfile = '" + userId +"'");
			// displaying records
			while(rs.next()){
				firstname = rs.getString("firstname");
				middlename = rs.getString("middlename");
				if (middlename ==null)
					middlename = "";
				lastname = rs.getString("surname");
				phone = rs.getString("phone");
				//out.print("\t\t\t");

				//out.print("<br>");
			}
			rs = stmt.executeQuery("select idDepartment, name from Department    where idDepartment = (select idDepartment from HasDepartment                    where idProfile ="+userId+");");
			// displaying records
			while(rs.next()){
				deptname = rs.getString("name");
				//middlename = rs.getString("surname");
				//out.print("\t\t\t");

				//out.print("<br>");
			}
		} catch (SQLException e) {
			throw new ServletException("Servlet Could not display records. ID = " + userId, e);
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
		
		String topText = "";
		if (userType.equals("0"))
			topText = firstname + " " + middlename + " " + lastname + "!" + "<br>" + phone + "<br> <b>" + "Admin" + "</b>";
		else if (userType.equals("1"))
			topText = firstname + " " + middlename + " " + lastname + "!" + "<br>" + phone + "<br>" + "Pædagog hos " + deptname;
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Savannah 1.0  - Logget ind som " + user + "</title>");
		out.println("<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />");
		out.println("</head>");
		out.println("<body>");
		out.println("<div id=\"mainBackground\">");
		out.println("<div id=\"mainsiteTop\">");
		out.println("<img src=\"images/i.jpg\" width=\"150\" height=\"100\" style=\"float:left;margin:0 5px 0 0;\">");
		out.println(topText);
		out.println("</div>");
		
		out.println("<hr color=\"Black\" size=\"2\">");
		out.println("<img src=\"images/homeicon.png\">Hjem");
		out.println("<hr color=\"Black\" size=\"2\">");
	
		out.println("<div id=\"mainsiteMain\">");
		out.println("<img src=\"images/dummypic.jpg\"> <br> Profil <br>");
		out.println("<img src=\"images/dummypic.jpg\"> <br> Indstillinger <br>");
		out.println("<img src=\"images/dummypic.jpg\"> <br> Statestik <br>");
		out.println("</div>");
		
		out.println("<div id=\"logoutAlign\">");
		out.println("<form method='POST' action='main' name=\"logoutForm\">\n"+
				"<P ALIGN=\"right\"> <a  href=\"#\" onClick=\"document.logoutForm.submit()\" >Logout</a> <p>"); //"<input type='submit' value='Logout'>\n");
		out.println("</div>");
		out.println("<hr color=\"Black\" size=\"2\">");
		out.println("<footer> Savannah v. 1.0.0 (C)opyright me!</footer>");
		//out.println("<form method='POST' action='main'>\n" +
		//"<input type='hidden' name='Logout'>"+
		//"<input type='submit' value='Logout'>\n" + "</form>");
		
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}
	
	protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		PrintWriter out = response.getWriter();
		out.println("Logger ud... Vent venligst.");
		session.removeAttribute("USER");
		
		
		response.setHeader("Refresh", "1");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		PrintWriter out = response.getWriter();
		out.println("Logger ud... Vent venligst.");
		session.removeAttribute("USER");
		
		
		response.setHeader("Refresh", "1");
	}

}
