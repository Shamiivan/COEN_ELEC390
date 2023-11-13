package com.example.coenelec390.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.coenelec390.Utils;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.example.coenelec390.ui.item.AddItemActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class BLE_BroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.coenelec390.bluetooth.NEW_CHARACTERISTIC")) {
            String data = intent.getStringExtra("data");
            Utils.print("I think its broadcasting to ble rec");

            // TODO : CALL addItem DialogFragment
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.findNFC(data)
                    .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                boolean typeExists = task.getResult();
                                if (typeExists) {
                                    // Type exists in the database, show a message
                                    //Toast.makeText(getActivity(), "Type exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Type doesn't exist in the database, show a message. go to op
                                    //Toast.makeText(getActivity(), "Type doesn't exist.", Toast.LENGTH_SHORT).show();
                                    AddItemActivity fragment = AddItemActivity.newInstance(data);
                                    fragment.show(fragment.getFragmentManager(), "dialogFragment");

                                }
                            } else {
                                // Handle any potential errors here
                                //Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}
