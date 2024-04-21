// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University

// Define the package name to provide a unique package name for the mobile app
package com.stephen.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Add Item Activity java code.
 * <p>
 * The AddItemActivity class includes functionality for the add item activity layout. It adds functionality to the buttons in the layout,
 * inserts a new inventory item into the database, and verifies that no field is left empty.
 * <p>
 * This class is called from by the ItemsListActivity.
 * <p>
 * Programmer: Stephen Owusu-Agyekum
 * Course: CS-360-Mobile Architect & Programming
 * School: Southern New Hampshire University
 */

// Define a class named AddItemActivity
public class AddItemActivity extends AppCompatActivity {

    // Declare variables to store user data and UI elements.
    String EmailHolder, DescHolder, QtyHolder, UnitHolder;
    TextView UserEmail;
    ImageButton IncreaseQty, DecreaseQty;
    EditText ItemDescValue, ItemQtyValue, ItemUnitValue;
    Button CancelButton, AddItemButton;
    Boolean EmptyHolder;
    ItemsSQLiteHandler db;

    // Method to initialize the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        // Initialize UI elements.
        UserEmail = findViewById(R.id.textViewLoggedUser);
        ItemDescValue = findViewById(R.id.editTextItemDescription);
        ItemUnitValue = findViewById(R.id.editTextItemUnit);
        IncreaseQty = findViewById(R.id.itemQtyIncrease);
        DecreaseQty = findViewById(R.id.itemQtyDecrease);
        ItemQtyValue = findViewById(R.id.editTextItemQuantity);
        CancelButton = findViewById(R.id.addCancelButton);
        AddItemButton = findViewById(R.id.addItemButton);
        db = new ItemsSQLiteHandler(this);

        // Receive user email from ItemsListActivity.
        EmailHolder = getIntent().getStringExtra(ItemsListActivity.UserEmail);

        // Set the user's email to the textViewLoggedUser.
        UserEmail.setText(getString(R.string.logged_user, EmailHolder));

        // Set click listeners for quantity increase and decrease buttons.
        IncreaseQty.setOnClickListener(view -> {
            int input = 0, total;
            String value = ItemQtyValue.getText().toString().trim();

            // Check if the quantity is not empty and increment it.
            if (!value.isEmpty()) {
                input = Integer.parseInt(value);
            }
            total = input + 1;
            ItemQtyValue.setText(String.valueOf(total));
        });

        DecreaseQty.setOnClickListener(view -> {
            int input, total;
            String qty = ItemQtyValue.getText().toString().trim();

            // Check if the quantity is not zero and decrement it.
            if (!qty.equals("0")) {
                input = Integer.parseInt(qty);
                total = input - 1;
                ItemQtyValue.setText(String.valueOf(total));
            } else {
                // Display a toast if the quantity is already zero.
                Toast.makeText(this, "Item Quantity is Zero", Toast.LENGTH_LONG).show();
            }
        });

        // Set click listener for cancel button to return to ItemsListActivity.
        CancelButton.setOnClickListener(view -> {
            Intent add = new Intent();
            setResult(0, add);
            finish();
        });

        // Set click listener for add item button to insert data into the database.
        AddItemButton.setOnClickListener(view -> InsertItemIntoDatabase());
    }

    // Method to insert item data into the database.
    public void InsertItemIntoDatabase() {
        String message = CheckEditTextNotEmpty();

        // Check if all fields are filled.
        if (!EmptyHolder) {
            String email = EmailHolder;
            String desc = DescHolder;
            String qty = QtyHolder;
            String unit = UnitHolder;

            // Create an Item object and insert it into the database.
            Item item = new Item(email, desc, qty, unit);
            db.createItem(item);

            // Display a toast message after successful insertion.
            Toast.makeText(this,"Item Added Successfully", Toast.LENGTH_LONG).show();

            // Close AddItemActivity.
            Intent add = new Intent();
            setResult(RESULT_OK, add);
            finish();
        } else {
            // Display a toast message if any field is empty.
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    // Method to check if item description and unit are not empty.
    public String CheckEditTextNotEmpty() {
        // Retrieve values from fields.
        String message = "";

        DescHolder = ItemDescValue.getText().toString().trim();
        UnitHolder = ItemUnitValue.getText().toString().trim();
        QtyHolder = ItemQtyValue.getText().toString().trim();

        // Check if description and unit are empty.
        if (DescHolder.isEmpty()) {
            ItemDescValue.requestFocus();
            EmptyHolder = true;
            message = "Item Description is Empty";
        } else if (UnitHolder.isEmpty()){
            ItemUnitValue.requestFocus();
            EmptyHolder = true;
            message = "Item Unit is Empty";
        } else {
            EmptyHolder = false;
        }
        return message;
    }

}
