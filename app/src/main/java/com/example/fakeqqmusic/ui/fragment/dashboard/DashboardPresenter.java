package com.example.fakeqqmusic.ui.fragment.dashboard;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.musicData;

public class DashboardPresenter implements DashboardContract.Presenter, LoadTasksCallBack<musicData> {
    private DashboardContract.Model mModel;
    private DashboardContract.View  addTaskView;
    public DashboardPresenter(DashboardContract.View addTaskView, DashboardContract.Model mModel) {
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
    public void getDashboardInfo(String ip) {

    }

    @Override
    public void unSubscribe() {
        mModel = null;
        addTaskView = null;
    }
}
