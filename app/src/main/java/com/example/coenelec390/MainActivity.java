package com.example.coenelec390;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        Toast.makeText(this, "Back button pressed", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
//        Utils.print("Back button pressed");
//        // Check if the current destination is not the start destination
//        if (navController.getCurrentDestination().getId() != navController.getGraph().getStartDestination()) {
//            Utils.print(navController.getCurrentDestination().getDisplayName());
//            // Navigate up in the navigation hierarchy
//            navController.navigateUp();
//        } else {
//            // Optional: Add another check here if you are at the 'Categories' fragment and want special handling
//            // e.g., if at 'Categories', show a dialog or exit the app
//
//            // Otherwise, call the super method which typically exits the app
//            super.onBackPressed();
//        }
    }

}