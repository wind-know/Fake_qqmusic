package com.example.fakeqqmusic.ui.fragment.dashboard;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;

public class DashboardModel implements DashboardContract.Model {
    private static DashboardModel INSTANCE = null;

    private LoadTasksCallBack loadTasksCallBack;

    public static DashboardModel getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DashboardModel();
        }
        return INSTANCE;
    }
    @Override
    public void getDashboardInfo(String ip, LoadTasksCallBack callBack) {
        callBack.onSuccess("success");
    }
}
