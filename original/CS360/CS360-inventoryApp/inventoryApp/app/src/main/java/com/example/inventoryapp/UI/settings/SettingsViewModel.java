package com.example.inventoryApp.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inventoryApp.data.InventoryDataSource;

import java.util.jar.Manifest;

public class SettingsViewModel extends ViewModel {

    private InventoryDataSource dataSource;

    public SettingsViewModel() {

    }

    // I will initialize the Data Source  Provider
    public void InitializeDataProvider(Context context) {
        dataSource = new InventoryDataSource(context);
    }

    // This will be used to clear out the data in the inventory table
    public boolean DeleteAllInventoryData() {
        return dataSource.deleteAllInventoryData();
    }

}
