package com.arvind.leadstorex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.arvind.leadstorex.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // Duration in milliseconds (2 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                // User is already logged in
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
            } else {
                // User is not logged in
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish(); // Finish splash activity
        }, SPLASH_DURATION);
    }
}
