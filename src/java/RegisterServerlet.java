
import databaseOperations.Hashing;
import models.User;
import databaseOperations.Queries;
import com.mysql.cj.protocol.Resultset;
import databaseOperations.db_connection;
import static inputValidation.Regex.*;
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "RegisterServerlet", urlPatterns = {"/RegisterServerlet"})
public class RegisterServerlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try ( PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirm_pass = request.getParameter("con_password");
            //Input validation with REGEX
            if (!usernameVal(username) || !emailVal(email) || !passwordVal(password)) {

                out.println("<h3 style='color:crimson; text-align:center'>"
                        + "One of the inputs were not correct format!<a href='register.html'>Back</a></h3>");

            } else {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/coursework_final?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "");

                try {
                    PreparedStatement ps = con.prepareStatement(
                            "insert into users (username,email,password) values(?,?,?)");
                    //Checking if the password is given the second time correct
                    if (password.equals(confirm_pass)) {
                        //Security hashing to store into the db the hashed pass
                        password = Hashing.toHexString(Hashing.getSHA(password));
                        ps.setString(1, username);
                        ps.setString(2, email);
                        ps.setString(3, password);

                        ps.executeUpdate();
                        Queries q = new Queries(db_connection.getConnection());
                        User user = q.getRoleId(username);
                        HttpSession session = request.getSession(true);
                        //Setting the attributes of this session based on the user
                        //That just register. Same logic follows for login at the
                        //other servlet file of "login.java"
                        session.setAttribute("ID", user.getID());
                        session.setAttribute("ROLE", user.getRole());
                        session.setAttribute("USERNAME", user.getUsername());
                        response.sendRedirect("index.jsp");

                    } else {
                        out.println("<h3 style='color:crimson; text-align:center'>"
                            + "Passwords do not match!<a href='register.html'>Back</a></h3>");
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                    out.println("<h3 style='color:crimson; text-align:center'>"
                            + "Username or Email already exists!<a href='register.html'>Back</a></h3>");
                }
            }
        } catch (Exception e2) {
            System.out.println(e2);
        }
    }
}
