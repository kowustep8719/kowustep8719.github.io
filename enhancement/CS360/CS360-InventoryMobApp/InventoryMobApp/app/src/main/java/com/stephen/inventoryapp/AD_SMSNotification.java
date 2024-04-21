// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University

package com.stephen.inventoryapp;

import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

/**
 * SMS Notification Alert Dialog java code.
 * <p>
 * The AD_SMSNotification class include the functionality to build the
 * alert dialog to enable or disable the SMS notifications when an item
 * quantity is zero.
 * <p>
 * This class is called from the ItemsListActivity class.
 *
 * Programmer: Stephen Owusu-Agyekum
 * Course: CS-360-Mobile Architect & Programming
 * School: Southern New Hampshire University
 */

// Define a class named AD_SMSNotification
public class AD_SMSNotification {

	/**
	 * Method to create a dialog with two buttons for enabling or disabling SMS notifications.
	 * <p>
	 * This method constructs an AlertDialog with options to enable or disable SMS alerts.
	 * It displays a toast indicating the action taken upon clicking the respective button.
	 * The dialog is invoked from the ItemsListActivity class.
	 *
	 * @param context The context from which the dialog is invoked.
	 * @return AlertDialog An AlertDialog instance with options to enable or disable SMS alerts.
	 *
	 * Efficiency: The method is static, allowing it to be called without instantiating the class.
	 * Time Complexity: O(1), as the method only constructs an AlertDialog.
	 */
	public static AlertDialog doubleButton(final ItemsListActivity context){
		// Use AlertDialog.Builder to conveniently construct dialogs.
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// Set the title of the dialog using a string resource.
		builder.setTitle(R.string.ad_sms_title)
				// Set the icon of the dialog using a drawable resource.
				.setIcon(R.drawable.sms_notification)
				// Ensure the dialog is not dismissible by pressing outside.
				.setCancelable(false)
				// Set the message of the dialog using a string resource.
				.setMessage(R.string.ad_sms_msg)
				// Set the positive button with text from a string resource and attach an OnClickListener.
				.setPositiveButton(R.string.ad_sms_enable_button, (dialog, arg1) -> {
					// Show a toast indicating SMS alerts are enabled.
					Toast.makeText(context, "SMS Alerts Enable", Toast.LENGTH_LONG).show();
					// Call the AllowSendSMS method of ItemsListActivity.
					ItemsListActivity.AllowSendSMS();
					// Cancel the dialog.
					dialog.cancel();
				})
				// Set the negative button with text from a string resource and attach an OnClickListener.
				.setNegativeButton(R.string.ad_sms_disable_button, (dialog, arg1) -> {
					// Show a toast indicating SMS alerts are disabled.
					Toast.makeText(context, "SMS Alerts Disable", Toast.LENGTH_LONG).show();
					// Call the DenySendSMS method of ItemsListActivity.
					ItemsListActivity.DenySendSMS();
					// Cancel the dialog.
					dialog.cancel();
				});
		// Create the AlertDialog object based on the configured properties and return it.
		return builder.create();
	}
}