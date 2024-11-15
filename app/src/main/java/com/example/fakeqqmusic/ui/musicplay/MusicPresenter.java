package com.example.fakeqqmusic.ui.musicplay;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.myview.DiscView;
import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.musicData;

import java.util.List;

public class MusicPresenter implements MusicContract.Presenter, LoadTasksCallBack<List<musicData>> {
    private MusicContract.View mView;
    private MusicContract.Model mModel;
    public MusicPresenter(MusicContract.View mView, MusicContract.Model mModel)
    {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }
    @Override
    public void getMusicInfo(String ip) {
        mModel.getMusicInfo(ip, this);
    }

    @Override
    public void onstart() {
        getMusicInfo("");
    }

    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }

    @Override
    public void onSuccess(List<musicData> data) {
        if (mView!=null&&mView.isACtive()) {
            mView.setMusicData(data);
        }
    }

    @Override
    public void onFailed() {

    }
}
