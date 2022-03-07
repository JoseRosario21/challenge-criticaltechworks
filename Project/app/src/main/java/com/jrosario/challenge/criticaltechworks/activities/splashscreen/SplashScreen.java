package com.jrosario.challenge.criticaltechworks.activities.splashscreen;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.jrosario.challenge.criticaltechworks.R;
import com.jrosario.challenge.criticaltechworks.activities.main.MainActivity;

import java.util.concurrent.Executor;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private final String TAG = SplashScreen.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            checkBiometric();
        } else {
            goToMainActivity();
        }
    }

    private void checkBiometric() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d(TAG, "App can authenticate using biometrics.");
                askForBiometricLogin();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                goToMainActivity();
                Log.e(TAG, "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                goToMainActivity();
                Log.e(TAG, "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                goToMainActivity();
                Log.e(TAG, "No fingerprint data available in this device");
                break;
        }
    }

    private void askForBiometricLogin() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(SplashScreen.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e(TAG, "Authentication error: " + errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.d(TAG, "Authentication succeeded!");
                goToMainActivity();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e(TAG, "Authentication failed");
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}