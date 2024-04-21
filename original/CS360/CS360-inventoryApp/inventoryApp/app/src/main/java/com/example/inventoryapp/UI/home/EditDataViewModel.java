package com.example.inventoryApp.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventoryApp.R;
import com.example.inventoryApp.data.InventoryDataSource;

import java.io.IOException;

public class EditDataViewModel extends ViewModel {

    private MutableLiveData<EditDataFormState> editDataFormState = new MutableLiveData<>();
    private InventoryDataSource dataSource;

    public EditDataViewModel() {
    }

    LiveData<EditDataFormState> getEditDataFormState() {
        return editDataFormState;
    }

    // We will start the Data Source Provider
    public void InitializeDataProvider(Context context) {
        dataSource = new InventoryDataSource(context);
    }

    public void addDataDataChanged(String productCount) {
        if (!isValidNumber(productCount)) {
            editDataFormState.setValue(new EditDataFormState(R.string.invalid_productCount));
        } else {
            editDataFormState.setValue(new EditDataFormState(true));
        }
    }

    // We will send the data to the database
    public void UpdateRecordCount(String id, String count) throws IOException {
        try {
            // We will create a function to insert a record into the database
            dataSource.updateInventoryDatabase(id, count);
        } catch (Exception e) {
            throw new IOException("Error updating record", e);
        }
    }

    /*
        Private Helpers
     */

    // We will create string validation for over 3 chars
    private boolean isStringValid(String sys) {
        return sys != null && sys.trim().length() > 3;
    }

    // checks to see if the entered number is really a number
    private boolean isValidNumber(String sys) {
        try {
            int i = Integer.parseInt(sys);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
