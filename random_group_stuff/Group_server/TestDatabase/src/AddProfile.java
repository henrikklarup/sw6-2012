

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Testservlet
 */
public class Testservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Testservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(
		"<html>" +
		"<head>" +
		"</head>" +
		"<body>" +
		"<h1> Tilføj profil </h1>" +
		"<table border='0'>" +
		"<form>" +
		"<tr>" +
		"<td>Navn:</td> <td><input type='text' name='name' /><td> <br />" +
		"</tr>" +
		"<tr>" +
		"<td>Mellemnavn(e):</td> <td> <input type='text' name='mellemnavn(e)' /> </td><br />" +
		"</tr>" +
		"<tr>" +
		"<td>Efternavn: </td><td><input type='text' name='efternavn' /> </td><br />" +
		"</tr>" +
		"<tr>" +
		"<td>Telefon nummer: </td> <td> <input type='text' name='tlfnr' /> </td><br />" +
		"</tr>" +
		"</form>" +
		"<form action=''>" +
		"<tr>" +
		"<td><select name='Rolle'>" +
		"<option value='Admin' selected='selected'>Administrator</option>" +
		"<option value='Guardian'>Pedagog</option>" +
		"<option value='Parent'>Forældre</option>" +
		"<option value='Child'>Barn</option>" +
		"</select></td> <br />" +
		"</tr>" +
		"<tr>" +
		"<td><input type='hidden' name='MAX_FILE_SIZE' value='100' />" +
		"<input name='file' type='file' /></td> <br />" +
		"</tr>" +
		"<tr>" +
		"<td>Brugernavn: </td><td><input type='text' name='brugernavn' /></td> <br />" +
		"</tr>" +
		"<tr>" +
		"<td>Kodeord:</td><td> <input type='password' name='pwd' /> </td><br />" +
		"</tr>" +
		"<tr>" +
		"<td>Gentag kodeord:</td> <td> <input type='password' name='pwd' /> </td><br />" +
		"</tr>" +
		"<form action=''>" +
		"<tr>" +
		"<td><input type='button' value='Tilføj></td>" +
		"<td><input type='button' value='Fortryd'></td>" +
		"</tr>" +
		"</form>" +
		"</form>" +
		"</table>" + 
		"</body>" +
		"</html>"
				
		);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
