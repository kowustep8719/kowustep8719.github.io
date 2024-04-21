// Inventory Mobile App

// Programmer: Stephen Owusu-Agyekum
// Course: CS-360-Mobile Architect & Programming
// Date : 2024-04-15
// Version: 7.3.0.1
// School: Southern New Hampshire University

// Define the package name to provide a unique package name for the mobile app
package com.stephen.inventoryapp;

// Import necessary classes for Android functionality.

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Customize Items List Activity java code.

 * The CustomItemsList class include the functionality to populate
 * the items from the database into the ItemListActivity. Add the
 * row functionality to edit and delete an item, and to change item
 * quantity. It also call and build the edit item alert dialog.
 * <p>
 * This class generates the row in the ItemsListActivity.
 * <p>
 * Programmer: Stephen Owusu-Agyekum
 * Course: CS-360-Mobile Architect & Programming
 * School: Southern New Hampshire University
 */
/**
 * This class represents a custom adapter for managing a list view of items.
 * It extends BaseAdapter and provides functionality for populating the list view,
 * handling item editing and deletion, and updating the UI accordingly.
 * The ViewHolder pattern is used for efficient view recycling.
 * <p>
 * Programmer: Stephen Owusu-Agyekum
 * Course: CS-360-Mobile Architect & Programming
 * School: Southern New Hampshire University
 */
public class CustomItemsList extends BaseAdapter {

	private final Activity context;
	private PopupWindow popwindow;
	ArrayList<Item> items;
	ItemsSQLiteHandler db;

	/**
	 * Constructs a new CustomItemsList object.
	 *
	 * @param context The activity context.
	 * @param items   The list of items to be displayed.
	 * @param db      The database handler for item operations.
	 */
	public CustomItemsList(Activity context, ArrayList<Item> items, ItemsSQLiteHandler db) {
		this.context = context;
		this.items = items;
		this.db = db;
	}

	// Declare ViewHolder to hold references to UI elements for efficient view recycling
	public static class ViewHolder {
		TextView textViewItemId;
		TextView textViewUserEmail;
		TextView textViewItemDesc;
		TextView textViewItemQty;
		TextView textViewItemUnit;
		ImageButton editBtn;
		ImageButton deleteBtn;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		LayoutInflater inflater = context.getLayoutInflater();
		ViewHolder vh;

		// Check if convertView is null to inflate a new row layout
		if (convertView == null) {
			vh = new ViewHolder();
			row = inflater.inflate(R.layout.item_row_template, null, true);

			// Initialize ViewHolder with references to UI elements
			vh.editBtn = row.findViewById(R.id.editButton);
			vh.textViewItemId = row.findViewById(R.id.textViewItemId);
			vh.textViewUserEmail = row.findViewById(R.id.textViewUserEmail);
			vh.textViewItemDesc = row.findViewById(R.id.textViewItemDesc);
			vh.textViewItemQty = row.findViewById(R.id.textViewItemQty);
			vh.textViewItemUnit = row.findViewById(R.id.textViewItemUnit);
			vh.deleteBtn = row.findViewById(R.id.deleteButton);

			// Set tag for row to ViewHolder
			row.setTag(vh);
		} else {
			// If convertView is not null, reuse ViewHolder from recycled view
			vh = (ViewHolder) convertView.getTag();
		}

		// Populate UI elements with data from items list
		vh.textViewItemId.setText(String.valueOf(items.get(position).getId()));
		vh.textViewUserEmail.setText(items.get(position).getUserEmail());
		vh.textViewItemDesc.setText(items.get(position).getDesc());
		vh.textViewItemQty.setText(items.get(position).getQty());
		vh.textViewItemUnit.setText(items.get(position).getUnit());

		// Change UI color based on item quantity and trigger SMS if quantity is zero
		String value = vh.textViewItemQty.getText().toString().trim();
		if (value.equals("0")) {
			vh.textViewItemQty.setBackgroundColor(Color.RED);
			vh.textViewItemQty.setTextColor(Color.WHITE);
			ItemsListActivity.SendSMSMessage(context.getApplicationContext());
		} else {
			vh.textViewItemQty.setBackgroundColor(Color.parseColor("#E6E6E6"));
			vh.textViewItemQty.setTextColor(Color.BLACK);
		}

		// Define actions for edit and delete button clicks
		final int positionPopup = position;
		vh.editBtn.setOnClickListener(view -> editPopup(positionPopup));

		// Update items list from database
		// Delete item from database
		// Notify adapter of data change
		vh.deleteBtn.setOnClickListener(view -> {
			db.deleteItem(items.get(positionPopup));
			items = (ArrayList<Item>) db.getAllItems();
			notifyDataSetChanged();

			Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();

			// Update total items count in UI
			int itemsCount = db.getItemsCount();
			TextView TotalItems = context.findViewById(R.id.textViewTotalItemsCount);
			TotalItems.setText(String.valueOf(itemsCount));
		});

		return row;
	}

	// Return item at specified position
	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	// Return total number of items in the list
	public int getCount() {
		return items.size();
	}

	/**
	 * Opens a popup window for editing the item at the specified position in the list.
	 *
	 * @param positionPopup The position of the item in the list to be edited.
	 */
	public void editPopup(final int positionPopup) {
		LayoutInflater inflater = context.getLayoutInflater();

		View layout = inflater.inflate(R.layout.edit_item_popup, context.findViewById(R.id.popup_element));

		// Initialize and show PopupWindow for editing item
		popwindow = new PopupWindow(layout, 800, 1000, true);
		popwindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

		// Get references to UI elements in edit popup
		final EditText editItemDesc = layout.findViewById(R.id.editTextItemDescriptionPopup);
		final EditText editItemQty = layout.findViewById(R.id.editTextItemQtyPopup);
		final EditText editItemUnit = layout.findViewById(R.id.editTextItemUnitPopup);

		// Set initial text in edit fields to item details
		editItemDesc.setText(items.get(positionPopup).getDesc());
		editItemQty.setText(items.get(positionPopup).getQty());
		editItemUnit.setText(items.get(positionPopup).getUnit());

		// Define actions for save and cancel buttons in edit popup
		Button save = layout.findViewById(R.id.editSaveButton);
		Button cancel = layout.findViewById(R.id.editCancelButton);

		save.setOnClickListener(view -> {
			// Get updated item details from edit fields
			String itemDesc = editItemDesc.getText().toString();
			String itemQty = editItemQty.getText().toString();
			String itemUnit = editItemUnit.getText().toString();

			// Update item in database with new details
			Item item = items.get(positionPopup);
			item.setDesc(itemDesc);
			item.setQty(itemQty);
			item.setUnit(itemUnit);
			db.updateItem(item);

			// Update items list from database and notify adapter of data change
			items = (ArrayList<Item>) db.getAllItems();
			notifyDataSetChanged();

			Toast.makeText(context, "Item Updated", Toast.LENGTH_SHORT).show();
			// Dismiss edit popup
			popwindow.dismiss();
		});

		cancel.setOnClickListener(view -> {
			Toast.makeText(context, "Action Canceled", Toast.LENGTH_SHORT).show();
			popwindow.dismiss();
		});
	}

}
