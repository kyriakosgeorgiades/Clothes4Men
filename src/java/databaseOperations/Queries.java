package databaseOperations;

import models.User;
import models.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
/**
 *
 * @author kakos
 */
public class Queries {

    private Connection con;
    private String query;
    private PreparedStatement ps;
    private ResultSet rs;

    public Queries(Connection con) {
        this.con = con;
    }
    //Method to get User object by its username
    public User getRoleId(String username) {

        boolean st = false;
        try {

            query = "SELECT ID,role,email FROM users where username=?";
            ps = this.con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            st = rs.next();
            User user = new User();
            int id = rs.getInt("ID");
            user.setID(id);
            String role = rs.getString("role");
            user.setRole(role);
            user.setUsername(username);
            user.setID(id);
            user.setEmail(rs.getString("email"));
            System.out.println("PLESSE WORK: " + user.getID());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //Method to update the Users password
    public void UpdatePass(int ID, String newpass) {
        try {
            query = "UPDATE users SET password=? WHERE ID=?";
            ps = this.con.prepareStatement(query);
            ps.setString(1, newpass);
            ps.setInt(2, ID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Method to get User's username by ID
    public String UsernamebyID(int ID) {
        try {

            query = "SELECT * FROM users WHERE ID=?";
            ps = this.con.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            rs.next();
            String username = rs.getString("username");
            return username;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //Method allowing admin/employee to add new products
    public int Add(String name, Float price, int count, String category, String desc, String fileName) {
        int result = -100;
        try {
            query = "INSERT INTO products(name,price,stock,category,description,filename) values(?,?,?,?,?,?)";
            ps = this.con.prepareStatement(query);
            ps.setString(1, name);
            ps.setFloat(2, price);
            ps.setInt(3, count);
            ps.setString(4, category);
            ps.setString(5, desc);
            ps.setString(6, fileName);
            result = ps.executeUpdate();
            System.out.println("I AM THE EXECUTION QEURY " + result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);

        }

        return result;
    }
    //Method for admin/employee to delete a product based on its ID
    public void Delete(int ID, String path) {
        try {
            Image image = FetchPic(ID);
            String file_name = image.getName();
            File f = new File(path+"\\images\\products\\" + file_name);
            if (f.delete()) {
                System.out.println("DELETED");
            } else {
                System.out.println("FAILED");
            }
            query = "DELETE FROM products WHERE ID = ?";
            ps = this.con.prepareStatement(query);
            ps.setInt(1, ID);
            int rowCount = ps.executeUpdate();
            System.out.println("ROW COUNT DELETED: " + rowCount);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
    //Method for admin/employee to update any fields of any combination of a product
    //based on ID product
    public void Updates(String[] parameters, String[] param_values, String path) {
        boolean st = false;
        boolean pic_update = false;
        int id = 0;
        try {

            System.out.println(Arrays.toString(parameters));
            System.out.println(Arrays.toString(param_values));
            int counter = 0;
            int index = 0;
            for (int i = 0; i < parameters.length; i++) {
                counter++;
                if (parameters[i].equals("id")) {
                    index = i;
                    id = Integer.parseInt(param_values[i]);
                }
                if (parameters[i].equals("filename")) {
                    pic_update = true;
                }
            }
            if (pic_update) {
                Image image = FetchPic(id);
                String file_name = image.getName();
                File f = new File(path + file_name);
                System.out.println(f);
                if (f.delete()) {
                    System.out.println("DELETED");
                } else {
                    System.out.println("FAILED");
                }
            }
            parameters = ArrayUtils.removeElement(parameters, "id");
            param_values = ArrayUtils.removeElement(param_values, param_values[index]);
            //As the parameters are unknown the query gets generated before the
            //PrepareStatement based on what was given in the list passed as
            //arguments to be updated
            String sql_q = "UPDATE products SET ";
            //+ " FROM products where ID=?";
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].equals("desc")) {
                    parameters[i] = "description";
                }
                if (parameters[i].equals("count")) {
                    parameters[i] = "stock";
                }
                if (!parameters[i].equals("id")) {
                    sql_q += parameters[i] + "=?,";
                }
            }
            sql_q = sql_q.substring(0, sql_q.length() - 1);
            sql_q = sql_q + " where ID=?";
            System.out.println(sql_q);
            query = sql_q;
            ps = this.con.prepareStatement(query);
            for (int i = 0; i < param_values.length; i++) {
                switch (parameters[i]) {
                    case "name":
                        ps.setString(i + 1, param_values[i]);
                        break;

                    case "price":
                        ps.setFloat(i + 1, Float.parseFloat(param_values[i]));
                        break;

                    case "description":
                        ps.setString(i + 1, param_values[i]);
                        break;

                    case "category":
                        ps.setString(i + 1, param_values[i]);
                        break;

                    case "stock":
                        ps.setInt(i + 1, Integer.parseInt(param_values[i]));
                        break;
                    case "filename":
                        ps.setString(i + 1, param_values[i]);
                        break;
                }

            }
            ps.setInt(counter, id);
            int rowCount = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
    //Method to get File Name of a picture based on ID product
    public Image FetchPic(int id) {
        boolean st = false;
        try {
            query = "SELECT filename FROM products where ID=?";
            ps = this.con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            st = rs.next();
            Image image = new Image();
            String filename = rs.getString("filename");
            image.setName(filename);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }

    }

}
