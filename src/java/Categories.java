/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kakos
 */
@WebServlet(urlPatterns = {"/categories"})
public class Categories extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String category = request.getParameter("category");
        System.out.println("I AM THE category: " + category);

        HttpSession session = request.getSession();
        System.out.println("Session attribute inside serverlet: " + session.getAttribute("category"));
        //Setting the session attribute based on Category
        //For the puprose of display of the products on the home page
        switch (category) {
            case "Jeans":
                session.setAttribute("category", "Jeans");
                break;
            case "Shirts":
                session.setAttribute("category", "Shirts");
                break;
            case "Tshirts":
                session.setAttribute("category", "Tshirts");
                break;
        }
        //System.out.println("I AM THE ATTRIBUTE AT NOT NULL: " + session.getAttribute("category"));
        response.sendRedirect("index.jsp");

    }
}
