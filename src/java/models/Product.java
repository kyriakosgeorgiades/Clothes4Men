package models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author kakos
 */
//Product model, the main model which the rest inheritance from it.
public class Product{

    public Product() {
    }

    private int id;
    private String name;
    private float price;
    private String description;
    private String category;
    private int stock;
    private String image;
    private float unit_price;

    public Product(int id, String name, float price, String description, String category, int stock, String image, float unit_price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.stock = stock;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

}
