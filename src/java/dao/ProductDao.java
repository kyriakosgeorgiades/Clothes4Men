/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Cart;
import models.Product;

/**
 *
 * @author kakos
 */
public class ProductDao {

    private Connection con;
    private String query;
    private PreparedStatement ps;
    private ResultSet rs;

    public ProductDao(Connection con) {
        this.con = con;
    }

    //Getting the product from the DB based on category
    public List<Product> getProducts(String category) {
        List<Product> products = new ArrayList<Product>();

        try {
            query = "SELECT * FROM products WHERE category=?";
            ps = this.con.prepareStatement(query);
            if (category == null) {
                ps.setString(1, "Jeans");
            } else {
                ps.setString(1, category);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Product row = new Product();
                row.setId(rs.getInt("ID"));
                row.setName(rs.getString("name"));
                row.setPrice(rs.getFloat("price"));
                row.setDescription(rs.getString("description"));
                row.setStock(rs.getInt("stock"));
                row.setImage(rs.getString("filename"));
                row.setCategory(rs.getString("category"));

                products.add(row);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return products;
    }

    //Getting a product by its ID
    public Product getProductByID(int id) {
        Product row = new Product();

        try {
            query = "SELECT * FROM products WHERE ID=?";
            ps = this.con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                row.setId(rs.getInt("ID"));
                row.setName(rs.getString("name"));
                row.setPrice(rs.getFloat("price"));
                row.setDescription(rs.getString("description"));
                row.setStock(rs.getInt("stock"));
                row.setImage(rs.getString("filename"));
                row.setCategory(rs.getString("category"));

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return row;
    }

    //All the products that are in the user cart
    public List<Cart> productsCart(ArrayList<Cart> cartList) {
        List<Cart> products = new ArrayList<Cart>();

        try {
            if (!cartList.isEmpty()) {
                for (Cart product : cartList) {
                    query = "SELECT * FROM products where ID=?";
                    ps = this.con.prepareStatement(query);
                    ps.setInt(1, product.getId());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Cart item = new Cart();
                        item.setId(rs.getInt("ID"));
                        item.setName(rs.getString("name"));
                        item.setPrice(rs.getFloat("price") * product.getCount());
                        item.setCategory(rs.getString("category"));
                        item.setImage(rs.getString("filename"));
                        item.setDescription(rs.getString("description"));
                        item.setUnit_price(rs.getFloat("price"));
                        item.setCount(product.getCount());
                        System.out.println("I AM INSIDE DAO PRODUCT: " + item.getCount());
                        products.add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return products;
    }

    //Method of getting the total of the cart
    public float total(ArrayList<Cart> Items) {
        float sum = 0;
        try {
            if (!Items.isEmpty()) {
                for (Cart item : Items) {
                    query = "SELECT price FROM products where ID=?";
                    ps = this.con.prepareStatement(query);
                    ps.setInt(1, item.getId());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        sum += rs.getFloat("price") * item.getCount();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return sum;
    }
}
