package com.example.fakeqqmusic.base;

import com.example.fakeqqmusic.base.network.LoadTasksCallBack;
import com.example.fakeqqmusic.base.network.NetTask;
import com.example.fakeqqmusic.base.network.musicData;

public class MusicInfoPresenter implements MusicInfoContract.Presenter, LoadTasksCallBack<musicData> {

    private NetTask netTask;
    private MusicInfoContract.View addTaskView;
    public MusicInfoPresenter(MusicInfoContract.View addTaskView, NetTask netTask) {
        this.netTask = netTask;
        this.addTaskView = addTaskView;
    }
    @Override
    public void onSuccess(musicData musicData) {
        if (addTaskView.isACtive()) {
            addTaskView.setMusicData(musicData);
        }
    }
    @Override
    public void onFailed() {
        if (addTaskView.isACtive()) {
            addTaskView.showError();
        }
    }
    @Override
    public void getMusiInfo(String ip) {
        netTask.execute(ip, this);
    }
    //热歌榜|新歌榜|飙升榜|抖音榜|电音榜
    @Override
    public void onstart() {
        getMusiInfo("新歌榜");
    }
}