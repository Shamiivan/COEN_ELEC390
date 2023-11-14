package com.example.coenelec390.db_manager;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.coenelec390.Utils;
import com.example.coenelec390.ui.item.AddItemActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
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

    /*
     * Adds a component to the database.
     * @param  mainCategory of component (active/passive).
     * @param Subcategory  name the component (resistors, transistors, etc.).
     * @param model The model of the component.
     * @param component The component data.
     */

    public void addComponent(Component component) {
        // TODO : HAVE TO CHECK FOR TAG if it exist ovewrite it
        //TODO : HAVE TO MAKE AN RFID IF NOT AVAILABLE
        try {
            String mainCategory = component.getMainCategory();
            String subCategory = component.getSubCategory();
            String key = component.getPartNumber();

            DatabaseReference componentRef = mDatabase.child("components").child(mainCategory).child(subCategory).child(key);
            componentRef.setValue(component)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        // added for error handling
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Utils.print("Error adding component: " + task.getException().getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            Utils.print("Exception: " + e.getMessage());
        }
    }


    //TODO ADD CATEGORY Method
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
        AddItemActivity addItemFragment = new AddItemActivity();
        Bundle args = new Bundle();
        args.putString("nfctag", tag);
        addItemFragment.setArguments(args);
        addItemFragment.show(addItemFragment.getFragmentManager(), "my");

    }


    public void deleteComponent(String type, String category, String model) {
        mDatabase.child("components").child(type).child(category).child(model).removeValue();
        //if you want to remove a model from the database
    }
    public void updateComponentFields(String type, String category, String model, Map<String, Object> updates) {
        mDatabase.child("components").child(type).child(category).child(model).updateChildren(updates);
        //the user only inputs Map<String, Object> updates, the rest stays the same
    }




    /*
    *
    *
    *  */

    public interface OnMainCategoriesLoadedListener {
        void onMainCategoriesLoaded(List<String> mainCategories);
        void onMainCategoriesError(String errorMessage);
    }

    public void fetchMainCategories(OnMainCategoriesLoadedListener listener){
       DatabaseReference reference = mDatabase.child("components");
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               List<String> mainCategories = new ArrayList<>();
               for (DataSnapshot mainCategorySnap : snapshot.getChildren()) {
                   String category = mainCategorySnap.getKey();
                   mainCategories.add(category);
               }
               listener.onMainCategoriesLoaded(mainCategories);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               listener.onMainCategoriesError(error.getMessage());
           }
       });
    }

    /*
    * Subcategories
    * */
    public interface OnSubCategoriesLoadedListener {
        void onSubCategoriesLoaded(List<String> subCategories);
        void onSubCategoriesError(String errorMessage);
    }

    public void fetchSubCategories(String mainCategory, OnSubCategoriesLoadedListener listener) {
        DatabaseReference reference = mDatabase.child("components").child(mainCategory);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> subCategories = new ArrayList<>();
                for (DataSnapshot subCategorySnap : snapshot.getChildren()) {
                    String subCategory = subCategorySnap.getKey();
                    subCategories.add(subCategory);
                }
                listener.onSubCategoriesLoaded(subCategories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onSubCategoriesError(error.getMessage());
            }
        });
    }

    /*
    * Component listing
    *
    * */

    public interface OnComponentLoadedListener{
        void onComponentLoaded(List<Component> components);
        void onComponentError(String errorMessage);
    }

    public void fetchComponents(String mainCategory,String subCategory, OnComponentLoadedListener listener) {
        DatabaseReference reference = mDatabase.child("components").child(mainCategory).child(subCategory);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Component> components = new ArrayList<>();
                for (DataSnapshot subCategorySnap : snapshot.getChildren()) {
                    Component subCategory = subCategorySnap.getValue(Component.class);
                    components.add(subCategory);
                }
                listener.onComponentLoaded(components);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onComponentError(error.getMessage());
            }
        });
    }
}
