/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author kakos
 */
//Order model extended from Product model
//Get/sets for requests and responses of the client
public class Order extends Product {

    public Order() {
    }

    private int idUser;
    private int idProduct;
    private String idOrder;
    private int quantity;
    private String username;
    private float price;
    private String date;
    private int count;

    public Order(int idUser, int idProduct, String idOrder, int quantity, String username, float price, String date, int count) {
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.idOrder = idOrder;
        this.quantity = quantity;
        this.username = username;
        this.price = price;
        this.date = date;
        this.count = count;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    


}
