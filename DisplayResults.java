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
      out.println("<meta charset='UTF-8'>");
      out.println("<link rel='preconnect' href='https://fonts.googleapis.com'>");
      out.println("<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>");
      out.println("<link href='https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap' rel='stylesheet'>");
      out.println("<meta name = 'viewport' content = 'width=device-width, initial-scale=1.0'>");
      out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js'></script>");

      out.println("<link rel='stylesheet' href='button.css'>");
      out.println("<link rel='stylesheet' href='boxes.css'>");   

      out.println("</head>");
      out.println("<title>Results</title>");
      out.println("<body>");
      out.println("<a href='http://localhost:9999/clicker/end.html' class='button'>Next</a>");
      out.println("<center><p style = 'font-size: 50px;'><b>What is 1 + 1?</b></p></center>");
      out.println("<div class='centerboxgraph'>");
      out.println("<canvas id='myChart' style='width:100%;max-width:600px'></canvas>");
      out.println("<script>");


      out.println("var barColors = ['red', 'blue','yellow','green']");


     try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clickerdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
     "myuser", "1234")) {
        Statement stmt = conn.createStatement();

        String sql = "SELECT choice, COUNT(*) AS count FROM responses " +
                     "WHERE questionNo = 1 GROUP BY choice";
        ResultSet rs = stmt.executeQuery(sql);

        StringBuilder xValuesBuilder = new StringBuilder("['A', 'B', 'C', 'D']");
        StringBuilder yValuesBuilder = new StringBuilder("[");

        while (rs.next()) {
            yValuesBuilder.append(rs.getInt("count") + ",");
        }

        // Remove trailing comma
        xValuesBuilder.deleteCharAt(xValuesBuilder.length() - 1);
        yValuesBuilder.deleteCharAt(yValuesBuilder.length() - 1);

        
        xValuesBuilder.append("]");
        yValuesBuilder.append("]");

        String xValuesString = xValuesBuilder.toString();
        String yValuesString = yValuesBuilder.toString();

        out.println("var xValues = " + xValuesString + ";");
        out.println("var yValues = " + yValuesString + ";");

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
 


