
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Killing the session when users logs out
        request.getSession().invalidate();
        //NULL the attributes of ROLE and ID

//        session.setAttribute("ROLE", null);
//        session.setAttribute("ID", null);
//        session.setAttribute("cart-list", null);
//        session.setAttribute("USERNAME", null);
        response.sendRedirect("index.jsp");
    }

}
