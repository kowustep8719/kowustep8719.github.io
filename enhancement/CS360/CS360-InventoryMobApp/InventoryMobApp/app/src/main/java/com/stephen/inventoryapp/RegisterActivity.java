// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University

// Define the package name to provide a unique package name for the mobile app
package com.stephen.inventoryapp;

// Import the Intent class for activity navigation.
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Define variables for UI elements and database interactions.
public class RegisterActivity extends AppCompatActivity {
    Button RegisterButton, CancelButton;
    EditText NameHolder, PhoneNumberHolder, EmailHolder, PasswordHolder;
    Boolean EmptyHolder;
    SQLiteDatabase db;
    UsersSQLiteHandler handler;
    String F_Result = "Not_Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements
        NameHolder = findViewById(R.id.editTextPersonName);
        PhoneNumberHolder = findViewById(R.id.editTextPhoneNumber);
        EmailHolder = findViewById(R.id.editTextEmailAddress);
        PasswordHolder = findViewById(R.id.editTextPassword);
        RegisterButton = findViewById(R.id.regSignupButton);
        CancelButton = findViewById(R.id.regCancelButton);
        handler = new UsersSQLiteHandler(this);

        // Adding click listener to RegisterButton
        // Check if input fields are empty
        RegisterButton.setOnClickListener(view -> {
            String message = CheckEditTextNotEmpty();
            // Verify email existence in database
            // Clear input fields after successful registration
            if (!EmptyHolder) {
                CheckEmailAlreadyExists();
                EmptyEditTextAfterDataInsert();
            } else {
                // Show error message if any field is empty
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });

        // Adding click listener to CancelButton
        CancelButton.setOnClickListener(view -> {
            // Return to the LoginActivity after canceling registration
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            this.finish();
        });
    }

    // Register a new user in the database.
    public void InsertUserIntoDatabase(){
        String name = NameHolder.getText().toString().trim();
        String phone = PhoneNumberHolder.getText().toString().trim();
        String email = EmailHolder.getText().toString().trim();
        String pass = PasswordHolder.getText().toString().trim();

        // Create User object and insert into the database
        User user = new User(name, phone, email, pass);
        handler.createUser(user);

        // Show success message and return to login screen
        Toast.makeText(RegisterActivity.this,"User Registered Successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        this.finish();
    }

    // Check if any EditText field is empty.
    public String CheckEditTextNotEmpty() {
        String message = "";
        String name = NameHolder.getText().toString().trim();
        String phone = PhoneNumberHolder.getText().toString().trim();
        String email = EmailHolder.getText().toString().trim();
        String pass = PasswordHolder.getText().toString().trim();

        // Verify if any of the fields are empty.
        if (name.isEmpty()) {
            NameHolder.requestFocus();
            EmptyHolder = true;
            message = "User Name is Empty";
        } else if (phone.isEmpty()){
            PhoneNumberHolder.requestFocus();
            EmptyHolder = true;
            message = "User Phone is Empty";
        } else if (email.isEmpty()){
            EmailHolder.requestFocus();
            EmptyHolder = true;
            message = "User Email is Empty";
        } else if (pass.isEmpty()){
            PasswordHolder.requestFocus();
            EmptyHolder = true;
            message = "User Password is Empty";
        } else {
            EmptyHolder = false;
        }
        return message;
    }

    // Verify if the user email already exists in the database.
    public void CheckEmailAlreadyExists(){
        String email = EmailHolder.getText().toString().trim();
        db = handler.getWritableDatabase();

        // Execute query to check email existence
        Cursor cursor = db.query(UsersSQLiteHandler.TABLE_NAME, null, " " + UsersSQLiteHandler.COLUMN_3_EMAIL + "=?", new String[]{email}, null, null, null);

        // Process query result
        // Set result variable
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                F_Result = "Email Found";
                cursor.close();
            }
        }
        handler.close();

        // Check final result and proceed accordingly
        CheckFinalCredentials();
    }

    // Verify final credentials and take appropriate action
    public void CheckFinalCredentials(){
        if(F_Result.equalsIgnoreCase("Email Found")) {
            // Show message if email exists
            Toast.makeText(RegisterActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();
        } else {
            // Insert user registration details into the SQLite database
            InsertUserIntoDatabase();
        }
        F_Result = "Not_Found" ;
    }

    // Clear the EditText fields after inserting data into the database.
    public void EmptyEditTextAfterDataInsert(){
        NameHolder.getText().clear();
        PhoneNumberHolder.getText().clear();
        EmailHolder.getText().clear();
        PasswordHolder.getText().clear();
    }
}
