// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;             // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;             // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/display")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class DisplayResults extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
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
      out.println("<head>");
      out.println("<title>Results</title>");
      out.println("<meta charset='UTF-8'>");
      out.println("<link rel='preconnect' href='https://fonts.googleapis.com'>");
      out.println("<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>");
      out.println("<link href='https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap' rel='stylesheet'>");
      out.println("<meta name = 'viewport' content = 'width=device-width, initial-scale=1.0'>");
      out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js'></script>");

      out.println("<link rel='stylesheet' href='button.css'>");
      out.println("<link rel='stylesheet' href='chart.css'>");   

      out.println("</head>");
      out.println("<body>");

      out.println("<div class='centerboxgraph'>");
      out.println("<canvas id='myChart' style='width:100%;max-width:600px'></canvas>");
      out.println("<script>");

      out.println("var xValues = ['1', '2', '3', '4'];");

      try (
        // Step 1: Allocate a database 'Connection' object
        Connection conn = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/clicker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
              "myuser", "1234");   // For MySQL
              // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

        // Step 2: Allocate a 'Statement' object in the Connection
        Statement stmt = conn.createStatement();
     ) {
        // Step 3: Execute a SQL SELECT query
        String sqlStr1, sqlStr2, sqlStr3, sqlStr4;
        sqlStr1 = "SELECT COUNT(*) AS count FROM responses WHERE questionNo=1 AND choice = '1'";
        sqlStr2 = "SELECT COUNT(*) AS count FROM responses WHERE questionNo=1 AND choice = '2'";        
        sqlStr3 = "SELECT COUNT(*) AS count FROM responses WHERE questionNo=1 AND choice = '3'";
        sqlStr4 = "SELECT COUNT(*) AS count FROM responses WHERE questionNo=1 AND choice = '4'";
        ResultSet rset1 = stmt.executeQuery(sqlStr1); 
        ResultSet rset2 = stmt.executeQuery(sqlStr2); 
        ResultSet rset3 = stmt.executeQuery(sqlStr3); 
        ResultSet rset4 = stmt.executeQuery(sqlStr4); 

        out.println("var yValues = [" + "'" + rset1.getString("count") + "'," + "'" + rset2.getString("count") + "'," + 
        "'" + rset3.getString("count") + "'," + "'" + rset4.getString("count") + "'];");

        out.println("new Chart('myChart', {type: 'bar', data: {labels: xValues,datasets: [{backgroundColor: barColors,data: yValues}]},options: {legend: {display: false},title: {display: true,text: 'Answers'}}});</script>");
        }
  
            catch(Exception ex) {
                out.println("<p>Error: " + ex.getMessage() + "</p>");
                out.println("<p>Check Tomcat console for details.</p>");
                ex.printStackTrace();
            }
             // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
            out.println("</div>");
            out.println("</body></html>");
            out.close();          

        }

  }
 


