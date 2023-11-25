package com.example.coenelec390.db_manager;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.coenelec390.model.Component;
import com.example.coenelec390.ui.item.AddItem;
import com.example.coenelec390.Utils;
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


    public void addComponent(Component component) {
        try {
            String mainCategory = component.getMainCategory();
            String subCategory = component.getSubCategory();
            String key = component.getPartNumber();
            String tag = component.getTag();
            String path = component.getComponentCommaSeparated();

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
            addComponentTag(tag, path );
        } catch (Exception e) {
            Utils.print("Exception: " + e.getMessage());
        }
    }

    public void addComponentTag(String id , String path) {
        //mDatabase.child("components").child(type).child(category).child(model).setValue(component);
        mDatabase.child("NFC TAG IDs").child(id).setValue(path)/*.setValue(3453)*/;
        //mDatabase.child("NFC TAG IDs").child(type).get()/*.setValue(3453)*/;

        counter++;
    }
    public interface OnComponentStringLoadedListener {
        void onComponentStringLoaded(String componentString);
        void onComponentError(String errorMessage);
    }
    public void getNFCvalue(String id ,OnComponentStringLoadedListener listener){
         String returnString ;
        mDatabase.child("NFC TAG IDs").child(id);
        DatabaseReference reference = mDatabase.child("NFC TAG IDs").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 String componentString = snapshot.getValue(String.class);
                //returnString = snapshot.getValue(String.class);
                if (componentString != null) {
                    listener.onComponentStringLoaded(componentString);
                    //returnString[0] = componentString;
                } else {
                    listener.onComponentError("Component not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onComponentError(error.getMessage());
            }
        });

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

    public interface OnComponentListener {
        void onComponentLoaded(Component component);
        void onComponentError(String errorMessage);
    }

    public void fetchcomp(String mainCategory,String SubCat, String partName, OnComponentListener listener) {
        DatabaseReference reference = mDatabase.child("components").child(mainCategory).child(SubCat).child(partName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //List<String> subCategories = new ArrayList<>();
                Component component = snapshot.getValue(Component.class);

                listener.onComponentLoaded(component);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onComponentError(error.getMessage());
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

    public void fetchComponent(String tag, OnSpecificComponentLoadedListener listener) {
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
    public void fetchModelDetails(String type, String category, String model, ValueEventListener listener) {
        DatabaseReference ref = mDatabase.child("components").child(type).child(category).child(model);
        ref.addListenerForSingleValueEvent(listener);
    }

}
