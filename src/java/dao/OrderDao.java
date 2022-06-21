/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import databaseOperations.Queries;
import databaseOperations.db_connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.*;
import models.Cart;
import models.Order;
import models.Product;
import models.User;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author kakos
 */
public class OrderDao {

    private Connection con;
    private String query;
    private PreparedStatement ps;
    private ResultSet rs;

    public OrderDao(Connection con) {
        this.con = con;
    }
    //Method to add an order from a given CART of a user.
    public void OrderAdd(ArrayList<Cart> cartList, String username) {
        try {
            if (!cartList.isEmpty()) {
                int lenght = 5;
                boolean useLetters = true;
                boolean useNumbers = true;
                String generatedString = RandomStringUtils.random(lenght, useLetters, useNumbers);
                Queries q = new Queries(db_connection.getConnection());
                User user;
                user = q.getRoleId(username);
                for (Cart product : cartList) {
                    query = "INSERT INTO orders (orderID,customerID,productID,quantity,item_price_total) values(?,?,?,?,?)";
                    ps = this.con.prepareStatement(query);
                    ps.setString(1, generatedString);
                    ps.setInt(2, user.getID());
                    ps.setInt(3, product.getId());
                    ps.setInt(4, product.getCount());
                    float totalOF = product.getPrice() * product.getCount();
                    ps.setFloat(5, totalOF);
                    int result = ps.executeUpdate();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
    //Method that returns all of the orders. This is for the admin/employee to track orders
    public List<Order> AllOrders() {
        List<Order> list = new ArrayList<>();
        try {
            query = "SELECT * from orders ORDER by orders.customerID DESC";
            ps = this.con.prepareStatement(query);
            rs = ps.executeQuery();
            //Getting all the orders from the productDAO
            while (rs.next()) {
                //ProductDao to get the require fields for display
                ProductDao productQuery = new ProductDao(this.con);
                //Order object to be used to add to the list
                Order order = new Order();
                int productId = rs.getInt("productID");
                int customerId = rs.getInt("customerID");
                Queries q = new Queries(db_connection.getConnection());
                String customer = q.UsernamebyID(customerId);
                Product product = productQuery.getProductByID(productId);
                order.setIdOrder(rs.getString("orderID"));
                order.setIdProduct(productId);//
                order.setIdUser(customerId);//
                order.setUsername(customer);
                order.setPrice(rs.getFloat("item_price_total"));//
                order.setDate(rs.getString("date"));//
                order.setName(product.getName());//
                order.setCount(rs.getInt("quantity"));//
                list.add(order);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    //This method displays the order only of the requested customer
    //Each customer can view only their OWN ex-orders
    public List<Order> CustomerOrders(int id) {
        List<Order> list = new ArrayList<>();
        try {
            query = "SELECT * from orders WHERE customerID=? ORDER by orders.customerID DESC";
            ps = this.con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            //Getting all the orders from the productDAO
            while (rs.next()) {
                //ProductDao to get the require fields for display
                ProductDao productQuery = new ProductDao(this.con);
                //Order object to be used to add to the list
                Order order = new Order();
                int productId = rs.getInt("productID");
                int customerId = rs.getInt("customerID");
                Queries q = new Queries(db_connection.getConnection());
                String customer = q.UsernamebyID(customerId);
                Product product = productQuery.getProductByID(productId);
                order.setIdOrder(rs.getString("orderID"));
                order.setIdProduct(productId);//
                order.setIdUser(customerId);//
                order.setUsername(customer);
                order.setPrice(rs.getFloat("item_price_total"));//
                order.setDate(rs.getString("date"));//
                order.setName(product.getName());//
                order.setCount(rs.getInt("quantity"));
                list.add(order);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    //When ever an order happens the stock of the products is reduced
    //based on of how many were bought of each item.
    public void UpdateStock(ArrayList<Cart> cartList) {
        try {
            for (Cart cart_item : cartList) {
                int update_stock = 0;
                query = "SELECT stock from products WHERE ID=?";
                ps = this.con.prepareStatement(query);
                ps.setInt(1, cart_item.getId());
                rs = ps.executeQuery();
                rs.next();
                update_stock = rs.getInt("stock") - cart_item.getCount();
                query = "UPDATE products SET stock=? WHERE ID=?";
                ps = this.con.prepareStatement(query);
                ps.setInt(1, update_stock);
                ps.setInt(2, cart_item.getId());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
