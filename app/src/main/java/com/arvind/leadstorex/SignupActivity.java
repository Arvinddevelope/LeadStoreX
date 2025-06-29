package com.arvind.leadstorex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signupButton;
    private TextView loginLink;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // UI references
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupButton = findViewById(R.id.signupButton);
        loginLink = findViewById(R.id.loginLink);

        // Sign Up button logic
        signupButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String pass = passwordEditText.getText().toString().trim();
            String confirmPass = confirmPasswordEditText.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                showSnackbar(v, "Please fill all fields");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showSnackbar(v, "Please enter a valid email");
                return;
            }

            if (pass.length() < 6) {
                showSnackbar(v, "Password must be at least 6 characters");
                return;
            }

            if (!pass.equals(confirmPass)) {
                showSnackbar(v, "Passwords do not match");
                return;
            }

            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(authResult -> {
                        showSnackbar(v, "Account created successfully");
                        startActivity(new Intent(this, DashboardActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                            showSnackbar(v, "Signup failed: " + e.getMessage()));
        });

        // Navigate to Login
        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setBackgroundTint(getColor(R.color.colorPrimary))
                .setTextColor(getColor(android.R.color.white))
                .show();
    }
}
