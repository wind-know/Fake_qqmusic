package com.example.fakeqqmusic.ui.fragment.home;

import com.example.fakeqqmusic.base.BaseView;
import com.example.fakeqqmusic.base.MusicInfoContract;
import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.NetTask;
import com.example.fakeqqmusic.base.network.musicData;

public interface HomeContract {
    interface Model {
        void getHomeInfo(String ip,  LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getHomeInfo(String ip);
        void unSubscribe();
    }
    interface View extends BaseView<Presenter> {
        void setHomeData(musicData musicData);
        void showError();
        Boolean isACtive();
    }
}
