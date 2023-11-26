package com.example.coenelec390.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.coenelec390.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccessRequestsActivity extends AppCompatActivity {

    private ListView requestsListView;
    private ArrayAdapter<String> adapter;
    private List<String> requestsList = new ArrayList<>();
    private DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("listOfUsers");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_requests);

        requestsListView = findViewById(R.id.requestsListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requestsList);
        requestsListView.setAdapter(adapter);

        fetchAccessRequests();
    }

    private void fetchAccessRequests() {
        usersRef.orderByChild("status").equalTo("pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userInfo = snapshot.child("username").getValue(String.class) + " - " + snapshot.child("email").getValue(String.class);
                    requestsList.add(userInfo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AccessRequestsActivity.this, "Failed to load requests.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
