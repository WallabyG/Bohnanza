package com.example.bohnanza;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 기본 액티비티
 */
public class BaseActivity extends AppCompatActivity {

    protected InfoApplication infoApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        infoApplication = (InfoApplication) this.getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        infoApplication.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        clearReferences();

        super.onPause();
    }

    @Override
    protected void onResume() {
        clearReferences();

        super.onResume();
    }

    private void clearReferences() {
        Activity currentActivity = infoApplication.getCurrentActivity();

        if (this.equals(currentActivity)) {
            infoApplication.setCurrentActivity(null);
        }
    }

}
