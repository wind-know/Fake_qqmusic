package com.example.fakeqqmusic.base.maneger;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class BasicApplication extends Application {
    private static ActivityManager activityManager;
    private static BasicApplication application;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        activityManager = new ActivityManager();
        context = getApplicationContext();
        application = this;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    public static ActivityManager getActivityManager() {
        return activityManager;
    }

    public static Context getContext() {
        return context;
    }

    public static BasicApplication getApplication() {
        return application;
    }
}


