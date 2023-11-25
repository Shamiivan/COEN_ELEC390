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
        super.onCreate(savedInstanceState);

        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(this);


        /* Get reference to the BottomNavigationView widget from layout.*/
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_items, R.id.navigation_notifications, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);



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