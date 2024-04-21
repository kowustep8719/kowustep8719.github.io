// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University

// Define the package name to provide a unique package name for the mobile app
package com.stephen.inventoryapp;

/**
* Imports the necessary Android classes and libraries for managing activities,
 * * database operations, user interface elements, and displaying messages.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

// Importing the AppCompatActivity class from the androidx.appcompat.app package.
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity Login java code.
 * <p>
 * The LoginActivity class include the functionality for the login activity
 * layout. It add functionality to the buttons in the layout, verify any
 * field is not empty, call the RegisterActivity, authenticate the user input
 * with the records in database, call and build the password forget alert
 * dialog, and empty fields after user is successfully authenticate.
 * <p>
 * This is the starting class when the program execute.
 * <p>
 * Programmer:	Stephen Owusu-Agyekum
 * Course: CS-360-Mobile Architect & Programming
 * School:	Southern New Hampshire University
 */
// Define a class named LoginActivity which extends AppCompatActivity
public class LoginActivity extends AppCompatActivity {

    // Declare variables for activity, buttons, edit texts, strings, boolean,
    // popup window, database, and SQLiteHandler
    Activity activity;
    Button LoginButton, RegisterButton, ForgotPassButton;
    EditText Email, Password;
    String NameHolder, PhoneNumberHolder, EmailHolder, PasswordHolder;
    Boolean EmptyHolder;
    PopupWindow popwindow;
    SQLiteDatabase db;
    UsersSQLiteHandler handler;
    String TempPassword = "NOT_FOUND" ;

    // Override the onCreate method to initialize the activity
    // and provide and a hook for initialization tasks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;

        // Find and initialize views for LoginButton, RegisterButton, ForgotPassButton, Email,
        // and Password using findViewById method
        LoginButton = findViewById(R.id.signinButton);
        RegisterButton = findViewById(R.id.registerButton);
        ForgotPassButton = findViewById(R.id.forgotPasswordButton);
        Email = findViewById(R.id.editTextEmailAddress);
        Password = findViewById(R.id.editTextPassword);
        handler = new UsersSQLiteHandler(this);

        // Adding click listener to sign in forgotPasswordButton
        LoginButton.setOnClickListener(view -> {
            // Call Login function
            LoginFunction();
        });

