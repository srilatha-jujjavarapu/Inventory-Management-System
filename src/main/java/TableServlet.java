import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class TableServlet
 */
@WebServlet("/TableServlet")
public class TableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TableServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter output = response.getWriter();
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "sri123");
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery("select * from product");
            
            // Begin HTML and CSS
            output.println("<html><head>");
            output.println("<style>");
            output.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0; }");
            output.println("nav { background-color: #333; padding: 10px; }");
            output.println("nav ul { list-style-type: none; padding: 0; margin: 0; }");
            output.println("nav ul li { display: inline; margin-right: 10px; }");
            output.println("nav ul li a { color: white; text-decoration: none; padding: 10px 15px; display: inline-block; }");
            output.println("nav ul li a:hover { background-color: #575757; }");
            output.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; background: #ffffff; box-shadow: 0 4px 8px rgba(0,0,0,0.1); border-radius: 8px; }");
            output.println("th, td { padding: 15px; text-align: center; }");
            output.println("th { background-color: #d6a6d4; color: white; }");
            output.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            output.println("tr:hover { background-color: #e0e0e0; }");
            output.println("table, th, td { border: 1px solid #ddd; }");
            output.println("</style>");
            output.println("</head><body>");
            
            // Navigation Bar
            output.println("<nav>");
            output.println("<ul>");
            output.println("<li><a href='admin.html'>Home</a></li>");
            output.println("</ul>");
            output.println("</nav>");
            
            output.println("<table>");
            output.println("<tr><th>Id</th><th>Name</th><th>Price</th><th>Quantity</th></tr>");
            while (rs.next()) {
                output.println("<tr><td>" + rs.getInt(1) + "</td><td>" + rs.getString(2) + "</td><td>" + rs.getInt(3) + "</td><td>" + rs.getInt(4) + "</td></tr>");
            }
            output.println("</table>");
            
            output.println("</body></html>");
            
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
