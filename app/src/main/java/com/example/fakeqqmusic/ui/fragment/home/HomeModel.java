package com.example.fakeqqmusic.ui.fragment.home;

import androidx.core.view.WindowInsetsAnimationCompat;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.MusicInfoTask;
import com.example.fakeqqmusic.base.network.NetTask;

public class HomeModel implements HomeContract.Model {
    private static HomeModel INSTANCE = null;

    private LoadTasksCallBack loadTasksCallBack;

    public static HomeModel getInstance(){
        if (INSTANCE == null){
            INSTANCE = new HomeModel();
        }
        return INSTANCE;
    }
    @Override
    public void getHomeInfo(String ip, LoadTasksCallBack callBack) {
        callBack.onSuccess("success");
    }
}
