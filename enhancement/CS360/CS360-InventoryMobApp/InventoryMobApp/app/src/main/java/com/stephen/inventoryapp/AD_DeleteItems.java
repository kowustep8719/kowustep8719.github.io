// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University
package com.stephen.inventoryapp;

import androidx.appcompat.app.AlertDialog;

/**
 * Deletion of Items Alert Dialog java code.
 * <p>
 * The AD_DeleteItems class include the functionality to build the
 * alert dialog to delete all the items records in the database.
 * <p>
 * This is class is called from the ItemsListActivity class.
 * Programmer: Stephen Owusu-Agyekum
 * Course: CS-360-Mobile Architect & Programming
 * School: Southern New Hampshire University
 */
// Declare a class named AD_DeleteItems responsible for creating a dialog to delete items.

public class AD_DeleteItems {

	// Method to create a dialog with two buttons for deleting items.
	// Efficiency: The method is static, allowing it to be called without instantiating the class.
	// Time Complexity: O(1), as the method only constructs an AlertDialog.
	public static AlertDialog doubleButton(final ItemsListActivity context){

		// Use AlertDialog.Builder to conveniently construct dialogs.
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		// Set the title of the dialog using a string resource.
		builder.setTitle(R.string.ad_delete_title)

				// Set the icon of the dialog using a drawable resource.
				.setIcon(R.drawable.delete_all_items)

				// Ensure the dialog is not dismissible by pressing outside.
				.setCancelable(false)

				// Set the message of the dialog using a string resource.
				.setMessage(R.string.ad_delete_msg)

				// Set the positive button with text from a string resource and attach an OnClickListener.
				.setPositiveButton(R.string.ad_delete_dialog_yes_button, (dialog, arg1) -> {

					// Call the YesDeleteItems method of ItemsListActivity.
					ItemsListActivity.YesDeleteItems();

					// Cancel the dialog.
					dialog.cancel();
				})

				// Set the negative button with text from a string resource and attach an OnClickListener.
				.setNegativeButton(R.string.ad_delete_dialog_no_button, (dialog, arg1) -> {

					// Call the NoDeleteItems method of ItemsListActivity.
					ItemsListActivity.NoDeleteItems();

					// Cancel the dialog.
					dialog.cancel();
				});

		// Create the AlertDialog object based on the configured properties and return it.
		return builder.create();
	}
}

