package com.example.coenelec390.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coenelec390.R;
import com.example.coenelec390.ui.notifications.NotificationsFragment;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button signUpButton = findViewById(R.id.buttonSignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    createNewUser(email, password);
                } else {
                    Toast.makeText(SignUpActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createNewUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("SignUp", "createUserWithEmail:success");
                        // Redirect to another activity after successful sign-up
                         Intent intent = new Intent(SignUpActivity.this, NotificationsFragment.class);
                         startActivity(intent);
                    } else {
                        Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}