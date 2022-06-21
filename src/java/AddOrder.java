/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dao.OrderDao;
import dao.ProductDao;
import databaseOperations.db_connection;
import static inputValidation.Regex.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(urlPatterns = {"/add-order"})
public class AddOrder extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            String card_name = request.getParameter("name");
            String card_number = request.getParameter("credit_num");
            String card_cvv = request.getParameter("CVV");
            //Input validation with REGEX of credit card input at checkout
            if (!cardNameVal(card_name) || !cardNumberVal(card_number) || !cardCvvVal(card_cvv)) {
                out.println("<h3 style='color:crimson; text-align:center'>"
                        + "Incorrect input please try again!<a href='checkout.jsp'>Back</a></h3>");

            } else {
                //Adding the cart list content to the table of orders in the database
                int card = Integer.parseInt(card_number);
                ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                HttpSession session = request.getSession();
                String username = (String) session.getAttribute("USERNAME");
                ProductDao pDao;
                List<Cart> cartProduct = null;
                //Accessing DAO of product
                pDao = new ProductDao(db_connection.getConnection());

                cartProduct = pDao.productsCart(cart_list);

                OrderDao add;
                //Adding the order of the specific item from cart with its elements
                add = new OrderDao(db_connection.getConnection());
                add.OrderAdd((ArrayList<Cart>) cartProduct, username);
                add.UpdateStock((ArrayList<Cart>) cartProduct);
                session.setAttribute("cart-list", null);
                out.println("<h3 style='color:crimson; text-align:center'>"
                        + "Order successful! Thank you for buying at our shop!<a href='index.jsp'>Home</a></h3>");
                //response.sendRedirect("index.jsp");

            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AddOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
