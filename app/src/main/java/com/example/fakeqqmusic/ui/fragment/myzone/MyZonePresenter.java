package com.example.fakeqqmusic.ui.fragment.myzone;

import android.util.Log;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.musicData;

public class MyZonePresenter implements MyZoneContract.Presenter , LoadTasksCallBack<musicData> {
    private MyZoneContract.View mView;
    private MyZoneContract.Model mModel;
    public MyZonePresenter(MyZoneContract.View mView, MyZoneContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }
    @Override
    public void getMyZoneInfo(String ip) {
        mModel.getMyZoneInfo(ip, this);
    }

    @Override
    public void onstart() {
        getMyZoneInfo("");
    }

    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }

    @Override
    public void onSuccess(musicData data) {
        if (mView!=null&&mView.isACtive()) {
            mView.setMyZoneData(data);
        }
    }

    @Override
    public void onFailed() {

    }
}
