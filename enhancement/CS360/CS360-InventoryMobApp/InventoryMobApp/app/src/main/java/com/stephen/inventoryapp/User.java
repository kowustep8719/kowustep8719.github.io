// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University


// Define the package name to provide a unique package name for the mobile app
package com.stephen.inventoryapp;

/**
 * User java class source code.
 * <p>
 * The User class include the functionality to model the
 * constructor to manage the user table in the database.
 * Stephen Owusu-Agyekum
 * CS-360-Mobile Architect & Programming
 * Southern New Hampshire University
 */
public class User {

    // Declare variables to store user information such as name,
    // phone number, email address and password
    int id;
    String user_name;
    String user_phone;
    String user_email;
    String user_pass;

    // Default constructor
    public User() {
        super();
    }

    // Constructor with all parameters
    public User(int id, String name, String phone, String email, String password) {
        super();
        this.id = id;
        this.user_name = name;
        this.user_phone = phone;
        this.user_email = email;
        this.user_pass = password;
    }

    // Constructor without id parameter
    public User(String name, String phone, String email, String password) {
        this.user_name = name;
        this.user_phone = phone;
        this.user_email = email;
        this.user_pass = password;
    }

    // Getter method for id
    public int getId() {
        return id;
    }

    // Setter method for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter method for user_name
    public String getUserName() {
        return user_name;
    }

    // Setter method for user_name
    public void setUserName(String name) {
        this.user_name = name;
    }

    // Getter method for user_phone
    public String getUserPhone() {
        return user_phone;
    }

    // Setter method for user_phone
    public void setUserPhone(String phone) {
        this.user_phone = phone;
    }

    // Getter method for user_email
    public String getUserEmail() {
        return user_email;
    }

    // Setter method for user_email
    public void setUserEmail(String email) {
        this.user_email = email;
    }

    // Getter method for user_pass
    public String getUserPass() {
        return user_pass;
    }

    // Setter method for user_pass
    public void setUserPass(String pass) {
        this.user_pass = pass;
    }
}





