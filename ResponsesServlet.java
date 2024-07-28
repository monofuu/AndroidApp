
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/select") 
public class ResponsesServlet extends HttpServlet {


   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>Your Response</title></head>");
      out.println("<body>");

      try (
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx"); 
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         Statement stmt = conn.createStatement();
      ) {
         String[] questionNos = request.getParameterValues("questionNo");
         if (questionNos != null) {
            String sqlStr;
            int count;
                String choice = request.getParameter("choice");
                sqlStr = "INSERT INTO responses (questionNo, choice) VALUES (1, '" + choice + "');";
                out.println("<p>" + sqlStr + "</p>");  // for debugging
                count = stmt.executeUpdate(sqlStr);
                out.println("<p>" + count + " records inserted.</p>");
                out.println("<h3>Your answer for question" + questionNos[i]
                     + " has been recorded.</h3>");
            }
            out.println("<h3>Thank you.<h3>");
         } else {
            out.println("<h3>Please select a question number!</h3>");
         }
      }
 
      out.println("</body></html>");
      out.close();
}
