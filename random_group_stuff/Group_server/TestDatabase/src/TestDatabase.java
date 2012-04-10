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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDatabase extends HttpServlet { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest, 
			HttpServletResponse aResponse) 
					throws IOException, ServletException {
		
		String username = aRequest.getParameter("username"); 
		String password = aRequest.getParameter("password");
		String DBID = "";
		String DBusername ="";
		// connecting to database
		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Profile");
			// displaying records
			while(rs.next()){
				DBID = rs.getObject(1).toString();
				//out.print("\t\t\t");
				DBusername = rs.getObject(2).toString();
				//out.print("<br>");
			}
		} catch (SQLException e) {
			throw new ServletException("Servlet Could not display records.", e);
		} catch (ClassNotFoundException e) {
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
			PrintWriter out = aResponse.getWriter();
			
			if ((username==null) || (password==null))
			{ 
				HttpSession session = aRequest.getSession();
				session.setAttribute("USER", null);
				session.setAttribute("ID", null);
				showLoginForm("Please login:", out); 
			} 
			else if (username.equals(DBusername) && password.equals("kodeord")) 
			{ 
				HttpSession session = aRequest.getSession(); 
				session.setAttribute("USER", username);
				session.setAttribute("ID", DBID);
				out.println("<h1>GRATZ!</h1>");
				aResponse.sendRedirect("main"); 
			} 
			else 
			{ 
				showLoginForm("Invalid login! Try again:", out); 
			} 
		}
	} 

	public void doPost(HttpServletRequest aRequest, 
			HttpServletResponse aResponse) 
					throws IOException, ServletException { 
		doGet(aRequest, aResponse); 
	} 

	private void showLoginForm( 
			String aCaptionText, PrintWriter aOutput) {
		
		aOutput.println( 
				"<html><head>" +
				"<link rel='stylesheet' type='text/css' href='/css/SavannahStyle.css' />" +
						"</heaqd>" +
						"<title>Login</title><body>\n" + 
						"<form method='POST' action='TestDatabase'>\n" + 
						aCaptionText + "<br>\n" + 
						"Brugernavn: <input type='text' name='username'><br>\n" + 
						"ID : <input type='password' name='password'><br>\n" + 
						"<input type='submit' value='Login'>\n" + "<p> <h6>For testing pourpose use username=Jesper password=kodeord</h6>"+ 
						"</body></form></html>" 
				); 
	} 
}
/*
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class TestDatabase

public class TestDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
			{
		String bruger = request.getParameter("username");
		String DBID = "-1";
		String DBusername = ".";

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// connecting to database
		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Profile");
			// displaying records
			while(rs.next()){
				DBID = rs.getObject(1).toString();
				//out.print("\t\t\t");
				DBusername = rs.getObject(2).toString();
				//out.print("<br>");
			}
		} catch (SQLException e) {
			throw new ServletException("Servlet Could not display records.", e);
		} catch (ClassNotFoundException e) {
			throw new ServletException("JDBC Driver not found.", e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
					rs = null;
				}
				if(stmt != null) {
					stmt.close();
					stmt = null;
				}
				if(con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {}
		}
		if (bruger == null)
		{
			showLogin("Please Login", out);
		}
		if (bruger.equals(DBusername))
		{
            HttpSession session = request.getSession(); 
            session.setAttribute("USER", bruger); 
		}
		else
		{
			showLogin("Invalid login", out);
		}
			}

    public void doPost(HttpServletRequest aRequest, 
            HttpServletResponse aResponse) 
        throws IOException, ServletException { 
            doGet(aRequest, aResponse); 
        } 

	void showLogin(String text, PrintWriter out)
	{
		out.println("<html>");
		out.println("<head><title>Servlet JDBC MyTest</title></head>");
		out.println("<body>");
		out.println("<form method='POST' action='login'>\n" + text +
				"<br>\n" + 
				"<input type='text' name='username'><br>\n" + 
				"<input type='password' name='password'><br>\n" + 
				"<input type='submit' value='Login'>\n");
		out.println("<h1>Servlet JDBC</h1>");
		out.println("</body></html>"); 
	}

}



 */