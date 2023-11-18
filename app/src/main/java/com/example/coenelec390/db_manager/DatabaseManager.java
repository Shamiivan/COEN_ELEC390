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
        try {
            String mainCategory = component.getMainCategory();
            String subCategory = component.getSubCategory();
            String key = component.getPartNumber();
            String tag = component.getTag();

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
            String path = "components/" + mainCategory + "/" + subCategory + "/" +  key;
            addNFCTag(path, tag);
        } catch (Exception e) {
            Utils.print("Exception: " + e.getMessage());
        }
    }

    public void addNFCTag(String path, String tag){

        try {
            DatabaseReference tagRef = mDatabase.child("tags").child(tag);
            tagRef.setValue(path);
        } catch (Exception e) {
            Utils.print("Exception: " + e.getMessage());
        }
    }

    //search database and check if nfc exits
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
                    Component component = subCategorySnap.getValue(Component.class);
                    components.add(component);
                }
                listener.onComponentLoaded(components);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onComponentError(error.getMessage());
            }
        });
    }
    public interface OnSpecificComponentLoadedListener {
        void onSpecificComponent(Component component);

        void onSpecificComponentError(String errorMessage);
    }

    public void fetchSpecificComponent(String tag, OnSpecificComponentLoadedListener listener) {
        DatabaseReference reference = mDatabase.child("tags").child(tag);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String path = snapshot.getValue(String.class);

                if (path != null) {
                    DatabaseReference componentRef = FirebaseDatabase.getInstance().getReference().child(path);
                    componentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Component component = snapshot.getValue(Component.class);
                            listener.onSpecificComponent(component);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            listener.onSpecificComponentError(error.getMessage());
                        }
                    });
                } else {
                    // Handle the case where the path is null
                    listener.onSpecificComponentError("Path is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onSpecificComponentError(error.getMessage());
            }
        });
    }

    public interface ComponentSearchCallback {
        abstract void onComponentsFound(List<Component> components);

        void onError(String error);
    }


    public void DatabaseSearch(String searchQuery, ComponentSearchCallback callback) {
        mDatabase.child("components").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //fetch all components
                List<Component> CopiedComponents = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot subCategorySnapshot : categorySnapshot.getChildren()) {
                        for (DataSnapshot componentSnapshot : subCategorySnapshot.getChildren()) {
                            Component component = componentSnapshot.getValue(Component.class);
                            if (component != null && component.getCharacteristics() != null) {
                                for (Object value : component.getCharacteristics().values()) {
                                    String mainCategoryKey = categorySnapshot.getKey();
                                    String subCategoryKey = subCategorySnapshot.getKey();
                                    String partNumberKey = componentSnapshot.getKey();
                                    component.setMainCategory(mainCategoryKey);
                                    component.setSubCategory(subCategoryKey);
                                    component.setPartNumber(partNumberKey);
                                    CopiedComponents.add(component);
                                }
                            }
                        }
                    }
                }
                //filter all components
                List<Component> searchResults = new ArrayList<>();
                for (Component component : CopiedComponents) {
                    if (component.getCharacteristics().containsKey(searchQuery)) {
                        searchResults.add(component);
                    } else if (component.getLocation().equals(searchQuery)) {
                        searchResults.add(component);
                    } else if (component.getPartNumber().equals(searchQuery)) {
                        searchResults.add(component);
                    } else if (component.getMainCategory().equals(searchQuery)) {
                        searchResults.add(component);
                    } else if (component.getSubCategory().equals(searchQuery)) {
                        searchResults.add(component);
                    }
                }
                //return the found component
                callback.onComponentsFound(searchResults);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }
}
