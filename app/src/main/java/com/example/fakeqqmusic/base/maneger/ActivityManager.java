package com.example.fakeqqmusic.base.maneger;


import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {
    private List<Activity> allActivities = new ArrayList<>();
    public void addActivity(Activity activity) {
        if (activity != null) {
            allActivities.add(activity);
        }
    }
    public void removeActivity(Activity activity) {
        if (activity != null) {
            allActivities.remove(activity);
        }
    }
    public void finishAll() {
        for (Activity activity : allActivities) {
            activity.finish();
        }

    }
    public Activity getTaskTop() {
        return allActivities.get(allActivities.size() - 1);
    }
}

