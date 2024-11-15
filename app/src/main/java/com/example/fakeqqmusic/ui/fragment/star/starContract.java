package com.example.fakeqqmusic.ui.fragment.star;

import com.example.fakeqqmusic.base.BaseView;
import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.musicData;

public interface starContract {
    interface Model {
        void getStarInfo(String ip, LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getStarInfo(String ip);
        void onstart();
        void unSubscribe();
    }
    interface View extends BaseView<Presenter> {
        void showError();
        void setStarData(musicData starData);
        Boolean isACtive();
    }
}
