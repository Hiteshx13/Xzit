package com.xzit.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.xzit.app.R;
import com.xzit.app.repository.LoginRepository;
import com.xzit.app.utils.AppPreference;

import java.util.concurrent.Executor;

public class SplashScreen extends AppCompatActivity {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    Intent intent;

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
                intent = new Intent(SplashScreen.this, SelectLoginActivity.class);

                if (new AppPreference().isLoggedIn()) {

                    BiometricManager biometricManager = BiometricManager.from(SplashScreen.this);
                    switch (biometricManager.canAuthenticate()) {
                        case BiometricManager.BIOMETRIC_SUCCESS:
                            intent = new Intent(SplashScreen.this, DashboardActivity.class);
                            performBiometricAuthentication();
                            Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                            startActivity(intent);
                            finish();
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                            startActivity(intent);
                            finish();
                            break;
                        case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                            startActivity(intent);
                            finish();
                            break;
                    }
                } else {
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    void performBiometricAuthentication() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(SplashScreen.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}
