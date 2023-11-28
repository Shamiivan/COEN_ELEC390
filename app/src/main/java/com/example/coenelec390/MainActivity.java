package com.example.coenelec390;

import android.os.Bundle;
import android.view.View;

import com.example.coenelec390.ui.item.AddItem;
import com.example.coenelec390.ui.item.AddItemActivity;
import com.example.coenelec390.ui.item.ComponentDetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.coenelec390.databinding.MainBinding;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements ComponentDetailFragment.OnBackPressedListener{

    private MainBinding binding;
    FloatingActionButton addFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.print("Activity created");
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            Utils.print("Not null");
        }

        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(this);



        // Remove the old setup with NavController as it's now handled above
        // NavigationUI.setupWithNavController(binding.navView, navController);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Existing AppBarConfiguration setup
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_items, R.id.navigation_notifications, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_dashboard) {
                navController.navigate(R.id.navigation_dashboard);
            } else if (itemId == R.id.navigation_items) {
                navController.navigate(R.id.navigation_items);
            } else if (itemId == R.id.navigation_notifications) {
                navController.navigate(R.id.navigation_notifications);
            } else if (itemId == R.id.navigation_search) {
                navController.navigate(R.id.navigation_search);
            } else if (itemId == R.id.navigation_categories) {
                navController.popBackStack(R.id.navigation_categories, false);
                navController.navigate(R.id.navigation_categories);
            }
            // Add else-if for other items if needed

            return true;
        });




        //floating add item button
        addFab = findViewById(R.id.add_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItem addItem = new AddItem();
                addItem.show(getSupportFragmentManager(), "Add Fragment");
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