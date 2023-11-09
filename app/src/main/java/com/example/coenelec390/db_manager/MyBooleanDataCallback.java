package com.example.coenelec390.db_manager;

public class MyBooleanDataCallback implements DatabaseManager.BooleanDataCallback {
    @Override
    public void onDataReceived(boolean result) {
        // Handle the result here
        if (result) {
            // Type exists in the database
        } else {
            // Type doesn't exist in the database
        }
    }
}
