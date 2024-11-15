package com.example.fakeqqmusic.ui.fragment.star;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.MusicInfoTask;

public class starModel implements starContract.Model{
    @Override
    public void getStarInfo(String ip, LoadTasksCallBack callBack) {
        MusicInfoTask.getInstance().execute(ip, callBack);
    }
    public static void getsomedate(){

    }

}
