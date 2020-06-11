package com.xzit.app.activity.spashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.xzit.app.R;
import com.xzit.app.activity.login.LoginActivity;
import com.xzit.app.activity.welcomescreen.WelcomeActivity;

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
    private void initialization()
    {
        loadSplash();
    }
    private void listener()
    {

    }

    private void loadSplash()
    {
        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
