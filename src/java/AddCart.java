/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Cart;

/**
 *
 * @author kakos
 */
@WebServlet(urlPatterns = {"/add-cart"})
public class AddCart extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            //Array of model Cart
            ArrayList<Cart> cartList = new ArrayList<>();
            //Creating Cart object and setting the ID product from the parameter
            int id = Integer.parseInt(request.getParameter("id"));
            String option = request.getParameter("op");
            Cart cs = new Cart();
            cs.setId(id);
            cs.setCount(1);
            int counter;
            //Request the session which the cart logic is based on
            HttpSession session = request.getSession();
            ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");

            //If the cart is empty add the product ID and set the attribute
            if (cart_list == null) {
                cartList.add(cs);
                session.setAttribute("cart-list", cartList);
                if (option.equals("cart")) {
                    response.sendRedirect("index.jsp");
                } else {
                    response.sendRedirect("checkout.jsp");
                }
            } else {
                //Checking if the selected product already exsists in the cart.
                cartList = cart_list;
                boolean exists = false;
                
                for (Cart c : cart_list) {
                    
                    if (c.getId() == id && option.equals("cart")) {
                        exists = true;
                        counter = c.getCount();
                        counter++;
                        c.setCount(counter);
                        response.sendRedirect("index.jsp");
                    } else if (c.getId() == id && option.equals("buy")) {
                        exists = true;
                        response.sendRedirect("checkout.jsp");
                    }
                }
                //Add it to the list.
                if (!exists) {
                    cartList.add(cs);
                    if (option.equals("cart")) {
                        response.sendRedirect("index.jsp");
                    } else {
                        response.sendRedirect("checkout.jsp");
                    }
                }
            }
        }
    }
}
