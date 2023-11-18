package com.example.coenelec390;

import android.os.Bundle;
import android.util.Log;

import com.example.coenelec390.db_manager.Component;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.example.coenelec390.ui.item.ComponentDetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.coenelec390.databinding.MainBinding;

import com.google.firebase.FirebaseApp;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ComponentDetailFragment.OnBackPressedListener{

    private MainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DatabaseManager mDatabase = new DatabaseManager();
        super.onCreate(savedInstanceState);

        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_items, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        mDatabase.DatabaseSearch("MSP430FR5989", new DatabaseManager.ComponentSearchCallback() {

            @Override
            public void onComponentsFound(List<Component> components) {
                // Check if the list is not empty
                if (!components.isEmpty()) {
                    // Loop through the found components and log their details
                    for (Component component : components) {
                        Log.d("ComponentSearch", "---------------------------------");
                        Log.d("ComponentSearch", "Part Number: " + component.getPartNumber());
                        Log.d("ComponentSearch", "Main Category: " + component.getMainCategory());
                        Log.d("ComponentSearch", "Sub Category: " + component.getSubCategory());
                        Log.d("ComponentSearch", "Location: " + component.getLocation());
                        Log.d("ComponentSearch", "Quantity: " + component.getQuantity());
                        for (Map.Entry<String, Object> entry : component.getCharacteristics().entrySet()) {
                            Log.d("ComponentSearch", "Characteristic - " + entry.getKey() + ": " + entry.getValue());
                        }
                        Log.d("ComponentSearch", "---------------------------------");
                    }
                } else {
                    Log.d("ComponentSearch", "No components found with the characteristic");
                }
            }

            @Override
            public void onError(String error) {
                Log.e("ComponentSearch", "Error searching components: " + error);
            }
        });

    }

    @Override
    public void onBackPressed() {
        ComponentDetailFragment fragment = (ComponentDetailFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        if (fragment != null && fragment.isVisible()) {
            fragment.handleBackPress();
        } else {
            super.onBackPressed();
        }
    }
}