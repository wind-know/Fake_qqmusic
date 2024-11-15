package com.example.fakeqqmusic.ui.fragment.myzone;

import com.example.fakeqqmusic.base.BaseView;
import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.ui.fragment.star.starContract;

public interface MyZoneContract {

    interface Model {
        void getMyZoneInfo(String ip, LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getMyZoneInfo(String ip);
        void onstart();
        void unSubscribe();
    }
    interface View extends BaseView<MyZoneContract.Presenter> {
        void showError();
        void setMyZoneData(musicData starData);
        Boolean isACtive();
    }
}
