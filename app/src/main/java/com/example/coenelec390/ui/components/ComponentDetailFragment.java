package com.example.coenelec390.ui.components;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private TextView locationTextView;
    private TextView mainCategoryTextView;
    private TextView subCategoryTextView;
    private TextView partNumberTextView;
    private TextView unitPriceTextView;
    private TextView quantityTextView;
    private TextView totalPriceTextView;
    private Button editButton;

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
        editButton = view.findViewById(R.id.editbutton);

        // Set view content
        tagTextView.setText(component.getTag());
        locationTextView.setText("Location : " + component.getLocation());
        mainCategoryTextView.setText("Main category : " + component.getMainCategory());
        subCategoryTextView.setText("Subcategory"+ component.getSubCategory());
        partNumberTextView.setText("Name : " + component.getPartNumber());
        unitPriceTextView.setText("Unit Price : " +  String.valueOf(component.getUnitPrice()));
        quantityTextView.setText(String.valueOf("Quantity : " + component.getQuantity()));
        totalPriceTextView.setText(String.valueOf("Total Price : " + component.getTotalPrice()));

//        TextView characteristicsTextView = view.findViewById(R.id.textViewCharacteristics);
//        characteristicsTextView.setText(component.getCharacteristics());

        Button editButton = view.findViewById(R.id.editbutton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
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