        // Adding click listener to register forgotPasswordButton.
        RegisterButton.setOnClickListener(view -> {
            // Opening new RegisterActivity using intent on forgotPasswordButton click.
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Adding click listener to register forgotPasswordButton.
        ForgotPassButton.setOnClickListener(view -> {
            EmailHolder = Email.getText().toString().trim();

            if (!EmailHolder.isEmpty()) {
                forgotPassPopup();
            } else {
                Toast.makeText(LoginActivity.this, "User Email is Empty", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Verify that editText fields are not empty.
    // This shows time Complexity: O(1) because it performs a constant number of operations regardless of input size
    // Optimization: The method efficiently checks if the email and password fields are empty or not, improving user experience.
    public String CheckEditTextNotEmpty() {
        // Retrieve values from fields and store them in string variables.
        String message = "";
        EmailHolder = Email.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        // verify if the EmailHolder string is empty
        if (EmailHolder.isEmpty()){
            Email.requestFocus();
            EmptyHolder = true;
            message = "User Email is Empty";

            // verify if the PasswordHolder string is empty
        } else if (PasswordHolder.isEmpty()){
            Password.requestFocus();
            EmptyHolder = true;
            message = "User Password is Empty";
        } else {
            EmptyHolder = false;
        }
        return message;
    }

    // Define the function for logging in.
    public void LoginFunction() {
        String message = CheckEditTextNotEmpty();
        // Check if the EmptyHolder boolean variable is false
        if(!EmptyHolder) {
            // Grant write permission to open the SQLite database.
            db = handler.getWritableDatabase();

            // Add email search query in the cursor.
            Cursor cursor = db.query(UsersSQLiteHandler.TABLE_NAME, null, " " + UsersSQLiteHandler.COLUMN_3_EMAIL + "=?", new String[]{EmailHolder}, null, null, null);

            // Loop through the cursor to advance to the next row.
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();

                    // Store the password and name associated with the entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(UsersSQLiteHandler.COLUMN_4_PASSWORD));
                    NameHolder = cursor.getString(cursor.getColumnIndex(UsersSQLiteHandler.COLUMN_1_NAME));
                    PhoneNumberHolder = cursor.getString(cursor.getColumnIndex(UsersSQLiteHandler.COLUMN_2_PHONE_NUMBER));

                    // Close the cursor to release resources.
                    cursor.close();
                }
            }
            handler.close();

            // Calling method to check final result
            CheckFinalResult();
        } else {

            // Execute this block if any of the login EditText fields are empty.
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    // Verify the entered password with the password associated with the email in the SQLite database.
    // Time Complexity: O(1) because it performs a constant number of comparisons regardless of input size
    // Efficiency: The method efficiently checks the password for login authentication, improving login speed.
    public void CheckFinalResult(){
        if(TempPassword.equalsIgnoreCase(PasswordHolder)) {
            Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();


            // Pass the Name to ItemsListActivity using an intent.
            Bundle bundle = new Bundle();
            bundle.putString("user_name", NameHolder);
            bundle.putString("user_email", EmailHolder);
            bundle.putString("user_phone", PhoneNumberHolder);

            // Navigate to ItemsListActivity upon successful login.
            Intent intent = new Intent(LoginActivity.this, ItemsListActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            // Clear the editText fields after a successful login and close the database.
            EmptyEditTextAfterDataInsert();
        } else {
            // Display error message if credentials are not correct
            Toast.makeText(LoginActivity.this,"Incorrect Email or Password\nor User Not Registered",Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND" ;
    }

    // Clear the editText fields after a successful login.
    // Time Complexity: O(1) because it performs a constant number of operations regardless of input size
    // Efficiency: The method efficiently clears the EditText fields, improving user experience.
    public void EmptyEditTextAfterDataInsert() {

        Email.getText().clear();
        Password.getText().clear();
    }

    // Define a method named forgotPassPopup with void return type and no parameters.
    public void forgotPassPopup() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.forgot_pass_popup, activity.findViewById(R.id.popup_element));

        // Create a PopupWindow object with the inflated layout,
        // specifying dimensions and focusability
        popwindow = new PopupWindow(layout, 800, 800, true);
        popwindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        // Find the EditText and TextView views within the inflated layout
        EditText phone = layout.findViewById(R.id.editTextItemDescriptionPopup);
        TextView password = layout.findViewById(R.id.textViewPassword);

        // Grant write permission to open the SQLite database.
        db = handler.getWritableDatabase();

        // Include a search email query in the cursor.
        Cursor cursor = db.query(UsersSQLiteHandler.TABLE_NAME, null, " " + UsersSQLiteHandler.COLUMN_3_EMAIL + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();

                // Store the password and name associated with the entered email.
                PhoneNumberHolder = cursor.getString(cursor.getColumnIndex(UsersSQLiteHandler.COLUMN_2_PHONE_NUMBER));
                TempPassword = cursor.getString(cursor.getColumnIndex(UsersSQLiteHandler.COLUMN_4_PASSWORD));

                // Closing cursor.
                cursor.close();
            }
        }
        handler.close();

        // Find the "Get" and "Cancel" buttons within the inflated layout.
        Button get = layout.findViewById(R.id.forgotGetButton);
        Button cancel = layout.findViewById(R.id.forgotCancelButton);

        // Assign a click listener to the "Get" button.
        get.setOnClickListener(view -> {
            String verifyPhone = phone.getText().toString();

            // Verify if the entered phone number matches the stored phone number.
            if(verifyPhone.equals(PhoneNumberHolder)) {
                password.setText(TempPassword);

                new android.os.Handler().postDelayed(() -> popwindow.dismiss(), 2000);
            } else {
                // If the phone numbers don't match, show a toast message indicating the mismatch.
                Toast.makeText(activity, "Phone Not Match", Toast.LENGTH_LONG).show();
            }
        });

        // Assign a click listener to the "Cancel" button.
        cancel.setOnClickListener(view -> {

            Toast.makeText(activity, "Action Canceled", Toast.LENGTH_SHORT).show();
            popwindow.dismiss();
        });
    }
}
