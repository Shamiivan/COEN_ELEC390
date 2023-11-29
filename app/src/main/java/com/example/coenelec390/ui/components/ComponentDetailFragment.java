package com.example.coenelec390.ui.components;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.fragment.app.Fragment;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.example.coenelec390.model.Component;
import com.example.coenelec390.ui.categories.CategoryViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class ComponentDetailFragment extends Fragment {
    private Component component;

    DatabaseManager db = new DatabaseManager();



    // UI ELEMENTS
    // Declare view references as member variables
    private TextView tagTextView;
    private EditText locationTextView;
    private EditText mainCategoryTextView;
    private EditText subCategoryTextView;
    private EditText partNumberTextView;
    private EditText unitPriceTextView;
    private EditText quantityTextView;
    private TextView totalPriceTextView;
    private EditText characteristicsTextView;
    private Button editButton;
    private Button saveButton;
    private Button deleteButton;
    private CategoryViewModel viewModel;

    public ComponentDetailFragment() {
        // Required empty public constructor
    }

    public static ComponentDetailFragment newInstance(Component component) {
        ComponentDetailFragment fragment = new ComponentDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("component", component);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            component = (Component) getArguments().getSerializable("component");
        }
    }
    //...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_detail, container, false);


        // Set the dynamic title
        if (getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                String path = component.getSubCategory() + "/" + component.getPartNumber();
                actionBar.setTitle(path);  // Replace with your dynamic title
            }
        }

        // Initialize view references
        tagTextView = view.findViewById(R.id.textViewTag);
        locationTextView = view.findViewById(R.id.textViewLocation);
        mainCategoryTextView = view.findViewById(R.id.textViewMainCategory);
        subCategoryTextView = view.findViewById(R.id.textViewSubCategory);
        partNumberTextView = view.findViewById(R.id.textViewPartNumber);
        unitPriceTextView = view.findViewById(R.id.textViewUnitPrice);
        quantityTextView = view.findViewById(R.id.textViewQuantity);
        totalPriceTextView = view.findViewById(R.id.textViewTotalPrice);
        editButton = view.findViewById(R.id.editButton);
        saveButton = view.findViewById(R.id.saveButton);
        characteristicsTextView = view.findViewById(R.id.textViewCharacteristics);
        deleteButton = view.findViewById(R.id.delete);



        // Set view content
        tagTextView.setText(component.getTag());
        locationTextView.setText( component.getLocation());
        mainCategoryTextView.setText( component.getMainCategory());
        subCategoryTextView.setText( component.getSubCategory());
        partNumberTextView.setText(component.getPartNumber());
        unitPriceTextView.setText(  String.valueOf(component.getUnitPrice()));
        quantityTextView.setText(String.valueOf( component.getQuantity()));
        totalPriceTextView.setText(String.valueOf(component.getTotalPrice()));
        //characteristicsTextView.setText(component.getCharacteristics());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationTextView.setEnabled(true);
                mainCategoryTextView.setEnabled(true);
                subCategoryTextView.setEnabled(true);
                partNumberTextView.setEnabled(true);
                unitPriceTextView.setEnabled(true);
                quantityTextView.setEnabled(true);
                characteristicsTextView.setEnabled(true);

                saveButton.setVisibility(View.VISIBLE);

                editButton.setVisibility(View.GONE);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String updatedLocation = locationTextView.getText().toString();
                String updatedMainCategory = mainCategoryTextView.getText().toString();
                String updatedSubCategory = subCategoryTextView.getText().toString();
                String updatedPartNumber = partNumberTextView.getText().toString();
                String updatedUnitPriceString = unitPriceTextView.getText().toString();
                Double updatedUnitPrice = Double.parseDouble(updatedUnitPriceString);
                String updatedQuantityString = quantityTextView.getText().toString();
                int updatedQuantity = Integer.parseInt(updatedQuantityString);
                //String[] updatedCharacteristics = characteristicsTextView.getText().toString().split(": ");
                 Component updatedcomponent = new Component();
                /*component.setLocation(updatedLocation);
                component.setMainCategory(updatedMainCategory);
                component.setSubCategory(updatedSubCategory);
                component.setPartNumber(updatedPartNumber);
                component.setUnitPrice(updatedUnitPrice);
                component.setQuantity(updatedQuantity);
                component.setCharacteristics("Description" ,updatedCharacteristics);*/

                /*String existingPath = component.getComponentCommaSeparated();

                // Update the component in the database
                DatabaseReference componentRef = FirebaseDatabase.getInstance()
                        .getReference().child(existingPath);  // Replace with the actual path in your database
                componentRef.setValue(component)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Component updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Failed to update component: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });*/

                //Map<String, Object> characteristics3 = new HashMap<>();
                //characteristics3.put("Description", updatedCharacteristics[1]);

                Map<String, Object> updates = new HashMap<>();
                updates.put("location" , updatedLocation);
                updates.put("quantity" , updatedQuantity);
                updates.put("unitPrice" , updatedUnitPrice);
                //updates.put();

                Utils.print("update started");
                db.updateComponentFields(updatedMainCategory, updatedSubCategory, updatedPartNumber, updates, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Handle successful update
                            //Log.d(TAG, "Update successful");
                            Utils.print("Update successful BASHAR");
                            Utils.print("type:" + updatedMainCategory + "subcategory"+updatedSubCategory + "part number" + updatedPartNumber);
                        } else {
                            // Handle failed update
                            Exception e = task.getException();
                            Utils.print("Update failed");

                            if (e != null) {
                              //  Log.e(TAG, "Update failed: " + e.getMessage());
                                Utils.print("Update failed 2222");
                            }
                        }
                    }
                });


                Utils.print("FINISHED update");


                locationTextView.setEnabled(false);
                mainCategoryTextView.setEnabled(false);
                subCategoryTextView.setEnabled(false);
                partNumberTextView.setEnabled(false);
                unitPriceTextView.setEnabled(false);
                quantityTextView.setEnabled(false);
                characteristicsTextView.setEnabled(false);
                editButton.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.GONE);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteComponent(component);

                Bundle bundle = new Bundle();
                NavController navController = NavHostFragment.findNavController(ComponentDetailFragment.this);
                navController.navigate(R.id.action_delete_to_success_fragment, bundle);
            }
        });
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Utils.print("On DestroyView " + component.getComponent());
        component = null;
        tagTextView = null;
        tagTextView = null;
        locationTextView = null;
        mainCategoryTextView = null;
        subCategoryTextView = null;
        partNumberTextView = null;
        unitPriceTextView = null;
        quantityTextView = null;
        totalPriceTextView = null;
        editButton = null;
    }
}
