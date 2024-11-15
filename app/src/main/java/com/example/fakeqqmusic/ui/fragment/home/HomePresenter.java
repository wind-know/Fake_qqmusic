package com.example.fakeqqmusic.ui.fragment.home;

import com.example.fakeqqmusic.base.MusicInfoContract;
import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.NetTask;
import com.example.fakeqqmusic.base.network.musicData;

public class HomePresenter implements HomeContract.Presenter, LoadTasksCallBack<musicData> {
    private HomeContract.Model mModel;
    private HomeContract.View  addTaskView;
    public HomePresenter(HomeContract.View addTaskView, HomeContract.Model mModel) {
        this.mModel = mModel;
        this.addTaskView = addTaskView;
        addTaskView.setPresenter(this);
    }
    @Override
    public void onSuccess(musicData data) {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void getHomeInfo(String ip) {
        mModel.getHomeInfo(ip, this);
    }

    @Override
    public void unSubscribe() {
        mModel = null;
        addTaskView = null;
    }
}
