// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University


// Define the package name to provide a unique package name for the mobile app
package com.stephen.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User SQLite Handler java code.
 * <p>
 * The UsersSQLiteHandler class include the functionality to manage
 * all the CRUD operations to of the user database.
 * Stephen Owusu-Agyekum
 * CS-360-Mobile Architect & Programming
 * Southern New Hampshire University
 */

// Class declaration for UsersSQLiteHandler extending SQLiteOpenHelper
public class UsersSQLiteHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "UsersData.DB";
	public static final String TABLE_NAME = "UsersTable";

	public static final String COLUMN_0_ID = "id";
	public static final String COLUMN_1_NAME = "name";
	public static final String COLUMN_2_PHONE_NUMBER = "phone_number";
	public static final String COLUMN_3_EMAIL = "email";
	public static final String COLUMN_4_PASSWORD = "password";

	private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " +
			TABLE_NAME + " (" +
			COLUMN_0_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
			COLUMN_1_NAME + " VARCHAR, " +
			COLUMN_2_PHONE_NUMBER + " VARCHAR, " +
			COLUMN_3_EMAIL + " VARCHAR, " +
			COLUMN_4_PASSWORD + " VARCHAR" + ");";

	// Constructor declaration for UsersSQLiteHandler accepting Context parameter
	public UsersSQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// Creating the UsersTable if it does not exist
		database.execSQL(CREATE_USERS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Executing SQL statement to drop table if exists during database upgrade
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		// Recreating the UsersTable after dropping it
		onCreate(db);
	}

	// Add item to database (CRUD)
	public void createUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_1_NAME, user.getUserName());
		values.put(COLUMN_2_PHONE_NUMBER, user.getUserPhone());
		values.put(COLUMN_3_EMAIL, user.getUserEmail());
		values.put(COLUMN_4_PASSWORD, user.getUserPass());

		// Inserting values into database table
		db.insert(TABLE_NAME, null, values);
		db.close();
	}

	// Read item from Database
	// Method declaration to read user, accepting id parameter
	public User readUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		// Executing query to retrieve user from table
		Cursor cursor = db.query(TABLE_NAME,
				new String[] { COLUMN_0_ID, COLUMN_1_NAME, COLUMN_2_PHONE_NUMBER, COLUMN_3_EMAIL, COLUMN_4_PASSWORD },
				COLUMN_0_ID + " = ?", new String[] { String.valueOf(id) }, null, null, null, null);

		// Checking if cursor is not null
		if (cursor != null)
			cursor.moveToFirst();

		// Create a new User object by extracting values from the cursor and parsing them appropriately
		User user = new User(
				Integer.parseInt(Objects.requireNonNull(cursor).getString(0)),
				cursor.getString(1),
				cursor.getString(2),
				cursor.getString(3),
				cursor.getString(4)
		);
		cursor.close();
		return user;
	}

	// Update user in database
	// Method declaration to update user, accepting User parameter
	public int updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_1_NAME, user.getUserName());
		values.put(COLUMN_2_PHONE_NUMBER, user.getUserPhone());
		values.put(COLUMN_3_EMAIL, user.getUserEmail());
		values.put(COLUMN_4_PASSWORD, user.getUserPass());

		/** Updating database with new values
		 * This method provides an efficient way to update user details in the database.
		 * The time complexity of this method is O(1) as it directly updates the row with the provided ID.
		 */
		return db.update(TABLE_NAME, values, COLUMN_0_ID + " = ?", new String[] { String.valueOf(user.getId()) });
	}

	// Delete user from database
	public void deleteItem(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		// Deleting user from database
		// Closing database connection
		db.delete(TABLE_NAME, COLUMN_0_ID + " = ?", new String[] { String.valueOf(user.getId()) });
		db.close();
	}

	/** Create the Global Database Operations
	 * Getting All Users
	 * Method declaration to get all users
	 */
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();

		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;

		// Getting writable database instance
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			// Efficiently parsing cursor data into User objects and adding them to the list
			do {
				User user = new User();
				user.setId(Integer.parseInt(cursor.getString(0)));
				user.setUserName(cursor.getString(1));
				user.setUserPhone(cursor.getString(2));
				user.setUserEmail(cursor.getString(3));
				user.setUserPass(cursor.getString(4));

				// Adding user to list
				userList.add(user);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return userList;
	}

	/**
	 * Deleting All Users
	 * Method declaration to delete all users
	 */
	public void deleteAllUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Efficiently deleting all users from the table without iterating through each entry
		db.delete(TABLE_NAME, null, null);
		db.close();
	}

	// Getting Users Count
	public int getUsersCount() {
		// Efficiently counting the number of users in the database
		String countQuery = "SELECT * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int usersTotal = cursor.getCount();
		cursor.close();
		return usersTotal;
	}
}
