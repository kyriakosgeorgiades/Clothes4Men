/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import databaseOperations.Hashing;
import databaseOperations.Queries;
import databaseOperations.Validate;
import databaseOperations.db_connection;
import static inputValidation.Regex.emailVal;
import static inputValidation.Regex.passwordVal;
import static inputValidation.Regex.usernameVal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author kakos
 */
@WebServlet(name = "UpdatePassword", urlPatterns = {"/update-password"})
public class UpdatePassword extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String paramName;
        HttpSession session = request.getSession();
//        Enumeration<String> parameterNames = request.getParameterNames();
//        while (parameterNames.hasMoreElements()) {
//            paramName = parameterNames.nextElement();
//            System.out.print(paramName);
//        }
        
        String oldpassword = request.getParameter("oldpass");
        String newpassword = request.getParameter("newpass");
        String username = (String) session.getAttribute("USERNAME");
        int id_user = (int) session.getAttribute("ID");
        //Input validation with REGEX
        if (!passwordVal(oldpassword) || !passwordVal(newpassword)) {

            out.println("<h3 style='color:crimson; text-align:center'>"
                    + "New Password must meet the requirments shown!<a href='myaccount.jsp'>Back</a></h3>");

        } else {
            try {
                //Hashing the old password to check if the user is valid
                //before updating his password
                oldpassword = Hashing.toHexString(Hashing.getSHA(oldpassword));
                if (Validate.checkUser(username, oldpassword)) {
                    out.println("CORRECT!");
                    //Once validated hash the new pass and update it in the db
                    newpassword = Hashing.toHexString(Hashing.getSHA(newpassword));
                    Queries q = new Queries(db_connection.getConnection());
                    q.UpdatePass(id_user, newpassword);
                    response.sendRedirect("myaccount.jsp?id="+id_user);

                } else {

                    out.println("<h3 style='color:crimson; text-align:center'>"
                            + "Wrong old password!<a href='myaccount.jsp?id="+id_user+"'>"+"Back</a></h3>");

                }
            } catch (Exception e2) {
                System.out.println(e2);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
