package com.example.fakeqqmusic.ui.fragment.dashboard;

import com.example.fakeqqmusic.base.BaseView;
import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.musicData;

public interface DashboardContract {
    interface Model {
        void getDashboardInfo(String ip,  LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getDashboardInfo(String ip);
        void unSubscribe();
    }
    interface View extends BaseView<Presenter> {
        void setDashboardData(musicData musicData);
        void showError();
        Boolean isACtive();
    }
}
