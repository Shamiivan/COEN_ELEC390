package com.example.coenelec390.ui.components;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.model.Component;

public class ComponentDetailFragment extends Fragment {
    private Component component;


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


        // Set view content
        tagTextView.setText(component.getTag());
        locationTextView.setText("Location : " + component.getLocation());
        mainCategoryTextView.setText("Main category : " + component.getMainCategory());
        subCategoryTextView.setText("Subcategory: "+ component.getSubCategory());
        partNumberTextView.setText("Name : " + component.getPartNumber());
        unitPriceTextView.setText("Unit Price : " +  String.valueOf(component.getUnitPrice()));
        quantityTextView.setText(String.valueOf("Quantity : " + component.getQuantity()));
        totalPriceTextView.setText(String.valueOf("Total Price : " + component.getTotalPrice()));
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
