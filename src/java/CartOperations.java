/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Cart;
import com.google.gson.Gson;

/**
 *
 * @author kakos
 */
@WebServlet(urlPatterns = {"/cart-operations"})
public class CartOperations extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //All cart operations in the same Servlet
        String operation = request.getParameter("operation");
        int id = Integer.parseInt(request.getParameter("id"));
        int counter;

        ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
        try {
            if (id >= 1 && operation != null) {
                //Case of delete the item from cart
                if (operation.equals("delete") && cart_list != null) {
                    for (Cart item : cart_list) {
                        if (item.getId() == id) {
                            cart_list.remove(cart_list.indexOf(item));
                            System.out.println("EXIT");
                            break;
                        }
                    }
                    //response.sendRedirect("cart.jsp");
                    //Increasing the quantity of the selected item by 1
                } else if (!cart_list.isEmpty() && operation.equals("plus")) {
                    counter = 0;
                    for (Cart item : cart_list) {
                        if (item.getId() == id) {
                            counter = item.getCount();
                            counter++;
                            item.setCount(counter);
                            //response.sendRedirect("cart.jsp");
                        }
                    }
                    //Decreasing the quantity of the selected item by -1
                } else if (cart_list.size()>0 && operation.equals("minus")) {
                    counter = 0;
                    for (Cart item : cart_list) {
                        if (item.getId() == id) {
                            counter = item.getCount();
                            counter--;
                            if (counter == 0) {
                                cart_list.remove(cart_list.indexOf(item));
                                break;
                            }
                            item.setCount(counter);

                        }
                        
                    } //response.sendRedirect("cart.jsp");
                }
                response.sendRedirect("cart.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
