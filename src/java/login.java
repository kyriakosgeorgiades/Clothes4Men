
import databaseOperations.Hashing;
import models.User;
import databaseOperations.Queries;
import databaseOperations.Validate;
import databaseOperations.db_connection;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            //Hashing the input password to be compared with the one stored
            //in the database
            password = Hashing.toHexString(Hashing.getSHA(password));
            if (Validate.checkUser(username, password)) {
                //out.println("CORRECT!");
                RequestDispatcher rs = request.getRequestDispatcher("/index.jsp");
                Queries q = new Queries(db_connection.getConnection());
                User user = q.getRoleId(username);
                HttpSession session = request.getSession(true);
                //Setting the session attributes as the user logs in.
                session.setAttribute("ID", user.getID());
                session.setAttribute("ROLE", user.getRole());
                session.setAttribute("USERNAME", user.getUsername());
                rs.forward(request, response);
            } else {

                out.println("<h3 style='color:crimson; text-align:center'>"
                        + "Username or Password incorrectly!<a href='login.jsp'>Back</a></h3>");

            }
        } catch (Exception e2) {
            System.out.println(e2);
        }

    }

}
