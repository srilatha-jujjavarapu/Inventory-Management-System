import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BuyServlet
 */
public class BuyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter output = response.getWriter();
        
        String name = request.getParameter("pname");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "sri123");
            System.out.println("Connection established");
            
            PreparedStatement st = conn.prepareStatement("select * from product");
            ResultSet rs = st.executeQuery();
            int q1 = 0, p = 0;
            while (rs.next()) {
                if (rs.getString(2).equals(name)) {
                    q1 = rs.getInt(4);
                    p = rs.getInt(3);
                    break;
                }
            }
            
            
            // Adding CSS styles
            output.println("<html><head>");
            output.println("<style>");
            output.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            output.println(".message { border: 1px solid #ddd; padding: 15px; border-radius: 5px; }");
            output.println(".message.success { background-color: #d4edda; color: #155724; }");
            output.println(".message.error { background-color: #f8d7da; color: #721c24; }");
            output.println("</style>");
            output.println("</head><body>");
            if(q1>=quantity) {
            	PreparedStatement pst = conn.prepareStatement("update product set quantity=? where pname=?");
                pst.setInt(1, (q1 - quantity));
                pst.setString(2, name);
                pst.executeUpdate();
            output.println("<div class='message success'>");
            output.println("Thank you!<br>");
            output.println("Total bill: $" + (quantity * p));
            output.println("</div>");
            }
            else {
            	output.println("<div class='message error'>");
            	output.println("OOPS!!<br>");
                output.println("Quantity not available");
                output.println("</div>");
            }
            output.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            output.println("<html><body>");
            output.println("<div class='message error'>");
            output.println("An error occurred: " + e.getMessage());
            output.println("</div>");
            output.println("</body></html>");
        }
    }
}
