/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author kakos
 */
//Model of Cart which is exented from the Product model
//Getters and Setters to help with the various requests/responses
public class Cart extends Product{
    private int count;
    
    public Cart(){}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
