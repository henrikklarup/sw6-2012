

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.xml.internal.ws.api.pipe.NextAction;

/**
 * Servlet implementation class EditProfileNotLoggedin
 */
public class EditProfileNotLoggedin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<Department> possibleDepartments = new ArrayList<Department>();
	ArrayList<Department> currentDepartments = new ArrayList<Department>();
	ArrayList<Profile> possibleChild = new ArrayList<Profile>();
	ArrayList<Profile> currentChild = new ArrayList<Profile>();
	ArrayList<App> currentApps = new ArrayList<App>();
	ArrayList<App> possibleApps = new ArrayList<App>();

	String[] selectedDeps;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditProfileNotLoggedin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		possibleDepartments.clear();
		currentDepartments.clear();
		possibleChild.clear();
		possibleChild.clear();
		currentApps.clear();
		possibleApps.clear();

		HttpSession session = request.getSession();

		String userID = (String) session.getAttribute("PROFILEIDTOEDIT");

		if (userID == null)
			response.sendRedirect("TestDatabase");

		PrintWriter out = response.getWriter();

		int idProfile = -1;
		String firstname = null;
		String surname = null;
		String middlename = null;
		int pRole = -1;
		int phone = -1;
		String picture = null;
		int idDepartment = -1;
		boolean isChild = false;
		boolean isGuardian = false;
		String username = null;
		String currentPassword = null;

		ArrayList<Profile> guardians = new ArrayList<Profile>();

		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * from Profile where idProfile = '" + userID +"'");
			// displaying records
			while(rs.next()){
				//out.print("\t\t\t");
				idProfile = rs.getInt("idProfile");
				firstname = rs.getString("firstname");
				surname = rs.getString("surname");
				middlename = rs.getString("surname");
				pRole = rs.getInt("pRole");
				phone = (int) rs.getLong("phone");
				picture = rs.getString("picture");
				if (pRole == 3)
					isChild = true;
				if (pRole == 1)
					isGuardian = true;
				//out.print("<br>");
			}

			Statement stmt0 = con.createStatement();
			ResultSet rs0 = stmt0.executeQuery("SELECT * from AuthUsers where idUser = '" + userID +"'");
			// displaying records
			while(rs0.next())
			{
				username = rs0.getString("username");
				//out.print("\t\t\t");
				//out.print("<br>");
			}


			//Find all departments a person can be linked to
			Statement stmt1 = con.createStatement();
			ResultSet rs1 = stmt1.executeQuery("SELECT * from HasDepartment where idProfile = '" + idProfile + "'");
			while(rs1.next()){
				idDepartment = rs1.getInt("idDepartment");
			}

			if (pRole != 2)
			{
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery("select * from Department "+
						"where idDepartment in "+
						"(select idDepartment from HasDepartment "+
						"where  idProfile = '"+idProfile+"');");

				while(rs2.next()){

					Department d = new Department(rs2.getInt("idDepartment"), rs2.getString("name"), rs2.getString("address"), (int)rs2.getInt("phone"), rs2.getString("email"));
					currentDepartments.add(d);
					//possibleDepartments.add(d);
					getAllDepts(d,"");
				}

			}

			//Find possible Guardians for a child
			if(isChild)
			{
				for (Department d : currentDepartments) {


					Statement stmt3 = con.createStatement();
					ResultSet rs3 = stmt3.executeQuery("select * from Profile "+
							"where idProfile in "+
							"(select idProfile from HasDepartment "+
							"where idDepartment = "+d.getID()+") " +
							"and pRole = 1;");
					while(rs3.next()){
						Profile p = new Profile(rs3.getInt("idProfile"), rs3.getString("firstname"), rs3.getString("surname"), rs3.getString("middlename"), rs.getInt("pRole"), (int)rs3.getLong("phone"), rs3.getString("picture"));
						possibleChild.add(p);

					}
				}
				//Find current Guardian(s) for child
				Statement stmt3 = con.createStatement();
				ResultSet rs3 = stmt3.executeQuery("select * from Profile "+
						"where idProfile in "+ 
						"(select idGuardian from HasGuardian "+ 
						"where idChild="+idProfile+");");
				while(rs3.next()){
					Profile p = new Profile(rs3.getInt("idProfile"), rs3.getString("firstname"), rs3.getString("surname"), rs3.getString("middlename"), rs.getInt("pRole"), (int)rs3.getLong("phone"), rs3.getString("picture"));
					possibleChild.add(p);

				}
			}

			//Find possible childs for a guardian
			if (isGuardian)
			{
				for (Department d : currentDepartments) {


					Statement stmt3 = con.createStatement();
					ResultSet rs3 = stmt3.executeQuery("select * from Profile "+
							"where idProfile in "+
							"(select idProfile from HasDepartment "+
							"where idDepartment = "+d.getID()+") " +
							"and pRole = 3;");
					while(rs3.next()){
						Profile p = new Profile(rs3.getInt("idProfile"), rs3.getString("firstname"), rs3.getString("surname"), rs3.getString("middlename"), rs3.getInt("pRole"), (int)rs3.getLong("phone"), rs3.getString("picture"));
						possibleChild.add(p);

					}
				}
				//Find current Guardian(s) for child
				Statement stmt4 = con.createStatement();
				ResultSet rs4 = stmt4.executeQuery("select * from Profile "+
						"where idProfile in "+ 
						"(select idChild from HasGuardian "+ 
						"where idGuardian="+userID+");");
				while(rs4.next()){
					Profile p = new Profile(rs4.getInt("idProfile"), rs4.getString("firstname"), rs4.getString("surname"), rs4.getString("middlename"), rs4.getInt("pRole"), (int)rs4.getLong("phone"), rs4.getString("picture"));
					possibleChild.add(p);

				}
				//Get current apps:
				Statement stmt5 = con.createStatement();
				ResultSet rs5= stmt5.executeQuery("select * from Apps "+
						"where idApp in (select idApp from ListOfApps "+
						"where idProfile ="+userID+");");

				while(rs5.next()){
					App app = new App(rs5.getInt("idApp"), rs5.getString("name"), rs5.getString("version"));
					currentApps.add(app);

				}
			}

			//Get all possible apps:
			Statement stmt6 = con.createStatement();
			ResultSet rs6= stmt6.executeQuery("select * from Apps;");

			while(rs6.next()){
				App app = new App(rs6.getInt("idApp"), rs6.getString("name"), rs6.getString("version"));
				possibleApps.add(app);

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



		out.println("<html>"+
				"<head>"+
				"<title>Savannah 1.0</title>"+
				"<link rel='stylesheet' type='text/css' href='CSS/SavannahStyle.css' />" +
				"</head>"+
				"<body>"+
				"<center>"+
				"<div id=\"mainBackground\">"+
				"<SCRIPT language = JavaScript>"+
				"function listbox_move(fromID, toID) " +
				"{"+
					"var from = document.getElementById(fromID);"+
					"var to = document.getElementById(toID);"+
					"for(var count=0; count < from.options.length; count++) " +
					"{"+
						"if(from.options[count].selected == true) " +
						"{"+
							"var option = from.options[count];"+ 
							"var newOption = document.createElement(\"option\");"+
							"newOption.value = option.value;"+
							"newOption.text = option.text;"+
							"newOption.selected = true;"+
							"try " +
							"{"+
								"to.add(newOption, null);"+ //for normal browsers"
								"from.remove(count, null);"+
							"}" +
							"catch(error) " +
							"{"+
								"to.add(newOption);"+ // for Internet Explorer
								"from.remove(count);"+
							"}"+
						"count--;"+
						"}"+
					"}"+
				"}"+
						"function doSubmit()" +
						"{" +
							"document.editForm.submit();" +
						"}"+
						"function SelectedDepSelectAll()" +
						"{" +
							"var select = document.getElementById( 'SelectedDep' );"+
							"for ( var i = 0, l = select.options.length, o; i < l; i++ )"+
							"{"+
								"o = select.options[i];"+
								"o.selected = true;"+
							"}"+

						"}"+
						

				"</SCRIPT>"+
				"<h1>Rediger profiler</h1>");
		if (selectedDeps != null){
			for (int i = 0; i < selectedDeps.length; i++)
			{
				out.println(selectedDeps[i]);
			}
		}

		out.println("<hr>"+
				"<table border = 0>"+
				"<form method='POST' name='editForm' action='EditProfileNotLoggedin'>"+
				"<tr>"+
				"<td>Navn:</td> <td><input type=\"text\" name=\"name\" value='"+firstname+"' /><td>"+ 
				"</tr>"+
				"<tr>"+
				"<td>Mellemnavn(e):</td> <td> <input type=\"text\" name=\"middlename\" value='"+middlename+"' /> </td>"+
				"</tr>"+
				"<tr>"+
				"<td>Efternavn: </td><td><input type=\"text\" name=\"surname\" value='"+surname+"' /> </td>"+
				"</tr>"+
				"<tr>"+
				"<td>Telefon nummer: </td> <td> <input type=\"text\" name=\"phone\" value='"+phone+"'  /> </td>"+
				"</tr>"+
				"<tr>"+
				"<td><select name=\"Rolle\">"+
				"<option value=\"Admin\""); if (pRole == 0) { out.println("selected=\"selected\"");} out.println(">Administrator</option>"+
						"<option value=\"Guardian\""); if (pRole == 1) { out.println("selected=\"selected\"");} out.println(">Pedagog</option>"+
								"<option value=\"Parent\""); if (pRole == 2) { out.println("selected=\"selected\"");} out.println(">Forældre</option>"+
										"<option value=\"Child\""); if (pRole == 3) { out.println("selected=\"selected\"");} out.println(">Barn</option>"+
												"</select></td> <br />"+
												"</tr>"+
												"<tr>" +
												"</tr>"+
												"</table>"+
												"<table>");
										out.println("<tr>" +
												"<td><hr></td><td><hr></td><td><hr></td>"+
												"</tr>"+
												"<tr><td>Mulige afdelinger:<br><select name=\"AllDep\" id=\"All\" size=\"4\" multiple=\"multiple\">");
										for (Department d : possibleDepartments) {
											out.println("<option value=\""+d.getID()+"\">"+d.getName()+"</option>");

										}
										out.println("</select></td>"+
												"<td> <input type=\"button\" value=\"Tilføj\" onclick=\"listbox_move('All', 'Selected');\"><br />"+
												"<input type=\"button\" value=\"Fjern\" onclick=\"listbox_move('Selected', 'All');\"> <br />"+ 
												"<input type=\"button\" value=\"Fjern alle\"></td>"+
												"<td>Med i afdelinger:<br><select name=\"SelectedDep\" id=\"Selected\" size=\"4\" multiple=\"multiple\">");

										for (Department d : currentDepartments) {
											out.println("<option value=\""+d.getID()+"\">"+d.getName()+"</option>");
										}

										out.println("</select></td>"+
												"</tr>");



										if (isGuardian)
										{
											out.println("<tr>"+
													"<td>Mulige Børn:<br><select name=\"AllDep\" id=\"AllKids\" size=\"4\" multiple=\"multiple\">");
											for (Profile p : possibleChild) {
												out.println("<option value=\""+p.getId()+"\">"+p.getName()+"</option>");

											}
											out.println("</select></td>"+
													"<td> <input type=\"button\" value=\"Tilføj\" onclick=\"listbox_move('AllKids', 'Selected1');\"><br />"+
													"<input type=\"button\" value=\"Fjern\" onclick=\"listbox_move('Selected1', 'AllKids');\"> <br />"+ 
													"<input type=\"button\" value=\"Fjern alle\"></td>"+
													"<td>Pædagog for:<br><select name=\"SelectedDep\" id=\"Selected1\" size=\"4\" multiple=\"multiple\">");

											for (Profile p : possibleChild) {
												out.println("<option value=\""+p.getId()+"\">"+p.getName()+"</option>");
											}

											out.println("</select></td>"+
													"</tr>");
										}



										out.println("<tr>" +
												"<td><hr></td><td><hr></td><td><hr></td>"+
												"</tr>");
										out.println("</table>" +
												"<table>" +
												"<tr>" +
												"<td>Adgang til apps:</td><td>");
										//This section needs optimization!
										for (App app : possibleApps) {
											boolean hasBeenPrinted = false;
											for (App hasApp : currentApps) {
												if (app.getID() == hasApp.getID())
												{
													hasBeenPrinted = true;
													out.println("<input type='checkbox' name='apps' value='"+app.getID()+"' checked=true>"+app.getName()+"<br>");
												}
											}
											if (!hasBeenPrinted)
											{
												out.println("<input type='checkbox' name='apps' value='"+app.getID()+"'>"+app.getName()+"<br>");
											}
											else
											{
												hasBeenPrinted = false;
											}

										}
										///The above section needs optimization

										out.println("<tr>"+
												"<td>Brugernavn: </td><td><input type=\"text\" name=\"brugernavn\" disabled=true value="+username+"  /></td>"+ 
												"</tr>"+
												"<tr>"+
												"<td>Gamle kodeord:</td><td><input type=\"password\" name=\"oldpassword\"></td>"+
												"</tr>"+
												"<tr>"+
												"<td>Kodeord:</td><td> <input type=\"password\" name=\"password\" /> </td>"+
												"</tr>"+
												"<tr>"+
												"<td>Gentag kodeord:</td> <td> <input type=\"password\" name=\"password2\" /> </td>"+
												"</tr>"+
												"<tr>"+
												"<td></td><td><input type=\"button\" onClick=\"SelectedDepSelectAll(); doSubmit();\" value=\"Gem\">"+
												"<input type=\"button\" value=\"Fortryd\"></td>"+
												"</tr>"+
												"</form>"+
												"</div>"+
												"</body>"+
												"</html>");

										/*
		String userOutput = (String) session.getAttribute("ADDPROFILERESPONSE");
		if (userOutput != null && !userOutput.equals(""))
			out.print(userOutput+"<br>");
		out.println("</center>" +
				"<br>");

		/*if(appList != null)
		{
			for (int i=0;i<appList.length; i++) {
				out.println("<li>" + appList[i]);
			}
		}


		out.println("<table border='0'>" +
				"<form method='POST' name='addForm1' action='AddProfile'>");
		if (!departments.isEmpty())
		{
			out.println("<tr>" +
					"<td>Afdeling</td><td>"+
					"<select name='dept'>");
			for (Department d : departments) {
				out.println("<option value='"+d.getID()+"'");
				if(deptField.equals(String.valueOf(d.getID())))
					out.print(" selected='true' " );
				out.print("'>"+d.getName());
			}
			out.println("</select>"+
					"</tr>");
		}
		out.println("<tr>" +
				"<td>Navn:</td> <td><input type='text' name='firstname' value='"+firstname+"'/>");

		if (firstname != null && firstname.equals("") && hasTriedToAdd.equals("1"))
			out.println("<font color='red'>Indtast navn</font>");

		out.println("</td></tr>" +
				"<tr>" +
				"<td>Mellemnavn(e):</td> <td> <input type='text' name='middlename' value='"+middlename+"' /> </td>" +
				"</tr>" +
				"<tr>" +
				"<td>Efternavn: </td><td><input type='text' name='surname'value='"+surname+"' />");

		if (surname != null && surname.equals("") && hasTriedToAdd.equals("1"))
			out.println("<font color='red'>Indtast efternavn</font>");

		out.println("</td></tr>" +
				"<tr>" +
				"<td>Telefon nummer: </td> <td> <input type='text' name='phone' value='"+phone+"' />");

		if (phone != null && phone.equals("") && hasTriedToAdd.equals("1"))
			out.println("<font color='red'>Indtast telefon nummer</font>");

		out.println("</td></tr>" +
				"<tr>" +
				"<td><select name='prole'>" +
				"<option value='0' "); 
		if(role.equals("0"))
			out.print("selected='true'");
		out.print(">Administrator</option>" +
				"<option value='1' ");
		if(role.equals("1"))
			out.print("selected='true'");
		out.print(">Pedagog</option>" +
				"<option value='2' ");
		if(role.equals("2"))
			out.print("selected='true'");
		out.print(">Forældre</option>" +
				"<option value='3' ");
		if(role.equals("3"))
			out.print("selected='true'");
		out.print(">Barn</option>" +
				"</select></td> " +
				"</tr>" +
				"<tr>" +
				"<td><input type='hidden' name='MAX_FILE_SIZE' value='100' />" +
				"<input name='file' type='file' /></td> " +
				"</tr>" +
				"<tr>" +
				"<td>Brugernavn: </td><td><input type='text' name='username' value='"+username+"' />");
		if (username != null && username.equals("") && hasTriedToAdd.equals("1"))
			out.println("<font color='red'>Indtast brugernavn</font>");
		out.println("</td></tr>" +
				"<tr>" +
				"<td>Kodeord:</td><td> <input type='password' name='password' />");
		if (password != null && password.equals("") && hasTriedToAdd.equals("1"))
			out.println("<font color='red'>Indtast kodeord</font>");
		out.println("</td></tr>" +
				"<tr>" +
				"<td>Gentag kodeord:</td> <td> <input type='password' name='password2' />");
		if (password2 != null && password2.equals("") && hasTriedToAdd.equals("1"))
			out.println("<font color='red'>Indtast kodeord</font>");
		out.println("</td></tr>" +
				"<tr>"+
				"<td><hr></td><td><hr></td>"+
				"</tr>" +
				"<tr>" +
				"<td>Adgang til apps</td><td>");
		for (App app : apps) {
			if (appLink.contains(app.getID()))
			{
				out.println("<input type='checkbox' name=\"appList\" value=\""+app.getID()+"\" checked='true'/>" + app.getName()+"<br>");
			}
			else
			{
				out.println("<input type='checkbox' name=\"appList\" value=\""+app.getID()+"\"/>" + app.getName()+"<br>");
			}

		}
		out.println("</td></tr>" +
				"<tr>" +
				"<td></td><td><input type='button' onClick=\"updateTryToAdd(); submitform();\" value='Tilføj'>" +
				"<input type='button' value='Fortryd'></td>" +
				"</tr>" +
				"<input type='hidden' name='tryToAdd' value='0'>" +
				"</form>" +
				"</table>" + 
				"</div>"+
				"</body>" +
				"</html>");
										 */
	}

	/**
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	private void getAllDepts(Department d, String star) throws ServletException
	{
		star = star + "*";
		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection 
					("jdbc:mysql://172.25.11.65:3306/04","eder","123456");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from Department "+
					"where idDepartment in "+
					"(select idSubdepartment from HasSubDepartment "+
					"where idDepartment = "+d.getID()+");");
			// displaying records
			while(rs.next()){
				//out.print("\t\t\t");
				Department d1 = new Department(rs.getInt("idDepartment"),star +rs.getString("name"), rs.getString("address"), (int)rs.getInt("phone"), rs.getString("email"));
				possibleDepartments.add(d1);
				getAllDepts(d1,star);
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
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Husk: SelectedDep
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		selectedDeps=request.getParameterValues("SelectedDep");
		for(int i=0;i<selectedDeps.length;i++){
			out.println(selectedDeps[i]+"<br>");
		}
		//doGet(request, response); 
	}

}
