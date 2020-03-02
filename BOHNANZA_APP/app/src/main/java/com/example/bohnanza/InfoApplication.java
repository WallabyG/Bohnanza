package com.example.bohnanza;

import android.app.Activity;
import android.app.Application;

/**
 *
 * 정보 공유 애플리케이션
 *
 * @author YJH
 * @version 1.0
 *
 */
public class InfoApplication extends Application {

    private Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

}
