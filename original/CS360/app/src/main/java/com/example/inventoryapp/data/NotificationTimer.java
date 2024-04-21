package com.example.inventoryApp.data;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import androidx.core.content.ContextCompat;

import com.example.inventoryApp.data.helpers.SqlDbHelper;
import com.example.inventoryApp.data.model.SqlDbContract;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationTimer {

    private Context context;
    private Timer timer;

    public NotificationTimer(Context context)
    {
        this.timer = new Timer();
        this.context = context;
    }

    private class Work extends TimerTask
    {
        private Context context;
        InventoryDataSource dataSource;

        public Work(Context context) {
            this.context = context;
            this.dataSource = new InventoryDataSource(this.context);
        }

        public void run()
        {
          
            String[] projection = {
                    BaseColumns._ID,
                    SqlDbContract.InventoryEntry.COLUMN_NAME_NAME,
                    SqlDbContract.InventoryEntry.COLUMN_NAME_TYPE,
                    SqlDbContract.InventoryEntry.COLUMN_NAME_COUNT
            };
        
            String selection = SqlDbContract.InventoryEntry.COLUMN_NAME_COUNT + " < ?";
            String[] selectionArgs = { "5" };
         
            Cursor cursor = dataSource.queryInventoryDatabase(projection, selection, selectionArgs, null);
         
            for (HashMap row : SqlDbHelper.GetAll(cursor)) {
        
                String productName = row.get(SqlDbContract.InventoryEntry.COLUMN_NAME_NAME).toString();
                String productCount = row.get(SqlDbContract.InventoryEntry.COLUMN_NAME_COUNT).toString();
                String phoneNumber = null;

             
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
                    phoneNumber = telephonyManager.getLine1Number();
                }
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                 
                    smsManager.sendTextMessage(phoneNumber, null, "Item " + productName + " has a low count of " + productCount + " .", null, null);
                }
            }
        }
    }

    public void StartThread() {
        timer.scheduleAtFixedRate(new Work(this.context), 0, 60000);
    }

    public void StopThread() {
        timer.cancel();
    }
}
