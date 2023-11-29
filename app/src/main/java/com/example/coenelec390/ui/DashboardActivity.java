package com.example.coenelec390.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.coenelec390.R;
import com.example.coenelec390.database.Component;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    // Views
    private TextView itemsTextView;
    private TextView groupsTextView;
    private TextView totalQuantityTextView;
    private TextView totalValueTextView;

    // Firebase
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        itemsTextView = findViewById(R.id.textViewItemsValue);
        groupsTextView = findViewById(R.id.textViewGroupsValue);
        totalQuantityTextView = findViewById(R.id.textViewTotalQuantityValue);
        totalValueTextView = findViewById(R.id.textViewTotalValue);

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Fetch components
        fetchComponents();
    }

    private void fetchComponents() {
        mDatabase.child("components").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Component> copiedComponents = new ArrayList<>();
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
                                    copiedComponents.add(component);
                                }
                            }
                        }
                    }
                }

                // Update UI with the fetched data
                updateDashboard(copiedComponents);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void updateDashboard(List<Component> components) {
        // Logic to calculate the summary data from components
        // This is a placeholder for your actual calculation logic
        int totalItems = components.size();
        int totalGroups = calculateTotalGroups(components);
        int totalQuantity = calculateTotalQuantity(components);
        String totalValue = calculateTotalValue(components);

        itemsTextView.setText(String.valueOf(totalItems));
        groupsTextView.setText(String.valueOf(totalGroups));
        totalQuantityTextView.setText(totalQuantity + " units");
        totalValueTextView.setText("$" + totalValue);
    }

    private int calculateTotalGroups(List<Component> components) {
        // Placeholder for actual calculation logic
        return 0;
    }

    private int calculateTotalQuantity(List<Component> components) {
        // Placeholder for actual calculation logic
        return 0;
    }

    private String calculateTotalValue(List<Component> components) {
        // Placeholder for actual calculation logic
        return "0";
    }
}
