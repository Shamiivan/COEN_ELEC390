package com.example.coenelec390.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coenelec390.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, usernameEditText, passwordEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEditText = findViewById(R.id.editTextEmail);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);

        findViewById(R.id.requestAccessButton).setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            requestAccess(email, username);
        });

        findViewById(R.id.signUpButton).setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            checkUserStatusAndSignUp(email, password);
        });
    }

    public void signUpUser(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User account created successfully
                    } else {
                        // Handle unsuccessful sign-ups
                    }
                });
    }

    public void requestAccess(String email, String username) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("listOfUsers");
        String userId = usersRef.push().getKey(); // Generate a unique ID for the user

        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("username", username);
        userData.put("status", "pending");

        usersRef.child(userId).setValue(userData)
                .addOnSuccessListener(aVoid -> {
                    // Notify the user that the access request has been sent
                    Toast.makeText(SignUpActivity.this, "Access request sent.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                    Toast.makeText(SignUpActivity.this, "Failed to send access request.", Toast.LENGTH_SHORT).show();
                });
    }

    public void checkUserStatusAndSignUp(String email, String password) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("listOfUsers");
        Query query = usersRef.orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String status = snapshot.child("status").getValue(String.class);
                        if ("accepted".equals(status)) {
                            signUpUser(email, password);
                        } else {
                            // Show message: "Your access request is still pending or denied."
                        }
                    }
                } else {
                    // User not found in listOfUsers, handle accordingly
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
}
