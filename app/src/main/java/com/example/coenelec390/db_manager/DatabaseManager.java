package com.example.coenelec390.db_manager;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.coenelec390.ui.item.AddItem;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//assign2 libraries


public class DatabaseManager {

    private DatabaseReference mDatabase;
    static int counter = 1;


    // Constructor
    public DatabaseManager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Adds a component to the database.
     * @param type The type of component (active/passive).
     * @param category The category of the component (resistors, transistors, etc.).
     * @param model The model of the component.
     * @param component The component data.
     */
    public void addComponent(String type, String category, String model, Component component) {
        mDatabase.child("components").child(type).child(category).child(model).setValue(component);
        //mDatabase.child("NFC TAG IDs").child(Integer.toString(counter)).setValue(9892);
        //counter++;
    }


    public Task<Boolean> findNFC(String tag) {
        DatabaseReference ref = mDatabase.child("NFC TAG IDs").child(tag);
        return ref.get().continueWith(new Continuation<DataSnapshot, Boolean>() {
            @Override
            public Boolean then(@NonNull Task<DataSnapshot> task) throws Exception {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    return dataSnapshot.exists();
                } else {
                    // Handle any potential errors here
                    return false; // Return false in case of error
                }
            }
        });

    }

    private void addItemFragment(String tag) {
        AddItem addItemFragment = new AddItem();
        Bundle args = new Bundle();
        args.putString("nfctag", tag);
        addItemFragment.setArguments(args);
        addItemFragment.show(addItemFragment.getFragmentManager(), "my");

    }

    public interface BooleanDataCallback {
        void onDataReceived(boolean result);
    }







    public void deleteComponent(String type, String category, String model) {
        mDatabase.child("components").child(type).child(category).child(model).removeValue();
        //if you want to remove a model from the database
    }
    public void updateComponentFields(String type, String category, String model, Map<String, Object> updates) {
        mDatabase.child("components").child(type).child(category).child(model).updateChildren(updates);
        //the user only inputs Map<String, Object> updates, the rest stays the same
    }
    public interface DataCallback {
        void onDataReceived(List<String> data);
        void onError(String error);
    }

//NEW FUNCTIONS
    //this isnt 100% stable fetchCategories
    public void fetchCategories(String type, DataCallback callback) {
        DatabaseReference ref = mDatabase.child("components").child(type);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> categories = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    categories.add(categorySnapshot.getKey());
                }
                callback.onDataReceived(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }
    public void fetchModels(String type, String category, DataCallback callback) {
        DatabaseReference ref = mDatabase.child("components").child(type).child(category);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> models = new ArrayList<>();
                for (DataSnapshot modelSnapshot : dataSnapshot.getChildren()) {
                    models.add(modelSnapshot.getKey());
                }
                callback.onDataReceived(models);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }
    public void fetchModelDetails(String type, String category, String model, ValueEventListener listener) {
        DatabaseReference ref = mDatabase.child("components").child(type).child(category).child(model);
        ref.addListenerForSingleValueEvent(listener);
    }

}
