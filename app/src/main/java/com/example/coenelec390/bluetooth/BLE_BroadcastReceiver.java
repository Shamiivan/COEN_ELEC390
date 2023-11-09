package com.example.coenelec390.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BLE_BroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.coenelec390.bluetooth.NEW_CHARACTERISTIC")) {
            String data = intent.getStringExtra("data");
            // TODO : CALL addItem DialogFragment
        }
    }
}
