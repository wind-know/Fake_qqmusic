package com.example.fakeqqmusic.service;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.example.fakeqqmusic.R;
import com.example.fakeqqmusic.base.network.musicData;
import com.example.fakeqqmusic.ui.musicplay.MusicActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    public static final String ACTION_OPT_MUSIC_PLAY = "ACTION_OPT_MUSIC_PLAY";
    public static final String ACTION_OPT_MUSIC_PAUSE = "ACTION_OPT_MUSIC_PAUSE";
    public static final String ACTION_OPT_MUSIC_NEXT = "ACTION_OPT_MUSIC_NEXT";
    public static final String ACTION_OPT_MUSIC_LAST = "ACTION_OPT_MUSIC_LAST";
    public static final String ACTION_OPT_MUSIC_SEEK_TO = "ACTION_OPT_MUSIC_SEEK_TO";

    public static final String ACTION_STATUS_MUSIC_PLAY = "ACTION_STATUS_MUSIC_PLAY";
    public static final String ACTION_STATUS_MUSIC_PAUSE = "ACTION_STATUS_MUSIC_PAUSE";
    public static final String ACTION_STATUS_MUSIC_COMPLETE = "ACTION_STATUS_MUSIC_COMPLETE";
    public static final String ACTION_STATUS_MUSIC_DURATION = "ACTION_STATUS_MUSIC_DURATION";

    public static final String PARAM_MUSIC_DURATION = "PARAM_MUSIC_DURATION";
    public static final String PARAM_MUSIC_SEEK_TO = "PARAM_MUSIC_SEEK_TO";
    public static final String PARAM_MUSIC_CURRENT_POSITION = "PARAM_MUSIC_CURRENT_POSITION";
    public static final String PARAM_MUSIC_IS_OVER = "PARAM_MUSIC_IS_OVER";
    public static final String PARAM_MUSIC_NOW_MUSIC_INDEX =  "PARAM_MUSIC_NOW_MUSIC_INDEX";

    private int mCurrentMusicIndex = 0;
    private boolean mIsMusicPause = false;
    private List<musicData> mMusicDatas = new ArrayList<>();

    private MyBroadcastReceiver mMyBroadcastReceiver = new MyBroadcastReceiver();
    private MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ButtonClick", "onStartCommand: ");
        initMusicDatas(intent);
        play(mCurrentMusicIndex);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBoardCastReceiver();
    }

    private void initMusicDatas(Intent intent) {
        if (intent == null) return;
        List<musicData> musicDatas = (List<musicData>) intent.getSerializableExtra(MusicActivity.PARAM_MUSIC_LIST);
        mMusicDatas.clear();
        mMusicDatas.addAll(musicDatas);
    }

    private void initBoardCastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_OPT_MUSIC_PLAY);
        intentFilter.addAction(ACTION_OPT_MUSIC_PAUSE);
        intentFilter.addAction(ACTION_OPT_MUSIC_NEXT);
        intentFilter.addAction(ACTION_OPT_MUSIC_LAST);
        intentFilter.addAction(ACTION_OPT_MUSIC_SEEK_TO);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMyBroadcastReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMyBroadcastReceiver);
    }

    private void play(final int index) {
        Log.d("ButtonClick", "play: " + index);
        try {
            Log.d("MusicService", "play: in" );
            Log.d("ButtonClick", "play:in " + index);
            Log.d("ButtonClick", "play: "+mMusicDatas.size());
            if (index >= mMusicDatas.size()) return;
            if (mCurrentMusicIndex == index && mIsMusicPause) {
                Log.d("ButtonClick", "start:in " + index);
                mMediaPlayer.start();
            } else {
                Log.d("ButtonClick", "play:out " + index);
//                if(mMediaPlayer != null){
//                    mMediaPlayer.stop();
//                    mMediaPlayer = null;
//                }
                if (mMediaPlayer!= null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.reset(); // 调用reset可以让MediaPlayer回到Initialized状态，方便重新设置数据源等操作
                }
                mMediaPlayer = new MediaPlayer();
                System.out.println(mMusicDatas.get(index).getData().getUrl());

//                mMediaPlayer.setDataSource(mMusicDatas.get(index).getData().getUrl());
//                mMediaPlayer.prepareAsync(); // 对于网络资源，推荐使用prepareAsync异步准备，避免阻塞主线程
//                mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
//                    mediaPlayer.start();
//                    mMediaPlayer.setOnCompletionListener(this);
//                    mCurrentMusicIndex = index;
//                    mIsMusicPause = false;
//                    int duration = mMediaPlayer.getDuration();
//                    sendMusicDurationBroadCast(duration);
//                });
                AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.apt);
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                afd.close();

                mMediaPlayer.setOnCompletionListener(this);
                mCurrentMusicIndex = index;
                mIsMusicPause = false;
                int duration = mMediaPlayer.getDuration();
                sendMusicDurationBroadCast(duration);
            }
            sendMusicStatusBroadCast(ACTION_STATUS_MUSIC_PLAY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void pause() {
        if (mMediaPlayer == null) return;

        mMediaPlayer.pause();
        mIsMusicPause = true;
        sendMusicStatusBroadCast(ACTION_STATUS_MUSIC_PAUSE);
    }

    private void stop() {

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    private void next() {
        if (mCurrentMusicIndex + 1 < mMusicDatas.size()) {
            play(mCurrentMusicIndex + 1);
        } else {
            stop();
        }
    }

    private void last() {
        if (mCurrentMusicIndex != 0) {
            play(mCurrentMusicIndex - 1);
        }
    }

    private void seekTo(Intent intent) {
        if (mMediaPlayer.isPlaying()) {
            int position = intent.getIntExtra(PARAM_MUSIC_SEEK_TO, 0);
            mMediaPlayer.seekTo(position);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        sendMusicCompleteBroadCast();
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_OPT_MUSIC_PLAY)) {
                play(mCurrentMusicIndex);
            } else if (action.equals(ACTION_OPT_MUSIC_PAUSE)) {
                pause();
            } else if (action.equals(ACTION_OPT_MUSIC_LAST)) {
                last();
            } else if (action.equals(ACTION_OPT_MUSIC_NEXT)) {
                next();
            } else if (action.equals(ACTION_OPT_MUSIC_SEEK_TO)) {
                seekTo(intent);
            }
        }
    }
    private void sendMusicCompleteBroadCast() {
        Intent intent = new Intent(ACTION_STATUS_MUSIC_COMPLETE);
        intent.putExtra(PARAM_MUSIC_IS_OVER, (mCurrentMusicIndex == mMusicDatas.size() - 1));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMusicDurationBroadCast(int duration) {
        Intent intent = new Intent(ACTION_STATUS_MUSIC_DURATION);
        intent.putExtra(PARAM_MUSIC_DURATION, duration);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    private void sendMusicStatusBroadCast(String action) {
        Intent intent = new Intent(action);
        if (action.equals(ACTION_STATUS_MUSIC_PLAY)) {
            intent.putExtra(PARAM_MUSIC_CURRENT_POSITION,mMediaPlayer.getCurrentPosition());
            intent.putExtra(PARAM_MUSIC_NOW_MUSIC_INDEX,mCurrentMusicIndex);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}