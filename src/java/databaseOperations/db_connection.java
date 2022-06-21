package databaseOperations;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
/**
 *
 * @author kakos
 */
import java.sql.*;
//Setting the connection of the database
public class db_connection {

    private static Connection connection = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/coursework_final?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "");
            
        }
        return connection;
    }
}
