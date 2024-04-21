// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University

package com.stephen.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Items SQLite Handler java code.
 * <p>
 * The ItemsSQLiteHandler class include the functionality to manage
 * all the CRUD operations the user can perform in the app.
 * <p>
 * Stephen Owusu-Agyekum
 * CS-360-Mobile Architect & Programming
 * Southern New Hampshire University
 */
// Database handler for managing items data in SQLite database
public class ItemsSQLiteHandler extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "ItemsData.DB";

    // Table name
    private static final String TABLE_NAME = "ItemsTable";

    // Column names
    private static final String COLUMN_0_ID = "id";
    private static final String COLUMN_1_USER_EMAIL = "email";
    private static final String COLUMN_2_DESCRIPTION = "description";
    private static final String COLUMN_3_QUANTITY = "quantity";
    private static final String COLUMN_4_UNIT = "unit";

    // SQL statement to create the items table
    private static final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_0_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_1_USER_EMAIL + " VARCHAR, " +
            COLUMN_2_DESCRIPTION + " VARCHAR, " +
            COLUMN_3_QUANTITY + " VARCHAR, " +
            COLUMN_4_UNIT + " VARCHAR" + ");";

    // Constructor for ItemsSQLiteHandler class
    public ItemsSQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the items table in the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    // Upgrade the database if the version is updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Database CRUD (Create, Read, Update, Delete) Operations
     * Stephen Owusu-Agyekum
     * CS-360-Mobile Architect & Programming
     */

    // Add item to database
    // This method inserts a new item into the database
    public void createItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1_USER_EMAIL, item.getUserEmail());
        values.put(COLUMN_2_DESCRIPTION, item.getDesc());
        values.put(COLUMN_3_QUANTITY, item.getQty());
        values.put(COLUMN_4_UNIT, item.getUnit());

        // Insert data into the database
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Read item from Database
    // This method retrieves an item from the database based on its ID
    public Item readItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        //Executing a query on the database table.Query the database table
        Cursor cursor = db.query(TABLE_NAME,
                new String[] { COLUMN_0_ID, COLUMN_1_USER_EMAIL, COLUMN_2_DESCRIPTION, COLUMN_3_QUANTITY, COLUMN_4_UNIT }, COLUMN_0_ID + " = ?",

                new String[] { String.valueOf(id) }, null, null, null, null);

        // Check if cursor is not null
        if (cursor != null)
            cursor.moveToFirst();

        // Create a new Item object with data from the cursor
        Item item = new Item(Integer.parseInt(Objects.requireNonNull(cursor).getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        cursor.close();

        return item;
    }

    // Update item in database
    // This method updates an existing item in the database
    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1_USER_EMAIL, item.getUserEmail());
        values.put(COLUMN_2_DESCRIPTION, item.getDesc());
        values.put(COLUMN_3_QUANTITY, item.getQty());
        values.put(COLUMN_4_UNIT, item.getUnit());

        // Update the item in the database
        return db.update(TABLE_NAME, values, COLUMN_0_ID + " = ?", new String[] { String.valueOf(item.getId()) });
    }

    // Delete item from database
    // This method deletes an item from the database
    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the item from the database
        db.delete(TABLE_NAME, COLUMN_0_ID + " = ?", new String[] { String.valueOf(item.getId()) });
        db.close();
    }

    // Getting All Items
    // This method retrieves all items from the database
    // It demonstrates optimization, time complexity (Big O), and efficiency
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();

        // Select All Query
        // This query retrieves all rows from the items table
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add items to the list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setUserEmail(cursor.getString(1));
                item.setDesc(cursor.getString(2));
                item.setQty(cursor.getString(3));
                item.setUnit(cursor.getString(4));

                // Add the item to the list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return itemList;
    }

    // Deleting all items
    // This method deletes all items from the database
    public void deleteAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    // Getting Items Count
    // This method returns the total number of items in the database
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int itemsTotal = cursor.getCount();

        cursor.close();
        return itemsTotal;
    }
}
