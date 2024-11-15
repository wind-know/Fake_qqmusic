package com.example.fakeqqmusic.ui.musicplay;

import com.example.fakeqqmusic.base.BaseView;
import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.musicData;

import java.util.List;

public interface MusicContract {
    interface Model {
        void getMusicInfo(String ip, LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getMusicInfo(String ip);
        void onstart();
        void unSubscribe();
    }
    interface View extends BaseView<Presenter> {
        void showError();
        void setMusicData(List<musicData> starData);
        Boolean isACtive();
    }
}
