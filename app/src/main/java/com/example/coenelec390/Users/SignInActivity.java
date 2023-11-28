package com.example.coenelec390.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coenelec390.R;
import com.example.coenelec390.ui.dashboard.DashboardFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button signInButton = findViewById(R.id.buttonSignIn);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    signInUser(email, password);
                } else {
                    Toast.makeText(SignInActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setContentView(R.layout.activity_sign_in);

        // Find the TextView for the sign-up prompt
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView signUpPrompt = findViewById(R.id.signUpButton_question);

        // Set an OnClickListener to the TextView
        signUpPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start SignUpActivity
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("SignIn", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Redirect to another activity after successful login
                        Intent intent = new Intent(SignInActivity.this, DashboardFragment.class);
                        startActivity(intent);
                    } else {
                        Log.w("SignIn", "signInWithEmail:failure", task.getException());
                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
