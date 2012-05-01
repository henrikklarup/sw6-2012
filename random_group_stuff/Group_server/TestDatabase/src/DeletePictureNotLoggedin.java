

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.buf.UDecoder;

/**
 * Servlet implementation class DeletePictureNotLoggedin
 */
@WebServlet("/DeletePictureNotLoggedin")
public class DeletePictureNotLoggedin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<Media> medias = new ArrayList<Media>();
	ArrayList<Tag> tags = new ArrayList<Tag>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeletePictureNotLoggedin() {
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
		String userID = (String) session.getAttribute("PROFILEIDTODELETEPIC");

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://172.25.11.65:3306/04", "eder", "123456");

			Statement stmt0 = con.createStatement();
			ResultSet rs0 = stmt0
					.executeQuery("select * from Media where ownerID = '"
							+ userID + "';");
			while (rs0.next()) {
				boolean mPublic;
				int temp = rs0.getInt("mPublic");
				if (temp == 0)
					mPublic = false;
				else
					mPublic = true;
				medias.add(new Media(rs0.getInt("idMedia"), rs0.getString("mPath"), rs0.getString("name"),mPublic,rs0.getInt("mType"), rs0.getInt("ownerID")));
			}
			
			for (Media m : medias) {
				Statement stmt1 = con.createStatement();
				ResultSet rs1 = stmt1
						.executeQuery("select idTags, caption from Tags "+
										"where idTags in (select idTag from HasTag " + 
										"where idMedia ="+m.getIdMedia()+");");
				while (rs1.next()) {
					tags.add(new Tag(m.getIdMedia(), rs1.getInt("idTags"), rs1.getString("caption")));
				}
			}
		} catch (SQLException e) {
			throw new ServletException(e.getMessage()
					+ " Servlet Could not display records. ", e);
		} catch (ClassNotFoundException e) {
			throw new ServletException("JDBC Driver not found.", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException e) {

			}
		}
		
		
		out.println("<html>" +
				"<head>" +
				"</head>" +
				"<body>");
		for (Media m : medias) {
			out.println(m.getIdMedia() + ": " + m.getName() +"<br>");
			for (Tag t : tags) {
				if (t.getIdMedia() == m.getIdMedia())
				out.println(t.getCaption() + " - ");
			}
			out.println("<br>");
			
		}
		out.println("</body>" +
				"</html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
