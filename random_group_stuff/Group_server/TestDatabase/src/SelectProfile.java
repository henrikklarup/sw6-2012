

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

import org.apache.jasper.tagplugins.jstl.core.ForEach;

/**
 * Servlet implementation class SelectProfile
 */
public class SelectProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	int id;
	String name;
	String guardian;
	String parent;
	String username;


	ArrayList<Profile> profiles = new ArrayList<Profile>();
	ArrayList<Profile> guardians = new ArrayList<Profile>();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		profiles.clear();

		guardians.clear();

		String loginID = request.getParameter("myId");
		String triedLogin = request.getParameter("triedLogin");
		String guardianID = request.getParameter("guardianID");
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		Profile originalProfile;
		//String originalUsername = request.getParameter("username");



		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * from Profile;");
			// displaying records
			while(rs.next()){
				id = rs.getInt("idProfile");
				name = rs.getString("firstname") + " " +rs.getString("middlename") + " " + rs.getString("surname");
				username = rs.getString("username");

				Profile p = new Profile(id, name, null, null,username);

				profiles.add(p);
			}

			if (loginID != null)
			{

				if (triedLogin != null && triedLogin.equals("1") && guardianID != null)
				{
					stmt = con.createStatement();
					rs = stmt.executeQuery("select username, password from Profile "+
							"where idProfile = " + guardianID + ";");

					// displaying records
					while(rs.next()){
						String correctPass= rs.getString("password");
						String correcUsername = rs.getString("username");


						session.setAttribute("LOGGEDINAS", correcUsername);

						if (password.equals(correctPass))
						{
							session.setAttribute("ID", loginID);
							session.setAttribute("USER", correcUsername);

							response.sendRedirect("main");
						}
					}
				}
				else
				{
					stmt = con.createStatement();
					rs = stmt.executeQuery("select * from Profile "+
							"where idProfile = "+loginID+";");
					originalProfile = null;
					while(rs.next()){
						originalProfile = new Profile(rs.getInt("idProfile"), "" + rs.getString("firstname") + " " + rs.getString("middlename") +" "+ rs.getString("surname"), null, rs.getString("password"), rs.getString("username"));
					}
					rs = stmt.executeQuery("select * from Profile "+
							"where idProfile in (select idGuardian from HasGuardian " +
							"where idChild = "+loginID+");");
					guardians.add(originalProfile);
					// displaying records
					while(rs.next()){
						id = rs.getInt("idProfile");
						name = rs.getString("firstname") + " " +rs.getString("middlename") + " " + rs.getString("surname");
						Profile g = new Profile(id, name, null, null,username);
						guardians.add(g);
					}
				}

			}

		} catch (SQLException e) {
			throw new ServletException("Servlet Could not display records. id=" + guardianID, e);
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

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Savannah 1.0</title>");
		out.println("<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />");
		out.println("<script src=\"javascript/popup.js\"></script>"); 
		out.println("</head>");
		if (loginID != null)
		{
			out.println("<body onLoad=\"setID('"+loginID+"'); popup('popUpDiv'); getFocus();\">");
		}
		else
		{
			out.println("<body>");

		}	

		out.println("<script type='text/javascript'>"+
				"function ChangeColor(tableRow, highLight)"+
				"{"+
				"if (highLight)"+
				"{"+
				"tableRow.style.backgroundColor = '#dcfac9';"+
				"}"+
				"else"+
				"{"+
				"tableRow.style.backgroundColor = '#00CCFA';"+
				"}"+
				"}"+
				"function DoNav(theUrl)"+
				"{"+
				"document.location.href = theUrl;"+
				"}"+
				"function setID(id)"+
				"{"+
				"document.DasForm.myId.value = id;"+
				"}"+
				"function setLogin()"+
				"{"+
				"document.DasForm.triedLogin.value = 1;"+
				"}"+
				"function submitform()"+
				"{"+
				"document.DasForm.submit();"+
				"}"+
				"function clearForm()" +
				"{"+
				"document.DasForm.password.value= \"\";"+
				"}"+
				"function getFocus()" +
				"{"+
				"document.DasForm.password.focus();" +
				"}"+
				"</script>");
		out.println("<div id=\"mainBackground\">");
		out.println("<center><h2> Vælg profil:</h2>");
		out.println("<br>");
		out.println("<hr>");
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>ID</th>");
		out.println("<th>Navn</th>");
		//out.println("<th>Pædagog</th>");
		//out.println("<th>Forældre</th>");
		out.println("</tr>");
		for (Profile p : profiles) 
		{

			out.println("<tr onmouseover=\"ChangeColor(this, true);\" onmouseout=\"ChangeColor(this, false);\"" + 
					"onclick=\"setID('"+p.getId()+"'); submitform();\">");
			out.println("<td>" + p.getId() + "</td><td>"+p.getName()+"</td>");//<td>"+p.getGuardian()+"</td><td>"+p.getParent()+"</td>");
			out.println("</tr>");

		}
		out.println("</table>");
		out.println("<p>");
		out.println("</div>");

		out.println("" +
				"<div id=\"blanket\" style=\"display:none;\"></div>"+
				"<div id=\"popUpDiv\" style=\"display:none;\">"+
				"<P align=\"right\"><a href=\"#\" onclick=\"popup('popUpDiv')\" ALIGN=RIGHT>[X]</a></p>"+
				"<form method='POST' action='SelectProfile' name='DasForm'>\n" + 
				"<center><h3>");
		if (!guardians.isEmpty())
		{
			out.println("Login påkrævet</h3>");
			out.println("<br>");
			out.println("<table border=\"0\">"+
					"<tr>"+
					"<td>Bruger:<td>");

			out.println("<select name='guardianID'>");
			for (Profile g : guardians) {
				out.println("<option value='"+g.getId()+"'>"+g.getName());	
			}
			out.println("</select");
			out.println("</tr>");

		}
		else
		{
			out.println("Login påkrævet");
			out.println("</h3>"+
					"<br>\n" + 
					"<table border=\"0\">"+
					"<tr>"+
					"<td>Brugernavn:<td><input type='text' name='username'><br>\n" +
					"</tr>");
		}
		out.println("<tr>"+
				"<td>Kodeord:<td><input type='password' name='password'><br>\n" +
				"</tr>"+
				"<tr>" +
				"<td><input type='hidden' name='myId' value=''><br><input type='hidden' name='triedLogin' value=0><br><input type='hidden' name='originalUsername'>"+
				"</tr>"+
				"</table>"+
				//"<tr>"+
				"<td><input type='button' value='Login' onClick=\"setLogin(); submitform();\"><td><input type='button' value='Fortryd' onClick=\"clearForm();popup('popUpDiv')\">\n" +
				//"</tr>"+

				"</center>"+
				"</div>");

		out.println("</body>");
		out.println("</html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest aRequest, 
			HttpServletResponse aResponse) 
					throws IOException, ServletException { 
		doGet(aRequest, aResponse); 
	} 


}

