package com.example.fakeqqmusic.base;

import com.example.fakeqqmusic.base.network.musicData;

public interface MusicInfoContract {
    interface Presenter {
        void getMusiInfo(String ip);
        void onstart();
    }
    interface View extends BaseView<Presenter> {
        void setMusicData(musicData musicData);
        void showError();
        Boolean isACtive();
    }
}