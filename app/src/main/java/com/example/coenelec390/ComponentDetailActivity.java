package com.example.coenelec390;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coenelec390.model.Component;
import com.example.coenelec390.ui.components.ComponentDetailFragment;

public class ComponentDetailActivity extends AppCompatActivity {

    private Component component;

    public static Intent newIntent(Context context, Component component) {
        Intent intent = new Intent(context, ComponentDetailActivity.class);
        intent.putExtra("component", component);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_detail);

        if (getIntent() != null) {
            component = (Component) getIntent().getSerializableExtra("component");
        }

        // Set the dynamic title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            String path = component.getSubCategory() + "/" + component.getPartNumber();
            actionBar.setTitle(path);  // Replace with your dynamic title
        }

        // Check if the activity is created for the first time
        if (savedInstanceState == null) {
            // Create and add the ComponentDetailFragment
            ComponentDetailFragment fragment = ComponentDetailFragment.newInstance(component);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            //navigation_categories
        }
    }
}
