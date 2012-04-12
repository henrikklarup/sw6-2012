/** 
 * This program is an example from the book "Internet 
 * programming with Java" by Svetlin Nakov. It is freeware. 
 * For more information: http://www.nakov.com/books/inetjava/ 
 */ 
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.catalina.connector.Request;
import org.apache.tomcat.dbcp.dbcp.DbcpException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabase extends HttpServlet { 
	/**
	 * 
	 */
	HttpSession session;
	private static final long serialVersionUID = 1L;
	String nextLocation = "";

	public void doGet(HttpServletRequest aRequest, 
			HttpServletResponse aResponse) 
					throws IOException, ServletException {
		session = aRequest.getSession();
		String username = aRequest.getParameter("username"); 
		String password = aRequest.getParameter("password");
		nextLocation = aRequest.getParameter("next");
		String DBpassword = null;
		String DBusername = null;



		String DBID = "-1";


		PrintWriter out = aResponse.getWriter();

		// connecting to database
		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT password, idProfile, username from Profile where username = '" + username +"'");
			// displaying records
			while(rs.next()){
				//out.print("\t\t\t");
				DBpassword = rs.getString("password");
				DBID = rs.getString("idProfile");
				DBusername = rs.getString("username");
				//out.print("<br>");
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


		if ((username==null) || (password==null))
		{ 
			session.setAttribute("TriedLOGIN", "0");
			showLoginForm("Brugerlogin påkrævet: ", out); 
		}
		else if (password.equals(DBpassword) && username.equals(DBusername)) 
		{ 

			session.setAttribute("USER", username);
			session.setAttribute("ID", DBID);
			//session.removeAttribute("TriedLOGIN");
			//session.setAttribute("ID", DBID);
			//out.println("<h1>GRATZ!</h1> " + username + " " + DBID);

			if (nextLocation == null)
			{
				out.println("<html>ERROR</html>");
			}
			else if (nextLocation.equals("profile"))
				aResponse.sendRedirect("main"); 
		} 
		else 
		{ 
			session.setAttribute("TriedLOGIN", "1");
			showLoginForm("Ugyldigt login, prøv venligst igen:", out); 
		} 
	}


	public void doPost(HttpServletRequest aRequest, 
			HttpServletResponse aResponse) 
					throws IOException, ServletException { 
		doGet(aRequest, aResponse); 
	} 

	private void showLoginForm( 
			String aCaptionText, PrintWriter aOutput) {
		String triedLogin =  (String) session.getAttribute("TriedLOGIN");
		aOutput.println( 
				"<html>" +
						"<head>" +
						"<title>Savannah 1.0</title>" +
						"<script src=\"javascript/popup.js\">"+
						"</script>" + 
						"<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />" +
				" </head>");
		if (triedLogin.equals("1"))
		{
			aOutput.println("<body onLoad=\"testThis('profile');popup('popUpDiv');getFocus();\">");
		}
		else
		{
			aOutput.println("<body>");

		}		
		aOutput.println("<div id=\"mainBackground\">");
		aOutput.println("<center><h2> Velkommen !</h2>");
		aOutput.println("<br>");
		aOutput.println("<SCRIPT language = JavaScript> "+
				"function testThis(link)"+
				"{"+
				"document.DasForm.next.value = link"+
				"}"+

				"function getFocus()" +
				"{"+
				"document.DasForm.username.focus();" +
				"}"+
				"function clearForm()" +
				"{"+
				"document.DasForm.username.value =\"\";"+
				"document.DasForm.password.value= \"\";"+
				"}"+
				"</SCRIPT>");
		aOutput.println("<hr>");
		aOutput.println("Vælg handling");
		aOutput.println("<p>");
		aOutput.println("<img src=\"images/i.jpg\" ALT=\"test\">");
		aOutput.println("<br>");
		aOutput.println("<a href=\"#\" onclick=\"testThis('profile');popup('popUpDiv');getFocus();\">Profiler</a>");
		aOutput.println("<p>");
		aOutput.println("Tilføj Rediger Slet");
		aOutput.println("</center>");
		aOutput.println("<hr>");
		aOutput.println("<footer> Savannah v. 1.0.0 (C)opyright me!</footer> </div>");
		//out.println("<form method='POST' action='main'>\n" +
		//"<input type='hidden' name='Logout'>"+
		//"<input type='submit' value='Logout'>\n" + "</form>"); 
		aOutput.println("" +
				"<div id=\"blanket\" style=\"display:none;\"></div>"+
				"<div id=\"popUpDiv\" style=\"display:none;\">"+
				"<P align=\"right\"><a href=\"#\" onclick=\"popup('popUpDiv')\" ALIGN=RIGHT>[X]</a></p>"+
				"<form method='POST' action='TestDatabase' name='DasForm'>\n" + 
				"<center><h3>"+
				aCaptionText +
				"</h3>"+
				"<br>\n" + 
				"<table border=\"0\">"+
				"<tr>"+
				"<td>Brugernavn:<td><input type='text' name='username'><br>\n" +
				"</tr>"+
				"<tr>"+
				"<td>Kodeord:<td><input type='password' name='password'><br>\n" +
				"</tr>"+
				"<tr>" +
				"<td><input type='hidden' name='next'><br>\n"+
				"</tr>"+
				"</table>"+
				//"<tr>"+
				"<td><input type='submit' value='Login'><td><input type='button' value='Fortryd' onClick=\"clearForm();popup('popUpDiv')\">\n" +
				//"</tr>"+
				
				"</center>"+
				"</div>" +
				""+
				"</body></form></html>" 
				); 
	}

}