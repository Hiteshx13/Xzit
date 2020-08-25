package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.xzit.app.R;
import com.xzit.app.utils.AppPreference;

public class SplashScreen extends AppCompatActivity {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        setContentView(R.layout.activity_splash_screen);

        initialization();

        listener();
    }

    private void initialization() {
        loadSplash();
    }

    private void listener() {

        Log.e("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
    }

    private void loadSplash() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, SelectLoginActivity.class);

                if (new AppPreference().isLoggedIn()) {
                    intent = new Intent(SplashScreen.this, DashboardActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
