// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University

// Define the package name to provide a unique package name for the mobile app
package com.stephen.inventoryapp;

/**
 * Items from java class code.
 * <p>
 * The Item class include the functionality to model the
 * constructor to manage the item table in the database.
 *
 * Stephen Owusu-Agyekum
 * CS-360 Mobile Architect & Programming
 * Southern New Hampshire University
 */
public class Item {

    // Declare string and integer variables to store item information
    int id;
    String user_email;
    String desc;
    String qty;
    String unit;

    // Default constructor for the Item class
    public Item() {
        super();
    }

    // Constructor with parameters for all fields
    public Item(int i, String email, String description, String quantity, String unit) {
        super();
        this.id = i;
        this.user_email = email;
        this.desc = description;
        this.qty = quantity;
        this.unit = unit;
    }

    // Constructor with parameters for all fields except ID
    public Item(String email, String description, String quantity, String unit) {
        // Initialize fields with provided values
        this.user_email = email;
        this.desc = description;
        this.qty = quantity;
        this.unit = unit;
    }

    // Getter and Setter methods for user email and setting retrieving item information
    public int getId() {
        return id;
    }

    // Setter method for setting the item ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter method for retrieving the user email associated with the item
    // Return the user email associated with the item
    public String getUserEmail() {
        return user_email;
    }

    // Setter method for setting the user email associated with the item
    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }

    // Getter method for retrieving the item description
    // Return the description of the item
    public String getDesc() {
        return desc;
    }

    // Setter method for setting the item description
    public void setDesc(String desc) {
        this.desc = desc;
    }

    // Getter method for retrieving the item quantity
    // Return the quantity of the item
    public String getQty() {
        return qty;
    }

    // Setter method for setting the item quantity
    public void setQty(String qty) {
        this.qty = qty;
    }

    // Getter method for retrieving the unit of measurement for the item
    // Return the unit of measurement for the item
    public String getUnit() {
        return unit;
    }

    // Setter method for setting the unit of measurement for the item
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
