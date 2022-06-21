package databaseOperations;


import java.sql.*;

public class Validate {
    //Method to check if the user exists in the database while trying to login
    public static boolean checkUser(String username, String password) {

        boolean st = false;
        try {
            //loading drivers for sql
            Class.forName("com.mysql.cj.jdbc.Driver");
            //creating connection with the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coursework_final?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            st = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return st;
    }
}
