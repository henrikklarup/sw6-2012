

import java.io.IOException;
import java.io.PrintWriter;

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
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Savannah 1.101 </title>");
		out.println("<link rel='stylesheet' type='text/css' href='/css/SavannahStyle.css' />");
		out.println("</head>");
		out.println("<body>");
		out.println("<center><h2> Velkommen !</h2>");
		out.println("<br>");
		out.println("<hr>");
		out.println("Vælg handling");
		out.println("<p>");
		out.println("<img src='../images/profileicon.jpg'>");
		out.println("Profiler");
		out.println("<p>");
		out.println("Tilføj Rediger Slet");
		out.println("</center>");
		out.println("<hr>");
		out.println("<footer> Savannah v. 1.0.0 (C)opyright me!</footer>");
		//out.println("<form method='POST' action='main'>\n" +
		//"<input type='hidden' name='Logout'>"+
		//"<input type='submit' value='Logout'>\n" + "</form>"); 
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
		out.println("Logger ud... Vent venligst.");
		session.removeAttribute("USER");
		
		
		response.setHeader("Refresh", "1");
	}

}
