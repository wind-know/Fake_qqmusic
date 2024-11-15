package com.example.fakeqqmusic.ui.fragment.star;

import android.os.Handler;
import android.os.Looper;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.musicData;

public class starPresenter implements starContract.Presenter, LoadTasksCallBack<musicData> {
    private starContract.View mView;
    private starContract.Model mModel;
    public starPresenter(starContract.View mView, starContract.Model mModel)
    {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }
    @Override
    public void getStarInfo(String ip) {

        mModel.getStarInfo(ip, this);
    }

    @Override
    public void onstart() {
        getStarInfo("");
    }

    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }

    @Override
    public void onSuccess(musicData data) {
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                mView.setStarData(data);
//            }
//        });
        if (mView!=null&&mView.isACtive()) {
            mView.setStarData(data);
        }
    }

    @Override
    public void onFailed() {

    }
}
