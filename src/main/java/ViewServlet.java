import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewServlet
 */
public class ViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html");

        PrintWriter output = response.getWriter();
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "sri123");
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery("select * from product");
            
            // Adding CSS styles
            output.println("<html><head>");
            output.println("<style>");
            output.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
            output.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: #fff; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            output.println("th, td { padding: 12px 15px; border: 1px solid #ddd; text-align: center; }");
            output.println("th { background-color: #009879; color: #fff; }");
            output.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            output.println("tr:hover { background-color: #f1f1f1; }");
            output.println(".container { margin-bottom: 50px; }");
            output.println("center { display: block; }");
            output.println("h1 { text-align: center; color: #333; }");
            output.println("</style>");
            output.println("</head><body>");
            output.println("<div class='container'>");
            output.println("<h1>Product List</h1>");
            output.println("<table>");
            output.println("<tr><th>Id</th><th>Name</th><th>Price</th><th>Quantity</th></tr>");
            while (rs.next()) {
                output.println("<tr><td>" + rs.getInt(1) + "</td><td>" + rs.getString(2) + "</td><td>" + rs.getInt(3) + "</td><td>" + rs.getInt(4) + "</td></tr>");
            }
            output.println("</table>");
            output.println("</div>");
            output.println("<div>");
            RequestDispatcher rd = request.getRequestDispatcher("buy.html");
            rd.include(request, response);
            output.println("</div>");
            output.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
