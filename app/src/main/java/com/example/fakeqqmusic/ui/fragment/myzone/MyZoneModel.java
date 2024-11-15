package com.example.fakeqqmusic.ui.fragment.myzone;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.MusicInfoTask;

public class MyZoneModel implements MyZoneContract.Model{
    @Override
    public void getMyZoneInfo(String ip, LoadTasksCallBack callBack) {
        MusicInfoTask.getInstance().execute(ip, callBack);
    }
}
